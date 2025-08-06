package ru.shiroforbes2.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.shiroforbes2.dto.request.SigninRequest
import ru.shiroforbes2.dto.request.SignupRequest
import ru.shiroforbes2.dto.request.TokenRefreshRequest
import ru.shiroforbes2.dto.response.MessageResponse
import ru.shiroforbes2.dto.response.SigninResponse
import ru.shiroforbes2.dto.response.TokenRefreshResponse
import ru.shiroforbes2.entity.Admin
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Rights
import ru.shiroforbes2.repository.RefreshTokenRepository
import ru.shiroforbes2.repository.UserRepository
import ru.shiroforbes2.security.jwt.JWTUtils
import ru.shiroforbes2.service.UserService
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Autowired
  private lateinit var jwtUtils: JWTUtils

  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var refreshTokenRepository: RefreshTokenRepository

  private val testAdminLogin = "testAdminLogin"
  private val testAdminPassword = "testAdminPassword"

  private val testLogin = "testLogin"
  private val testPassword = "testPassword"
  private val testRole = Rights.Student
  private val testName = "testName"
  private val testGroup = Group.Urban

  private lateinit var adminAccessToken: String

  private lateinit var accessToken: String
  private lateinit var refreshToken: String

  @BeforeEach
  fun setup() {
    val admin =
      Admin(
        login = testAdminLogin,
        password = passwordEncoder.encode(testAdminPassword),
        name = "Admin User",
      )
    userService.createNewAdmin(admin)

    adminAccessToken = signin(testAdminLogin, testAdminPassword).accessToken
  }

  @AfterEach
  fun cleanup() {
    refreshTokenRepository.deleteAll()
    userRepository.deleteAll()
  }

  private fun signup(): MessageResponse {
    val result =
      mockMvc
        .post("/api/v2/auth/signup") {
          header("Authorization", "Bearer $adminAccessToken")

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
    return objectMapper.readValue<MessageResponse>(body)
  }

  private fun signin(
    login: String,
    password: String,
  ): SigninResponse {
    val result =
      mockMvc
        .post("/api/v2/auth/signin") {
          contentType = MediaType.APPLICATION_JSON
          content = objectMapper.writeValueAsString(SigninRequest(login, password))
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
  fun `register a new user and sign in`() {
    signup()
    val signInResponse = signin(testLogin, testPassword)
    accessToken = signInResponse.accessToken
    refreshToken = signInResponse.refreshToken
    assertEquals(HttpStatus.OK.value(), profile())
  }

  @Test
  fun `failing access with expired access token`() {
    signup()
    val signInResponse = signin(testLogin, testPassword)
    accessToken = jwtUtils.generateJwtToken(testLogin, -1000L)
    refreshToken = signInResponse.refreshToken
    assertEquals(HttpStatus.UNAUTHORIZED.value(), profile())
  }

  @Test
  fun `refreshing access token`() {
    signup()
    val signInResponse = signin(testLogin, testPassword)
    refreshToken = signInResponse.refreshToken
    accessToken = jwtUtils.generateJwtToken(testLogin, -1000L)
    val refreshResponse = refresh()
    assertEquals(refreshToken, refreshResponse.refreshToken)
    assertNotEquals(accessToken, refreshResponse.accessToken)
    accessToken = refreshResponse.accessToken
    assertEquals(HttpStatus.OK.value(), profile())
  }
}
