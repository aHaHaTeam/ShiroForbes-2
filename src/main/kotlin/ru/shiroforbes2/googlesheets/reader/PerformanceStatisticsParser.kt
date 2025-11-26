package ru.shiroforbes2.googlesheets.reader

import org.springframework.stereotype.Component
import ru.shiroforbes2.entity.Area
import ru.shiroforbes2.entity.PerformanceStatistics
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.entity.toArea
import ru.shiroforbes2.repository.StudentRepository
import java.time.LocalDateTime

@Component
class PerformanceStatisticsParser(
  private val studentRepository: StudentRepository,
) : TableParser<PerformanceStatistics> {
  override fun parse(table: List<List<String>>): List<PerformanceStatistics> {
    val ids = table.studentIds()
    val scores =
      table
        .drop(HEADER_HEIGHT)
        .mapIndexed { i, row -> i to row[RATING_POSITION].toInt() }
        .sortedByDescending { it.second }
        .map { it.first }
    val areas = table.areas()
    val result =
      table.drop(HEADER_HEIGHT).mapIndexed { i, row ->
        PerformanceStatistics(
          episode = table[0].mapNotNull { it.toIntOrNull() }.max(),
          student = ids[i],
          totalSolved = row[TOTAL_SOLVED_POSITION].parseGoogleFloat(),
          totalRating = row[RATING_POSITION].parseGoogleFloat(),
          algebra = row.studentArea(Area.Algebra, areas).first,
          numbersTheory = row.studentArea(Area.NumberTheory, areas).first,
          geometry = row.studentArea(Area.Geometry, areas).first,
          combinatorics = row.studentArea(Area.Combinatorics, areas).first,
          totalSolvedPercent = row[TOTAL_SOLVED_POSITION].parseGoogleFloat() / table.totalProblems(),
          algebraSolvedPercent = row.studentArea(Area.Algebra, areas).second,
          numbersTheorySolvedPercent = row.studentArea(Area.NumberTheory, areas).second,
          geometrySolvedPercent = row.studentArea(Area.Geometry, areas).second,
          combinatoricsSolvedPercent = row.studentArea(Area.Combinatorics, areas).second,
          grobs = row[GROB_POSITION].toInt(),
          position = scores.indexOf(i) + 1,
          date = LocalDateTime.now(),
        )
      }
    return result
  }

  private fun List<List<String>>.areas(): List<Area?> = drop(1).first().map { it.toArea() }

  private fun List<List<String>>.studentIds(): List<Student> {
    val names = parseNames()
    val fetched =
      studentRepository.findStudentsByFirstNameInAndLastNameIn(
        names.map { it.second },
        names.map { it.first },
      )
    return names.map { student ->
      fetched
        .firstOrNull {
          it.firstName == student.second && it.lastName == student.first
        } ?: throw IllegalArgumentException("Unknown student $student")
    }
  }

  private fun List<String>.studentArea(
    area: Area,
    areas: List<Area?>,
  ): Pair<Float, Float> {
    val idx = areas.indexOf(area)
    return this[idx].parseGoogleFloat() to this[idx + 1].parseGoogleFloat()
  }

  private fun List<List<String>>.totalProblems(): Int = first().mapNotNull { it.toIntOrNull() }.size
}
