package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "transaction")
class Transaction(
  @Id
  @Column(name = "id", nullable = false)
  val id: Int,

  @Column(name = "date", nullable = false)
  var date: LocalDateTime,
  @Column(name = "student_id", nullable = false)
  var studentId: Int,

  @Column(name = "amount", nullable = false)
  var amount: Int,
)
