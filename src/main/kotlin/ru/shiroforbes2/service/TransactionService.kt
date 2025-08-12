package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.TransactionDTO
import ru.shiroforbes2.dto.toTransactionDTO
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.googlesheets.writer.SheetWriterService
import ru.shiroforbes2.repository.TransactionRepository

@Service
class TransactionService(
  private val transactionRepository: TransactionRepository,
  private val sheetWriterService: SheetWriterService,
) {
  fun getGroupTransactions(group: Group): List<TransactionDTO> =
    transactionRepository
      .findAllOrderByDate(group)
      .map { it.toTransactionDTO() }

  fun insertTransactions(
    logins: List<String>,
    amount: Long,
    message: String,
  ) {
    transactionRepository.insertTransactions(logins, amount, message)
  }

  fun updateTransactions(
    spreadsheetId: String,
    sheetTitle: String,
    group: Group,
    transactions: List<TransactionDTO>,
  ) {
    sheetWriterService.updateTransactions(spreadsheetId, sheetTitle, group, transactions)
  }
}
