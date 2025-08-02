package ru.shiroforbes2.googlesheets

import ru.shiroforbes2.dto.Episode

class RatingTableParser : TableParser<List<Episode>> {
  override fun parse(table: List<List<String>>): List<List<Episode>> {
    val numbersRow = table.first()
    return table.drop(1).map { row ->
      var previous: Int? = null
      val grades = mutableListOf<Float>()
      val episodes = mutableListOf<Episode>()
      row.zip(numbersRow).forEach { (gradeString, seriesNumberString) ->
        val seriesNumber = seriesNumberString.toIntOrNull() ?: return@forEach
        val grade = parseGrade(gradeString) ?: 0f
        if (previous != seriesNumber && previous != null) {
          episodes.add(Episode(previous, grades.toList()))
          grades.clear()
        }
        previous = seriesNumber
        grades.add(grade)
      }
      if (grades.isNotEmpty()) {
        episodes.add(Episode(previous!!, grades))
      }
      return@map episodes.toList()
    }
  }

  private fun parseGrade(grade: String): Float? = grade.replace(",", ".").toFloatOrNull()
}
