package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.Transaction
import java.time.LocalDateTime

data class TransactionDTO(
  val date: LocalDateTime,
  val studentId: Long,
  val amount: Long,
)

fun Transaction.toTransactionDTO(): TransactionDTO =
  TransactionDTO(
    date = date,
    studentId = studentId,
    amount = amount,
  )
