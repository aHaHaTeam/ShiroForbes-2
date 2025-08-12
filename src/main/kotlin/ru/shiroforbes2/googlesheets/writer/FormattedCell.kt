package ru.shiroforbes2.googlesheets.writer

import com.google.api.services.sheets.v4.model.Border
import com.google.api.services.sheets.v4.model.Borders
import com.google.api.services.sheets.v4.model.CellData
import com.google.api.services.sheets.v4.model.CellFormat
import com.google.api.services.sheets.v4.model.ErrorValue
import com.google.api.services.sheets.v4.model.ExtendedValue
import com.google.api.services.sheets.v4.model.TextFormat

internal enum class DataType {
  STRING,
  LONG,
  DOUBLE,
  FORMULA,
  BOOL,
  ERROR,
}

internal class FormattedCell(
  data: String = "",
  dataType: DataType = DataType.STRING,
  val width: Int = 1,
) {
  private val cellFormat =
    CellFormat().setTextFormat(TextFormat().setBold(false)).setBorders(Borders())

  private val cellData: CellData =
    CellData().setUserEnteredValue(parse(data, dataType)).setUserEnteredFormat(cellFormat)

  fun bold(): FormattedCell {
    cellData.userEnteredFormat = cellFormat.setTextFormat(TextFormat().setBold(true))
    return this
  }

  fun borders(width: Int = 1): FormattedCell {
    cellFormat.borders.top = Border().setWidth(width).setStyle("SOLID")
    cellFormat.borders.bottom = Border().setWidth(width).setStyle("SOLID")
    cellFormat.borders.left = Border().setWidth(width).setStyle("SOLID")
    cellFormat.borders.right = Border().setWidth(width).setStyle("SOLID")
    return this
  }

  fun rightBorder(width: Int = 1): FormattedCell {
    cellFormat.borders.right = Border().setWidth(width).setStyle("SOLID")
    return this
  }

  fun leftBorder(width: Int = 1): FormattedCell {
    cellFormat.borders.left = Border().setWidth(width).setStyle("SOLID")
    return this
  }

  fun topBorder(width: Int = 1): FormattedCell {
    cellFormat.borders.top = Border().setWidth(width).setStyle("SOLID")
    return this
  }

  fun bottomBorder(width: Int = 1): FormattedCell {
    cellFormat.borders.bottom = Border().setWidth(width).setStyle("SOLID")
    return this
  }

  fun centerAlign(): FormattedCell {
    cellData.userEnteredFormat = cellFormat.setHorizontalAlignment("CENTER")
    return this
  }

  private fun parse(
    data: String,
    dataType: DataType,
  ): ExtendedValue =
    when (dataType) {
      DataType.STRING -> ExtendedValue().setStringValue(data)
      DataType.LONG -> ExtendedValue().setNumberValue(data.toLongOrNull()?.toDouble())
      DataType.DOUBLE -> ExtendedValue().setNumberValue(data.toDoubleOrNull())
      DataType.FORMULA -> ExtendedValue().setFormulaValue(data)
      DataType.BOOL -> ExtendedValue().setBoolValue(data.toBooleanStrictOrNull())
      DataType.ERROR -> ExtendedValue().setErrorValue(ErrorValue().setMessage(data))
    }

  fun toCellData(): CellData = cellData
}
