package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.PerformanceStatistics
import java.time.LocalDateTime

data class PerformanceStatisticsDTO(
  val date: LocalDateTime,
  val episode: Int,
  val totalSolved: Float,
  val totalRating: Float,
  val algebra: Float,
  val numbersTheory: Float,
  val geometry: Float,
  val combinatorics: Float,
  val totalSolvedPercent: Float,
  val algebraSolvedPercent: Float,
  val numbersTheorySolvedPercent: Float,
  val geometrySolvedPercent: Float,
  val combinatoricsSolvedPercent: Float,
  val grobs: Int,
  val position: Int,
)

fun PerformanceStatistics.toRatingDTO(): PerformanceStatisticsDTO =
  PerformanceStatisticsDTO(
    date = date,
    episode = episode,
    totalSolved = totalSolved,
    totalRating = totalRating,
    algebra = algebra,
    numbersTheory = numbersTheory,
    geometry = geometry,
    combinatorics = combinatorics,
    totalSolvedPercent = totalSolvedPercent,
    algebraSolvedPercent = algebraSolvedPercent,
    numbersTheorySolvedPercent = numbersTheorySolvedPercent,
    geometrySolvedPercent = geometrySolvedPercent,
    combinatoricsSolvedPercent = combinatoricsSolvedPercent,
    grobs = grobs,
    position = position,
  )
