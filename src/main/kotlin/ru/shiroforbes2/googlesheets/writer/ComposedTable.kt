package ru.shiroforbes2.googlesheets.writer

import com.google.api.services.sheets.v4.model.DimensionProperties
import com.google.api.services.sheets.v4.model.DimensionRange
import com.google.api.services.sheets.v4.model.GridRange
import com.google.api.services.sheets.v4.model.MergeCellsRequest
import com.google.api.services.sheets.v4.model.Request
import com.google.api.services.sheets.v4.model.RowData
import com.google.api.services.sheets.v4.model.UnmergeCellsRequest
import com.google.api.services.sheets.v4.model.UpdateCellsRequest
import com.google.api.services.sheets.v4.model.UpdateDimensionPropertiesRequest

internal class ComposedTable(
  val cells: List<List<FormattedCell>>,
  val columnWidths: List<Int?>,
) {
  fun generateUpdateRequests(sheetId: Int): List<Request> =
    generateUnmergeRequests(sheetId) +
      generateUpdateRequests(cells, sheetId) +
      generateResizeRequests(columnWidths, sheetId) +
      generateMergeRequests(cells, sheetId)

  private fun generateUnmergeRequests(sheetId: Int?): List<Request> =
    listOf(Request().setUnmergeCells(UnmergeCellsRequest().setRange(GridRange().setSheetId(sheetId))))

  private fun generateMergeRequests(
    data: List<List<FormattedCell>>,
    sheetId: Int,
  ): List<Request> =
    data
      .mapIndexed { row, rowList ->
        var column = 0
        rowList.mapNotNull { cell ->
          var request: Request? = null
          if (cell.width > 1) {
            request =
              Request()
                .setMergeCells(
                  MergeCellsRequest()
                    .setMergeType("MERGE_ALL")
                    .setRange(gridRange(sheetId, row, row, column, column + cell.width)),
                )
          }
          column += cell.width
          return@mapNotNull request
        }
      }.flatten()
      .toList()

  private fun generateResizeRequests(
    columWidths: List<Int?>,
    sheetId: Int?,
  ): List<Request> =
    columWidths
      .mapIndexedNotNull { column, width ->
        width ?: return@mapIndexedNotNull null

        Request()
          .setUpdateDimensionProperties(
            UpdateDimensionPropertiesRequest()
              .setRange(
                DimensionRange()
                  .setSheetId(sheetId)
                  .setDimension("COLUMNS")
                  .setStartIndex(column)
                  .setEndIndex(column + 1),
              ).setProperties(DimensionProperties().setPixelSize(width))
              .setFields("pixelSize"),
          )
      }.toList()

  private fun generateUpdateRequests(
    data: List<List<FormattedCell>>,
    sheetId: Int?,
  ): List<Request> =
    data.flatMapIndexed { rowIndex, row ->
      val rowExtended = row.flatMap { cell -> List(cell.width) { cell } }

      listOf(
        Request()
          .setUpdateCells(
            UpdateCellsRequest()
              .setRange(
                gridRange(
                  sheetId,
                  rowIndex,
                  rowIndex + 1,
                  0,
                  row.fold(0) { acc, cell -> acc + cell.width },
                ),
              ).setRows(listOf(RowData().setValues(rowExtended.map { it.toCellData() })))
              .setFields(
                "userEnteredValue," +
                  "userEnteredFormat.textFormat.bold," +
                  "userEnteredFormat.horizontalAlignment," +
                  "userEnteredFormat.Borders",
              ),
          ),
      )
    }

  private fun gridRange(
    sheetId: Int?,
    startRow: Int,
    endRow: Int,
    startColumn: Int,
    endColumn: Int,
  ): GridRange =
    GridRange()
      .setSheetId(sheetId)
      .setStartRowIndex(startRow)
      .setEndRowIndex(endRow + 1)
      .setStartColumnIndex(startColumn)
      .setEndColumnIndex(endColumn)
}
