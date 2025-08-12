package ru.shiroforbes2.googlesheets.writer

import ru.shiroforbes2.dto.TransactionDTO
import ru.shiroforbes2.entity.Group
import java.time.format.DateTimeFormatter

private const val NUMBER_OF_COLUMNS: Int = 4
private const val COLUMN_WIDTH: Int = 40

internal class TransactionsTableComposer {
  fun composeTable(
    group: Group,
    transactions: List<TransactionDTO>,
  ): ComposedTable =
    ComposedTable(
      composeHeader(group) + composeTransactions(transactions),
      List(NUMBER_OF_COLUMNS) { COLUMN_WIDTH },
    )

  private fun composeHeader(group: Group): List<List<FormattedCell>> =
    listOf(
      listOf(FormattedCell(group.name, DataType.STRING, NUMBER_OF_COLUMNS).centerAlign().bold().borders(2)),
      listOf("date", "studentId", "amount", "message").map { FormattedCell(it, DataType.STRING).bold().borders() },
    )

  private val dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

  private fun composeTransactions(transactions: List<TransactionDTO>): List<List<FormattedCell>> =
    transactions.map { transaction ->
      listOf(
        FormattedCell(transaction.date.format(dateTimeFormat), DataType.STRING)
          .topBorder()
          .bottomBorder()
          .leftBorder()
          .centerAlign(),
        FormattedCell(transaction.studentId.toString(), DataType.LONG)
          .topBorder()
          .bottomBorder()
          .centerAlign(),
        FormattedCell(transaction.amount.toString(), DataType.LONG)
          .topBorder()
          .bottomBorder()
          .centerAlign(),
        FormattedCell(transaction.message, DataType.STRING)
          .topBorder()
          .bottomBorder()
          .rightBorder()
          .centerAlign(),
      )
    }
}
