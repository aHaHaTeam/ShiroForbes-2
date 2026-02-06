package ru.shiroforbes2.init

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.shiroforbes2.entity.Admin
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.entity.Teacher
import ru.shiroforbes2.googlesheets.reader.SheetReaderService
import ru.shiroforbes2.googlesheets.reader.reflectiveParser
import ru.shiroforbes2.service.UserService

@Component
class DatabaseInit(
  private val userService: UserService,
//  private val transactionService: TransactionService,
  private val loaderService: SheetReaderService,
) : ApplicationRunner {
  @Value("\${shiroforbes.app.rating.spreadsheetId}")
  val spreadsheet: String = ""

  @Value("\${shiroforbes.app.rating.students}")
  val students: String = ""

  @Value("\${shiroforbes.app.rating.admins}")
  val admins: String = ""

  @Value("\${shiroforbes.app.rating.teachers}")
  val teachers: String = ""

  override fun run(args: ApplicationArguments?) {
    if (args?.containsOption("init") != true) {
      return
    }
    userService.dropAll()
    createStudents()
    createAdmins()
    createTeachers()
  }

  private fun createStudents() {
    val rows =
      loaderService
        .getRows(spreadsheet, students, reflectiveParser(InitStudentRow::class))
        .map {
          Student(it.login(), it.password(), it.firstName(), it.lastName(), it.group, 0, 0F)
        }

    rows.forEach(userService::createNewStudent)

//    transactionService.insertTransactions(rows.map { it.login }, 0, "Initial transaction")
  }

  private fun createAdmins() {
    loaderService
      .getRows(spreadsheet, admins, reflectiveParser(InitAdminRow::class))
      .map {
        Admin(it.login(), it.password(), it.name)
      }.forEach(userService::createNewAdmin)
  }

  private fun createTeachers() {
    loaderService
      .getRows(spreadsheet, teachers, reflectiveParser(InitTeacherRow::class))
      .map {
        Teacher(it.login(), it.password(), it.name)
      }.forEach(userService::createNewTeacher)
  }
}
