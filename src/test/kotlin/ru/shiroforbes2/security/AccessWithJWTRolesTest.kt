package ru.shiroforbes2.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.request.SignupRequest
import ru.shiroforbes2.dto.response.SigninResponse
import ru.shiroforbes2.entity.Group

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccessWithJWTRolesTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  private lateinit var adminToken: String
  private lateinit var studentToken: String

  @BeforeEach
  fun setup() {
    adminToken =
      createUser(
        login = "testAdmin",
        password = "password",
        role = "admin",
        name = "Test Admin",
        group = Group.Urban.name,
      )

    studentToken =
      createUser(
        login = "testStudent",
        password = "12345678",
        role = "student",
        name = "Test Student",
        group = Group.Countryside.name,
      )
  }

  private fun createUser(
    login: String,
    password: String,
    role: String,
    name: String,
    group: String?,
  ): String {
    val signupRequest =
      SignupRequest(
        login = login,
        password = password,
        role = role,
        name = name,
        group = group,
      )

    mockMvc
      .post("/api/v2/auth/signup") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(signupRequest)
      }.andExpect {
        status { isOk() }
      }

    val result =
      mockMvc
        .post("/api/v2/auth/signin") {
          contentType = MediaType.APPLICATION_JSON
          content = objectMapper.writeValueAsString(mapOf("login" to login, "password" to password))
        }.andReturn()

    val signinResponse = objectMapper.readValue(result.response.contentAsString, SigninResponse::class.java)
    assertNotNull(signinResponse.accessToken)
    return signinResponse.accessToken
  }

  @Test
  fun `allow admin to access all profiles`() {
    mockMvc
      .get("/api/v2/profile/all") {
        header("Authorization", "Bearer $adminToken")
      }.andExpect {
        status { isOk() }
      }
  }

  @Test
  fun `allow student to access all profiles`() {
    mockMvc
      .get("/api/v2/profile/all") {
        header("Authorization", "Bearer $studentToken")
      }.andExpect {
        status { isForbidden() }
      }
  }

  @Test
  fun `forbid unauthorised access to all profiles`() {
    mockMvc
      .get("/api/v2/profile/all")
      .andExpect {
        status { isUnauthorized() }
      }
  }
}
