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

  fun getStudentTransactions(login: String): List<TransactionDTO> =
    transactionRepository
      .findAllByStudentLoginOrderByDate(login)
      .map { it.toTransactionDTO() }

  fun insertTransactions(
    logins: List<String>,
    amount: Long,
    message: String,
  ) {
    transactionRepository.insertTransactions(logins, amount, message)
  }

  // TODO
  // fun getWealthState(group: Group): List<SomeObject>
  // SomeObject has to contain information {Name, Wealth, TransactionCount, AmountSpent etc}
  // returns information about all students in group

  // TODO
  // fun getStudentWealth(login: String): SomeObject
  // returns information about one student

  fun updateTransactions(
    spreadsheetId: String,
    sheetTitle: String,
    group: Group,
    transactions: List<TransactionDTO>,
  ) {
    // writes transactions to GoogleSheets
    sheetWriterService.updateTransactions(spreadsheetId, sheetTitle, group, transactions)
  }
}
