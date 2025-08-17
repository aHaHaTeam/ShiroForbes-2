package ru.shiroforbes2.googlesheets.reader

internal const val HEADER_HEIGHT = 4
internal const val PRICES_POSITION = 2
internal const val FIRST_NAME_POSITION = 2
internal const val LAST_NAME_POSITION = 1
internal const val TOTAL_SOLVED_POSITION = 3
internal const val RATING_POSITION = 4
internal const val GROB_POSITION = 5

internal fun List<List<String>>.parseNames(): List<Pair<String, String>> =
  drop(HEADER_HEIGHT).map {
    it[LAST_NAME_POSITION].trim() to it[FIRST_NAME_POSITION].trim()
  }

internal fun String.parseGoogleFloat(): Float = replace(",", ".").toFloatOrNull() ?: 0f
