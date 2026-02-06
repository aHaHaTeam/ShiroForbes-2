package ru.shiroforbes2.dto.request

import java.time.LocalDateTime

data class CreateAchievementRequest(
  val title: String,
  val description: String,
  val image: String,
  val date: LocalDateTime? = null,
)
