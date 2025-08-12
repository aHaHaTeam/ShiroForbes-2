package ru.shiroforbes2.dto

import java.time.LocalDateTime

data class SheetsTransactionRow(
  val studentId: Long,
  val login: String,
  val firstName: String,
  val lastName: String,
  val date: LocalDateTime,
  val amount: Long,
  val message: String,
) {
  val name: String
    get() = "$lastName $firstName"
}
