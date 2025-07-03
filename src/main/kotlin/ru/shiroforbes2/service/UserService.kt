package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.entity.Admin
import ru.shiroforbes2.entity.Student
import ru.shiroforbes2.entity.Teacher
import ru.shiroforbes2.repository.AdminRepository
import ru.shiroforbes2.repository.StudentRepository
import ru.shiroforbes2.repository.TeacherRepository
import ru.shiroforbes2.repository.UserRepository

@Service
class UserService(
  private val adminRepository: AdminRepository,
  private val teacherRepository: TeacherRepository,
  private val studentRepository: StudentRepository,
  private val userRepository: UserRepository,
) {
  fun createNewAdmin(admin: Admin) {
    adminRepository.save(admin)
  }

  fun createNewTeacher(teacher: Teacher) {
    teacherRepository.save(teacher)
  }

  fun createNewStudent(student: Student) {
    studentRepository.save(student)
  }

  fun existsByLogin(login: String): Boolean = userRepository.existsByLogin(login)
}
