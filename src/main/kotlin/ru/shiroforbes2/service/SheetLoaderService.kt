package ru.shiroforbes2.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.shiroforbes2.googlesheets.CustomDecoder
import ru.shiroforbes2.googlesheets.DefaultDecoder
import ru.shiroforbes2.googlesheets.ReflectiveTableParser
import java.io.IOException
import kotlin.reflect.KClass

@Service
class SheetLoaderService(
  @Autowired private val sheetsService: SheetsService,
) {
  class RatingServiceException(
    message: String,
    cause: Throwable? = null,
  ) : RuntimeException(message, cause)

  @Throws(RatingServiceException::class)
  fun <T : Any> getRows(
    spreadsheetId: String,
    range: String,
    clazz: KClass<T>,
  ): List<T> = getRows(spreadsheetId, listOf(range), clazz)

  @Throws(RatingServiceException::class)
  fun <T : Any> getRows(
    spreadsheetId: String,
    ranges: List<String>,
    clazz: KClass<T>,
  ): List<T> {
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

    return ReflectiveTableParser(clazz, listOf(CustomDecoder(), DefaultDecoder())).joinAndParse(tables)
  }

  companion object {
    private val log: Logger = LoggerFactory.getLogger(SheetLoaderService::class.java)
  }
}
