package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Student

data class StudentDTO(
  val name: String,
  val group: Group,
  val login: String,
  val score: Int,
  val total: Float,
  val isInvesting: Boolean,
)

fun Student.toStudentDTO(): StudentDTO =
  StudentDTO(
    login = login,
    name = "$firstName $lastName",
    group = group,
    score = score,
    total = total,
    isInvesting = isInvesting,
  )
