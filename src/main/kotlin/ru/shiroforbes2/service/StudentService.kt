package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.StudentDTO
import ru.shiroforbes2.dto.toStudentDTO
import ru.shiroforbes2.repository.StudentRepository

@Service
class StudentService(
  private val studentRepository: StudentRepository,
) {
  fun getAllStudents(): List<StudentDTO> = studentRepository.findAll().map { student -> student.toStudentDTO() }
}
