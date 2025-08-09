package ru.shiroforbes2.init

import ru.shiroforbes2.entity.Group

internal data class InitTeacherRow(
  val id: Int,
  val name: String,
  val login: String = "",
  val password: String = "",
  val group: Group,
) : WithLogin {
  override fun login(): String =
    name
      .split(" ")
      .first()
      .lowercase()
      .transliterate()

  override fun storedPassword(): String = password
}
