package ru.shiroforbes2.init

import ru.shiroforbes2.entity.Group

internal data class InitStudentRow(
  val id: Int,
  val name: String = "",
  val login: String = "",
  val password: String = "",
  val group: Group,
  val rating: Int = 0,
  val wealth: Int = 0,
  val totalSolved: Int = 0,
  val algebraSolved: Int = 0,
  val geometrySolved: Int = 0,
  val combinatoricsSolved: Int = 0,
  val isExercised: Boolean?,
  val isBeaten: Boolean?,
  val isInvesting: Boolean?,
) {
  fun firstName() = nameParts().first()

  fun lastName() = nameParts().last()

  private fun nameParts() = name.trim().split(" ")
}
