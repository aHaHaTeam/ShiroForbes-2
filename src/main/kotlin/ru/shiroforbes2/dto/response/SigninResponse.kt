package ru.shiroforbes2.dto.response

import ru.shiroforbes2.entity.Group

data class SigninResponse(
  val accessToken: String,
  val refreshToken: String,
  val userId: Long,
  val login: String,
  val role: String,
  val group: Group,
)
