package ru.shiroforbes2

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.request.SignupRequest

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Test
  fun `register a new user and sign in`() {
    val signupRequest =
      SignupRequest(
        login = "testLogin",
        password = "password",
        role = "student",
        name = "Test User",
        group = "Urban",
      )

    mockMvc
      .post("/api/v2/auth/signup") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(signupRequest)
      }.andExpect {
        status { isOk() }
        jsonPath("$.message") { value("User registered successfully!") }
      }

    mockMvc
      .post("/api/v2/auth/signin") {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(mapOf("login" to "testLogin", "password" to "password"))
      }.andExpect {
        status { isOk() }
        jsonPath("$.token") { exists() }
      }
  }
}
