package ru.shiroforbes2.init

internal data class InitAdminRow(
  val id: Int,
  val name: String,
  val login: String = "",
  val password: String = "",
) : WithLogin {
  override fun login(): String =
    name
      .split(" ")
      .first()
      .lowercase()
      .transliterate()

  override fun storedPassword(): String = password
}
