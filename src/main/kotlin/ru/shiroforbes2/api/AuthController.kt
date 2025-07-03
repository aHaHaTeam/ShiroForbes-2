package ru.shiroforbes2.api

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.request.SigninRequest
import ru.shiroforbes2.dto.request.SignupRequest
import ru.shiroforbes2.dto.response.JwtResponse
import ru.shiroforbes2.dto.response.MessageResponse
import ru.shiroforbes2.entity.Admin
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.entity.Teacher
import ru.shiroforbes2.security.jwt.JWTUtils
import ru.shiroforbes2.security.services.UserDetailsImpl
import ru.shiroforbes2.service.UserService

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v2/auth")
class AuthController(
  private val authenticationManager: AuthenticationManager,
  private val userService: UserService,
  private val encoder: PasswordEncoder,
  private val jwtUtils: JWTUtils,
) {
  @PostMapping("/signin")
  fun authenticateUser(
    @RequestBody signinRequest: SigninRequest,
  ): ResponseEntity<*> {
    val authentication =
      authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken(signinRequest.login, signinRequest.password),
      )

    SecurityContextHolder.getContext().authentication = authentication
    val jwt = jwtUtils.generateJwtToken(authentication)

    val userDetails = authentication.principal as UserDetailsImpl
    val roles = userDetails.authorities?.mapNotNull { it?.authority } ?: emptyList()

    return ResponseEntity.ok(
      JwtResponse(
        jwt,
        userDetails.id,
        userDetails.username,
        roles,
      ),
    )
  }

  @PostMapping("/signup")
  fun registerUser(
    @RequestBody signUpRequest: SignupRequest,
  ): ResponseEntity<*> {
    if (userService.existsByLogin(signUpRequest.login)) {
      return ResponseEntity
        .badRequest()
        .body(MessageResponse("Error: Username is already taken!"))
    }

    when (signUpRequest.role.lowercase()) {
      "student" -> {
        val student =
          Student(
            login = signUpRequest.login,
            password = encoder.encode(signUpRequest.password),
            name = signUpRequest.name,
            group = signUpRequest.group?.let { Group.valueOf(it) } ?: Group.Urban,
            score = 0,
            total = 0.0f,
          )
        userService.createNewStudent(student)
      }

      "teacher" -> {
        val teacher =
          Teacher(
            login = signUpRequest.login,
            password = encoder.encode(signUpRequest.password),
            name = signUpRequest.name,
          )
        userService.createNewTeacher(teacher)
      }

      "admin" -> {
        val admin =
          Admin(
            login = signUpRequest.login,
            password = encoder.encode(signUpRequest.password),
            name = signUpRequest.name,
          )
        userService.createNewAdmin(admin)
      }

      else -> return ResponseEntity.badRequest().body(MessageResponse("Error: Role not found!"))
    }

    return ResponseEntity.ok(MessageResponse("User registered successfully!"))
  }
}
