package ru.shiroforbes2.init

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.shiroforbes2.entity.Admin
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.entity.Teacher
import ru.shiroforbes2.service.SheetLoaderService
import ru.shiroforbes2.service.UserService
import ru.shiroforbes2.service.reflectiveParser

@Component
class DatabaseInit : ApplicationRunner {
  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var loaderService: SheetLoaderService

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
    loaderService
      .getRows(spreadsheet, students, reflectiveParser(InitStudentRow::class))
      .map {
        Student(it.login(), it.password(), it.firstName(), it.lastName(), it.group, 0, 0F)
      }.forEach(userService::createNewStudent)
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
