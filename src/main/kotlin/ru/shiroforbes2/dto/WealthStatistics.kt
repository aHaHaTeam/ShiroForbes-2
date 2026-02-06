package ru.shiroforbes2.dto

data class WealthStatistics(
  val wealth: Long,
  val spent: Long,
  val transactionsCount: Long,
  val firstName: String,
  val lastName: String,
)

fun WealthStatistics.name(): String = "$lastName $firstName"
