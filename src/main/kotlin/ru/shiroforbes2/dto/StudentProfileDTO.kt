package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.Group

data class StudentProfileDTO(
  val name: String,
  val group: Group,
  val login: String,
  val score: Int,
  val total: Float,
  val ratings: List<PerformanceStatisticsDTO>,
  val achievements: List<AchievementDTO>,
)
