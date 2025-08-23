package ru.shiroforbes2.dto

data class LoginWealthStatistics(
  val login: String,
  val wealth: Long,
  val spent: Long,
  val transactionsCount: Long,
)
