package ru.shiroforbes2.entity

import org.springframework.core.convert.converter.Converter

enum class Group(
  val value: String,
) {
  Urban1("Urban1"),
  Urban2("Urban2"),
  Countryside("Countryside"),
  ;

  companion object {
    private lateinit var countrysideRange: String
    private lateinit var urban1Range: String
    private lateinit var urban2Range: String

    fun initializeFromConfig(
      countrysideRange: String,
      urban1Range: String,
      urban2Range: String,
    ) {
      this.countrysideRange = countrysideRange
      this.urban1Range = urban1Range
      this.urban2Range = urban2Range
    }
  }

  val ratingRange: String
    get() =
      when (this) {
        Countryside -> countrysideRange
        Urban1 -> urban1Range
        Urban2 -> urban2Range
      }
}

class StringToGroup : Converter<String, Group> {
  override fun convert(source: String): Group = Group.valueOf(source.replaceFirstChar { it.titlecase() })
}
