package ru.shiroforbes2.dto.response

data class TokenRefreshResponse(
  val accessToken: String,
  val refreshToken: String,
  val tokenType: String = "Bearer",
)
