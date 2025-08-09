package ru.shiroforbes2.init

import ru.shiroforbes2.entity.Group

internal data class InitStudentRow(
  val id: Int,
  val name: String,
  val group: Group,
  val login: String = "",
  val password: String = "",
) : WithLogin {
  fun lastName() = nameParts().last()

  private fun nameParts() = name.trim().split(" ")

  fun firstName() = nameParts().first()

  override fun login(): String =
    lastName()
      .lowercase()
      .transliterate()
      .plus(firstName().lowercase().substring(0, 1).transliterate())

  override fun storedPassword(): String = password
}
