package ru.shiroforbes2.dto.request

data class CreateTransactionRequest(
  val logins: List<String>,
  val message: String,
  val amount: Long,
)
