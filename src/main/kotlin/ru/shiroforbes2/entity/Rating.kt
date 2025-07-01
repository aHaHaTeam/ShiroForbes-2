package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "rating")
data class Rating(
  @Id
  @Column(name = "id", nullable = false)
  val id: Int,

  @Column(name = "date", nullable = false)
  var date: LocalDateTime,
  @Column(name = "student_id", nullable = false)
  var studentId: Int,

  @Column(name = "points", nullable = false)
  var points: Int,
  @Column(name = "total", nullable = false)
  var total: Float,
  @Column(name = "algebra", nullable = false)
  val algebra: Float,
  @Column(name = "numbers_theory", nullable = false)
  val numbersTheory: Float,
  @Column(name = "geometry", nullable = false)
  val geometry: Float,
  @Column(name = "combinatorics", nullable = false)
  val combinatorics: Float,
  @Column(name = "total_percent", nullable = false)
  var totalPercent: Int,
  @Column(name = "algebra_percent", nullable = false)
  var algebraPercent: Int,
  @Column(name = "numbers_theory_percent", nullable = false)
  var numbersTheoryPercent: Int,
  @Column(name = "geometry_percent", nullable = false)
  var geometryPercent: Int,
  @Column(name = "combinatorics_percent", nullable = false)
  var combinatoricsPercent: Int,
  @Column(name = "grobs", nullable = false)
  var grobs: Int,
  @Column(name = "position", nullable = false)
  val position: Int,
)
