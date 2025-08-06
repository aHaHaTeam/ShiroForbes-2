package ru.shiroforbes2.dto.request

data class CreateTransactionRequest(
  val names: List<String>,
  val message: String,
  val amount: Long,
)
