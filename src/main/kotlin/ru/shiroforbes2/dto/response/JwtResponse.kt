package ru.shiroforbes2.dto.response

data class JwtResponse(
  val token: String?,
  val id: Long?,
  val login: String?,
  val roles: List<String>,
)
