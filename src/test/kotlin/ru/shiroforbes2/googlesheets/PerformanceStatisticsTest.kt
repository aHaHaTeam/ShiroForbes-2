package ru.shiroforbes2.googlesheets

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.googlesheets.reader.ParsedStatictics
import ru.shiroforbes2.googlesheets.reader.RawPerformanceStatistics
import ru.shiroforbes2.repository.StudentRepository
import ru.shiroforbes2.service.UserService
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.test.Test
import kotlin.test.assertContentEquals

@SpringBootTest
class PerformanceStatisticsTest {

  @Autowired
  private lateinit var students: StudentRepository

  @Autowired
  private lateinit var users: UserService

  @Test
  fun `parse episode`() =
    assertContentEquals(oneEpisodeRating(), RawPerformanceStatistics(students).parse(loadRating()))

  private fun loadRating(): List<List<String>> {
    createStudent("Максим", "Бубнов")
    createStudent("Дмитрий", "Гулев")
    return Path("src/test/resources/test_rating.csv").readLines().map { it.trim().split(',') }
  }

  private fun createStudent(first: String, last: String) {
    users.createNewStudent(
      Student(
        login = first,
        password = "aaa",
        firstName = first,
        lastName = last,
        group = Group.Countryside,
        score = 0,
        total = 0F,
        isInvesting = false
      )
    )
  }

  private fun oneEpisodeRating(): List<ParsedStatictics> =
    listOf(
      ParsedStatictics(0, 1, 3F, 53F, 0F, 0F, 0F, 0F, 0.6F, 0F, 0F, 0.0F, 0.0F, 1, 1),
      ParsedStatictics(0, 2, 4F, 84F, 0F, 1F, 0F, 0F, 0.8F, 0F, 0.5F, 0.0F, 0.0F, 2, 0),
    )
}
