package ru.shiroforbes2.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.shiroforbes2.dto.request.SigninRequest
import ru.shiroforbes2.dto.response.SigninResponse
import ru.shiroforbes2.entity.Admin
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.repository.RefreshTokenRepository
import ru.shiroforbes2.repository.UserRepository
import ru.shiroforbes2.service.UserService

@SpringBootTest
@AutoConfigureMockMvc
class AccessWithJWTRolesTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var refreshTokenRepository: RefreshTokenRepository

  private lateinit var adminToken: String
  private lateinit var studentToken: String

  @BeforeEach
  fun setup() {
    userService.createNewAdmin(Admin("testAdmin", passwordEncoder.encode("password"), "adminName"))
    adminToken =
      signin(
        login = "testAdmin",
        password = "password",
      )
    userService.createNewStudent(
      Student(
        "testStudent",
        passwordEncoder.encode("12345678"),
        "studentFirstName",
        "studentLastName",
        Group.Urban,
        0,
        0f,
      ),
    )
    studentToken =
      signin(
        login = "testStudent",
        password = "12345678",
      )
  }

  @AfterEach
  fun cleanup() {
    refreshTokenRepository.deleteAll()
    userRepository.deleteAll()
  }

  private fun signin(
    login: String,
    password: String,
  ): String {
    val result =
      mockMvc
        .post("/api/v2/auth/signin") {
          contentType = MediaType.APPLICATION_JSON
          content = objectMapper.writeValueAsString(SigninRequest(login, password))
        }.andExpect {
          status { isOk() }
        }.andReturn()

    val body = result.response.contentAsString
    val signinResponse = objectMapper.readValue<SigninResponse>(body)
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
