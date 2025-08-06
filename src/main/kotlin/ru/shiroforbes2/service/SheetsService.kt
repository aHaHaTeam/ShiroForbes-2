package ru.shiroforbes2.service

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import org.springframework.stereotype.Service
import java.io.IOException
import kotlin.jvm.Throws

@Service
class SheetsService(
  private val sheets: Sheets,
) {
  fun getSpreadsheetValues(
    spreadsheetId: String,
    range: String,
  ): ValueRange {
    val response =
      sheets
        .spreadsheets()
        .values()
        .get(spreadsheetId, range)
        .setValueRenderOption("FORMATTED_VALUE")
        .execute()
    return response
  }

  @Throws(IOException::class)
  fun getSpreadsheetValues(
    spreadsheetId: String,
    ranges: List<String>,
  ): List<ValueRange> {
    val response =
      sheets
        .spreadsheets()
        .values()
        .batchGet(spreadsheetId)
        .setRanges(ranges)
        .setValueRenderOption("FORMATTED_VALUE")
        .execute()
    return response.valueRanges
  }
}
