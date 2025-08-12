package ru.shiroforbes2.googlesheets.reader

import kotlin.reflect.KType

interface Decoder {
  fun supports(type: KType): Boolean

  fun convert(
    string: String,
    type: KType,
  ): Any?
}
