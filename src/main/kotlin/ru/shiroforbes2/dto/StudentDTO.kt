package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Student

class StudentDTO(
  val name: String,
  val group: Group,
  val login: String,
  val score: Int,
  val total: Float,
)

fun Student.toStudentDTO(): StudentDTO =
  StudentDTO(
    login = login,
    name = name,
    group = group,
    score = score,
    total = total,
  )
