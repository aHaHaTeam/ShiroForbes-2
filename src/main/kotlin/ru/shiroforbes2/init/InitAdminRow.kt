package ru.shiroforbes2.init

import ru.shiroforbes2.entity.Group

internal data class InitAdminRow(
  val id: Int,
  val name: String = "",
  val login: String = "",
  val password: String = "",
  val group: Group,
)
