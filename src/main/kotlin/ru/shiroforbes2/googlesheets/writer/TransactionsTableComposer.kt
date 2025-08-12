package ru.shiroforbes2.googlesheets.writer

import ru.shiroforbes2.dto.SheetsTransactionRow
import ru.shiroforbes2.entity.Group
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val NUMBER_OF_COLUMNS: Int = 5
private const val COLUMN_WIDTH: Int = 40

internal class TransactionsTableComposer {
  fun composeTable(
    group: Group,
    transactions: List<SheetsTransactionRow>,
  ): ComposedTable =
    ComposedTable(
      composeHeader(group) + composeTransactions(transactions),
      List(NUMBER_OF_COLUMNS) { COLUMN_WIDTH },
    )

  private fun composeHeader(group: Group): List<List<FormattedCell>> =
    listOf(
      listOf(FormattedCell(group.name, DataType.STRING, NUMBER_OF_COLUMNS).centerAlign().bold().borders(2)),
      listOf(
        "date",
        "studentId",
        "name",
        "amount",
        "message",
      ).map { FormattedCell(it, DataType.STRING).bold().borders() },
    )

  private val moscowZoneId: ZoneId = ZoneId.of("Europe/Moscow")
  private val dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

  private fun composeTransactions(transactions: List<SheetsTransactionRow>): List<List<FormattedCell>> =
    transactions.map { transaction ->
      listOf(
        FormattedCell(transaction.date.atZone(moscowZoneId).format(dateTimeFormat), DataType.STRING)
          .topBorder()
          .bottomBorder()
          .leftBorder()
          .centerAlign(),
        FormattedCell(transaction.studentId.toString(), DataType.LONG)
          .topBorder()
          .bottomBorder()
          .centerAlign(),
        FormattedCell(transaction.name, DataType.LONG)
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
