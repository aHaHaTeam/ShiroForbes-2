package ru.shiroforbes2.entity

import org.springframework.core.convert.converter.Converter

enum class Group(
  val text: String,
  val ranges: String,
) {
  Urban1("Urban1", "\${shiroforbes.app.rating.camps.urban1}"),
  Urban2("Urban2", "\${shiroforbes.app.rating.camps.urban2}"),
  Countryside("Countryside", "\${shiroforbes.app.rating.camps.countryside}"),
}

class StringToGroup : Converter<String, Group> {
  override fun convert(source: String): Group = Group.valueOf(source.replaceFirstChar { it.titlecase() })
}
