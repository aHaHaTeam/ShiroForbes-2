package ru.shiroforbes2.googlesheets

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.PerformanceStatistics
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.googlesheets.reader.PerformanceStatisticsParser
import ru.shiroforbes2.service.UserService
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.Test
import kotlin.test.assertContentEquals

@SpringBootTest
class PerformanceStatisticsTest {
  @Autowired
  private lateinit var parser: PerformanceStatisticsParser

  @Autowired
  private lateinit var users: UserService

  @Test
  fun `parse episode`() {
    val actual = parser.parse(loadRating()).toParsedStatistics()
    assertContentEquals(oneEpisodeRating(), actual)
  }

  private fun loadRating(): List<List<String>> {
    createStudent("Максим", "Бубнов")
    createStudent("Дмитрий", "Гулев")
    return Path("src/test/resources/test_rating.csv").readLines().map { it.trim().split(',') }
  }

  private fun createStudent(
    first: String,
    last: String,
  ) {
    users.createNewStudent(
      Student(
        login = first,
        password = "aaa",
        firstName = first,
        lastName = last,
        group = Group.Countryside,
        score = 0,
        total = 0F,
        isInvesting = false,
      ),
    )
  }

  private fun oneEpisodeRating(): List<ParsedStatistics> =
    listOf(
      ParsedStatistics(0, 1, 3F, 53F, 0F, 0F, 0F, 0F, 0.6F, 0F, 0F, 0.0F, 0.0F, 1, 1),
      ParsedStatistics(0, 2, 4F, 84F, 0F, 1F, 0F, 0F, 0.8F, 0F, 0.5F, 0.0F, 0.0F, 2, 0),
    )

  data class ParsedStatistics(
    var episode: Int,
    var student: Long,
    var totalSolved: Float,
    var totalRating: Float,
    val algebra: Float,
    val numbersTheory: Float,
    val geometry: Float,
    val combinatorics: Float,
    var totalSolvedPercent: Float,
    var algebraSolvedPercent: Float,
    var numbersTheorySolvedPercent: Float,
    var geometrySolvedPercent: Float,
    var combinatoricsSolvedPercent: Float,
    var grobs: Int,
    val position: Int,
  )

  private fun PerformanceStatistics.toParsedStatistics() =
    ParsedStatistics(
      episode = episode,
      student = student.id,
      totalSolved = totalSolved,
      totalRating = totalRating,
      algebra = algebra,
      numbersTheory = numbersTheory,
      geometry = geometry,
      combinatorics = combinatorics,
      totalSolvedPercent = totalSolvedPercent,
      algebraSolvedPercent = algebraSolvedPercent,
      numbersTheorySolvedPercent = numbersTheorySolvedPercent,
      geometrySolvedPercent = geometrySolvedPercent,
      combinatoricsSolvedPercent = combinatoricsSolvedPercent,
      grobs = grobs,
      position = position,
    )

  private fun List<PerformanceStatistics>.toParsedStatistics() = map { it.toParsedStatistics() }
}
