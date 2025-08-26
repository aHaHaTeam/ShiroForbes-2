package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "transaction")
open class Transaction(
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int,
  @Column(name = "date", nullable = false)
  val date: LocalDateTime,
  @Column(name = "student_id", nullable = false)
  val studentId: Long,
  @Column(name = "amount", nullable = false)
  val amount: Long,
  @Column(name = "message", nullable = false)
  val message: String,
)
