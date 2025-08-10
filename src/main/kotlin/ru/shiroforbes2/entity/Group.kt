package ru.shiroforbes2.entity

import org.springframework.beans.factory.annotation.Value

enum class Group(
  val text: String,
) {
  Urban1("Urban1"),
  Urban2("Urban2"),
  Countryside("Countryside");

  fun ranges(): String {
    when (this) {
      Urban1 -> TODO()
      Urban2 -> TODO()
      Countryside -> TODO()
    }
  }

  @Value("\${shiroforbes.app.rating.students}")
  val students: String = ""

  @Value("\${shiroforbes.app.rating.admins}")
  val admins: String = ""

  @Value("\${shiroforbes.app.rating.teachers}")
  val teachers: String = ""
}
