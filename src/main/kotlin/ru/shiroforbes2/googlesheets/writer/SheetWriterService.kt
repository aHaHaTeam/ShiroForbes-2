//package ru.shiroforbes2.googlesheets.writer
//
//import com.google.api.services.sheets.v4.Sheets
//import com.google.api.services.sheets.v4.model.AddSheetRequest
//import com.google.api.services.sheets.v4.model.AppendDimensionRequest
//import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
//import com.google.api.services.sheets.v4.model.ClearValuesRequest
//import com.google.api.services.sheets.v4.model.Request
//import com.google.api.services.sheets.v4.model.SheetProperties
//import com.google.api.services.sheets.v4.model.Spreadsheet
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Service
//import ru.shiroforbes2.googlesheets.reader.SheetReaderService
//import java.io.IOException
//
//@Service
//class SheetWriterService(
//  private val sheetsClient: Sheets,
//) {
//  class SheetsWriterException internal constructor(
//    message: String,
//    cause: Throwable,
//  ) : RuntimeException(message, cause) {
//    init {
//      logger.error(message, cause)
//    }
//  }
//
//
//  fun createSheet(
//    spreadsheetId: String,
//    title: String,
//    height: Int = 1e3.toInt(),
//    width: Int = 1e2.toInt(),
//  ): Int {
//    batchUpdate(
//      spreadsheetId,
//      createSheetRequest(title),
//      "Failed to create sheet (spreadsheetId=[$spreadsheetId], sheetTitle=[$title])",
//    )
//
//    val spreadsheet = resolveSpreadsheet(spreadsheetId)
//
//    val sheetId =
//      spreadsheet.sheets
//        .first { it.properties.title == title }
//        .properties.sheetId
//
//    batchUpdate(
//      spreadsheetId,
//      extendTableRequests(sheetId, width, height),
//      "Failed to resize the sheet (spreadsheetId=[$spreadsheetId], sheetTitle=[$title])",
//    )
//
//    return sheetId
//  }
//
//  private fun createSheetRequest(title: String): List<Request> =
//    listOf(Request().setAddSheet(AddSheetRequest().setProperties(SheetProperties().setTitle(title))))
//
//  private fun extendTableRequests(
//    sheetId: Int,
//    width: Int,
//    height: Int,
//  ): List<Request> =
//    listOf(
//      Request()
//        .setAppendDimension(
//          AppendDimensionRequest().apply {
//            this.sheetId = sheetId
//            this.dimension = "COLUMNS"
//            this.length = width
//          },
//        ),
//      Request()
//        .setAppendDimension(
//          AppendDimensionRequest().apply {
//            this.sheetId = sheetId
//            this.dimension = "ROWS"
//            this.length = height
//          },
//        ),
//    )
//
//
//  private fun resolveSpreadsheet(spreadsheetId: String): Spreadsheet =
//    try {
//      sheetsClient.spreadsheets().get(spreadsheetId).execute()
//    } catch (e: IOException) {
//      throw SheetsWriterException("Error resolving a spreadsheet with given spreadsheetId=[$spreadsheetId]", e)
//    }
//
//  private fun clearSheet(
//    spreadsheetId: String,
//    sheetTitle: String,
//  ) {
//    try {
//      sheetsClient
//        .spreadsheets()
//        .values()
//        .clear(spreadsheetId, sheetTitle, ClearValuesRequest())
//        .execute()
//    } catch (e: IOException) {
//      throw SheetsWriterException("Failed to clear sheet (spreadsheetId=[$spreadsheetId], sheetTitle=[$sheetTitle])", e)
//    }
//  }
//
//  private fun batchUpdate(
//    spreadsheetId: String,
//    requests: List<Request>,
//    message: String,
//  ) {
//    val batchUpdateRequest = BatchUpdateSpreadsheetRequest().setRequests(requests)
//    try {
//      sheetsClient.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute()
//    } catch (e: IOException) {
//      throw SheetsWriterException(message, e)
//    }
//  }
//
//  companion object {
//    private val logger: Logger = LoggerFactory.getLogger(SheetReaderService::class.java)
//  }
//}
