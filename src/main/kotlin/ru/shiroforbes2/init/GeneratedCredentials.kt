package ru.shiroforbes2.init

import kotlin.random.Random

fun String.transliterate() =
  this
    .lowercase()
    .asSequence()
    .map(Char::toString)
    .filter(alphabet::containsKey)
    .map(alphabet::get)
    .joinToString(separator = "")

fun randomPassword(): String {
  val password = StringBuilder()
  repeat(PASSWORD_LENGTH) {
    password.append(takeAny(consonants))
    password.append(takeAny(vowels))
  }
  return password.toString()
}

private fun takeAny(from: List<Char>) = from[random.nextInt(from.size)]

private const val PASSWORD_LENGTH = 4

private val random by lazy { Random(System.currentTimeMillis()) }

private val consonants: List<Char> by lazy {
  listOf('b', 'c', 'd', 'f', 'g', 'h', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'v', 'w', 'x', 'z')
}

private val vowels: List<Char> by lazy {
  listOf('a', 'e', 'i', 'o', 'u')
}

private val alphabet: Map<String, String> by lazy {
  hashMapOf(
    "а" to "a",
    "б" to "b",
    "в" to "v",
    "г" to "g",
    "д" to "d",
    "е" to "e",
    "ё" to "yo",
    "ж" to "zh",
    "з" to "z",
    "и" to "i",
    "й" to "y",
    "к" to "k",
    "л" to "l",
    "м" to "m",
    "н" to "n",
    "о" to "o",
    "п" to "p",
    "р" to "r",
    "с" to "s",
    "т" to "t",
    "у" to "u",
    "ф" to "f",
    "х" to "kh",
    "ц" to "ts",
    "ч" to "ch",
    "ш" to "sh",
    "щ" to "sch",
    "ъ" to "",
    "ы" to "y",
    "ь" to "",
    "э" to "e",
    "ю" to "yu",
    "я" to "ya",
  )
}
