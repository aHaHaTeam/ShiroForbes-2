package ru.shiroforbes2.dto.request

data class SignupRequest(
  val login: String,
  val password: String,
  val role: String,
  val name: String,
  val group: String?,
)
