package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.TransactionDTO
import ru.shiroforbes2.dto.toTransactionDTO
import ru.shiroforbes2.repository.TransactionRepository

@Service
class TransactionService(
  private val transactionRepository: TransactionRepository,
) {
  fun getCountrysideTransactions(): List<TransactionDTO> =
    transactionRepository
      .findAllCountrysideOrderByDate()
      .map { it.toTransactionDTO() }

  fun getUrbanTransactions(): List<TransactionDTO> =
    transactionRepository
      .findAllUrbanOrderByDate()
      .map { it.toTransactionDTO() }

  fun insertTransaction(
    names: List<String>,
    amount: Long,
    message: String,
  ) = transactionRepository.insertTransaction(names, amount, message)
}
