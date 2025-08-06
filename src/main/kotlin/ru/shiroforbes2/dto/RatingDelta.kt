package ru.shiroforbes2.dto

data class RatingDelta(
  val firstName: String,
  val lastName: String,
  val login: String,
  val oldRank: Int,
  val newRank: Int,
  val solved: Float,
  val solvedDelta: Float,
  val rating: Int,
  val ratingDelta: Int,
)
