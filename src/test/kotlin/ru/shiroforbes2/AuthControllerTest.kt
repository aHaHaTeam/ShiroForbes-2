package ru.shiroforbes2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.request.SigninRequest
import ru.shiroforbes2.dto.request.SignupRequest
import ru.shiroforbes2.dto.request.TokenRefreshRequest
import ru.shiroforbes2.dto.response.SigninResponse
import ru.shiroforbes2.dto.response.SignupResponse
import ru.shiroforbes2.dto.response.TokenRefreshResponse
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Rights
import ru.shiroforbes2.security.jwt.JWTUtils
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Autowired
  private lateinit var jwtUtils: JWTUtils

  private val testLogin = "testLogin"
  private val testPassword = "testPassword"
  private val testRole = Rights.Student
  private val testName = "testName"
  private val testGroup = Group.Urban

  private lateinit var accessToken: String
  private lateinit var refreshToken: String

  private fun signup(): SignupResponse {
    val result =
      mockMvc
        .post("/api/v2/auth/signup") {
          contentType = MediaType.APPLICATION_JSON
          content =
            objectMapper.writeValueAsString(
              SignupRequest(
                login = testLogin,
                password = testPassword,
                role = testRole.name,
                name = testName,
                group = testGroup.name,
              ),
            )
        }.andExpect {
          status { isOk() }
          jsonPath("$.message") { value("User registered successfully!") }
        }.andReturn()
    val body = result.response.contentAsString
    return objectMapper.readValue<SignupResponse>(body)
  }

  private fun signin(): SigninResponse {
    val result =
      mockMvc
        .post("/api/v2/auth/signin") {
          contentType = MediaType.APPLICATION_JSON
          content = objectMapper.writeValueAsString(SigninRequest(testLogin, testPassword))
        }.andExpect {
          status { isOk() }
          jsonPath("$.accessToken") { exists() }
          jsonPath("$.refreshToken") { exists() }
        }.andReturn()
    val body = result.response.contentAsString
    return objectMapper.readValue<SigninResponse>(body)
  }

  private fun refresh(): TokenRefreshResponse {
    val result =
      mockMvc
        .post("/api/v2/auth/refresh") {
          contentType = MediaType.APPLICATION_JSON
          content =
            objectMapper.writeValueAsString(
              TokenRefreshRequest(refreshToken),
            )
        }.andExpect {
          status { isOk() }
          jsonPath("$.accessToken") { exists() }
          jsonPath("$.refreshToken") { exists() }
        }.andReturn()
    val body = result.response.contentAsString
    return objectMapper.readValue<TokenRefreshResponse>(body)
  }

  private fun profile(): Int {
    val result =
      mockMvc
        .get("/api/v2/profile/$testLogin") {
          header("Authorization", "Bearer $accessToken")
        }.andReturn()
    return result.response.status
  }

  @Test
  fun `register a new user and sign in`() =
    runTest {
      signup()
      val signInResponse = signin()
      accessToken = signInResponse.accessToken
      refreshToken = signInResponse.refreshToken
      assertEquals(HttpStatus.OK.value(), profile())
    }

  @Test
  fun `failing access with expired access token`() =
    runTest {
      signup()
      val signInResponse = signin()
      accessToken = jwtUtils.generateJwtToken(testLogin, -1000L)
      refreshToken = signInResponse.refreshToken
      assertEquals(HttpStatus.UNAUTHORIZED.value(), profile())
    }

  @Test
  fun `refreshing access token`() =
    runTest {
      signup()
      val signInResponse = signin()
      refreshToken = signInResponse.refreshToken
      accessToken = jwtUtils.generateJwtToken(testLogin, -1000L)
      val refreshResponse = refresh()
      assertEquals(refreshToken, refreshResponse.refreshToken)
      assertNotEquals(accessToken, refreshResponse.accessToken)
      accessToken = refreshResponse.accessToken
      assertEquals(HttpStatus.OK.value(), profile())
    }
}
