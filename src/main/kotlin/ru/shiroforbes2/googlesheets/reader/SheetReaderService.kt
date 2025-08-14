package ru.shiroforbes2.googlesheets.reader

import com.google.api.services.sheets.v4.Sheets
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class SheetReaderService(
  private val sheetsClient: Sheets,
) {
  class SheetsReaderException internal constructor(
    message: String,
    cause: Throwable? = null,
  ) : RuntimeException(message, cause) {
    init {
      logger.error(message, cause)
    }
  }

  @Throws(SheetsReaderException::class)
  fun <T : Any> getRows(
    spreadsheetId: String,
    range: String,
    parser: TableParser<T>,
  ): List<T> = getRows(spreadsheetId, listOf(range), parser)

  @Throws(SheetsReaderException::class)
  fun <T : Any> getRows(
    spreadsheetId: String,
    ranges: List<String>,
    parser: TableParser<T>,
  ): List<T> {
    val response =
      try {
        val valueRenderOption = sheetsClient
          .spreadsheets()
          .values()
          .batchGet(spreadsheetId)
          .setRanges(ranges)
          .setValueRenderOption("FORMATTED_VALUE")
        valueRenderOption
          .execute()
          .valueRanges
      } catch (e: IOException) {
        val message = "Error fetching rating from google sheets"
        logger.error(message, e)
        throw SheetsReaderException(message, e)
      }

    val tables =
      response.map { table ->
        table.getValues().map { row -> row.map { it.toString() } }
      }

    return parser.joinAndParse(tables)
  }

  companion object {
    private val logger: Logger = LoggerFactory.getLogger(SheetReaderService::class.java)
  }
}
