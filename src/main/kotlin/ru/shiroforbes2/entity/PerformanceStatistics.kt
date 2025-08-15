package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "rating")
@Suppress("LongParameterList")
class PerformanceStatistics(
  @Id
  @Column(name = "id", nullable = false)
  val id: Int,
  @Column(name = "date", nullable = false)
  var date: LocalDateTime,
  @Column(name = "episode", nullable = false)
  var episode: Int,
  @OneToOne
  @JoinColumn(name = "student_id", nullable = false)
  var student: Student,
  @Column(name = "totalSolved", nullable = false)
  var totalSolved: Float,
  @Column(name = "totalRating", nullable = false)
  var totalRating: Float,
  @Column(name = "algebra", nullable = false)
  val algebra: Float,
  @Column(name = "numbers_theory", nullable = false)
  val numbersTheory: Float,
  @Column(name = "geometry", nullable = false)
  val geometry: Float,
  @Column(name = "combinatorics", nullable = false)
  val combinatorics: Float,
  @Column(name = "total_solved_percent", nullable = false)
  var totalSolvedPercent: Float,
  @Column(name = "algebra_solved_percent", nullable = false)
  var algebraSolvedPercent: Float,
  @Column(name = "numbers_theory_solved_percent", nullable = false)
  var numbersTheorySolvedPercent: Float,
  @Column(name = "geometry_solved_percent", nullable = false)
  var geometrySolvedPercent: Float,
  @Column(name = "combinatorics_solved_percent", nullable = false)
  var combinatoricsSolvedPercent: Float,
  @Column(name = "grobs", nullable = false)
  var grobs: Int,
  @Column(name = "position", nullable = false)
  val position: Int,
)
