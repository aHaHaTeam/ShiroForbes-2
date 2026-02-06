package ru.shiroforbes2.dto

import java.time.LocalDateTime

data class AchievementDTO(
  val userLogin: String,
  val title: String,
  val description: String,
  val image: String,
  val date: LocalDateTime?,
)
