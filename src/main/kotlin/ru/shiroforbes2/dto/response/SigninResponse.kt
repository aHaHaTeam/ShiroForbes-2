package ru.shiroforbes2.dto.response

data class SigninResponse(
  val accessToken: String,
  val refreshToken: String,
  val userId: Long,
  val login: String,
  val role: String,
)
