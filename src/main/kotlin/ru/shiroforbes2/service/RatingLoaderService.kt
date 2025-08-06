package ru.shiroforbes2.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.shiroforbes2.googlesheets.CustomDecoder
import ru.shiroforbes2.googlesheets.DefaultDecoder
import ru.shiroforbes2.googlesheets.ReflectiveTableParser
import ru.shiroforbes2.googlesheets.SheetsRatingRow
import java.io.IOException

@Service
class RatingLoaderService(
  @Autowired private val sheetsService: SheetsService,
) {
  class RatingServiceException(
    message: String,
    cause: Throwable? = null,
  ) : RuntimeException(message, cause)

  private val parser = ReflectiveTableParser(SheetsRatingRow::class, listOf(CustomDecoder(), DefaultDecoder()))

  @Throws(RatingServiceException::class)
  fun getRating(
    spreadsheetId: String,
    ranges: List<String>,
  ): List<SheetsRatingRow> {
    val response =
      try {
        sheetsService.getSpreadsheetValues(spreadsheetId, ranges)
      } catch (e: IOException) {
        val message = "Error fetching rating from google sheets"
        log.error(message, e)
        throw RatingServiceException(message, e)
      }

    val tables =
      response.map { table ->
        table.getValues().map { row -> row.map { it.toString() } }
      }

    return parser.joinAndParse(tables)
  }

  companion object {
    private val log: Logger = LoggerFactory.getLogger(RatingLoaderService::class.java)
  }
}
