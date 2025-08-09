package ru.shiroforbes2.init

import kotlin.random.Random

fun String.transliterate() =
  this
    .asSequence()
    .filter(alphabet::containsKey)
    .map(alphabet::get)
    .joinToString(separator = "") { it.toString() }

fun randomPassword(): String {
  val password: StringBuilder = StringBuilder()
  repeat(4) {
    password.append(takeAny(consonants))
    password.append(takeAny(vowels))
  }
  return password.toString()
}

private fun takeAny(from: List<Char>) = from[random.nextInt(from.size)]

private val random by lazy { Random(System.currentTimeMillis()) }

private val consonants: List<Char> by lazy {
  listOf('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z')
}

private val vowels: List<Char> by lazy {
  listOf('a', 'e', 'i', 'o', 'u')
}

private val alphabet: Map<Char, Char> by lazy {
  hashMapOf(
    'A' to 'А',
    'B' to 'Б',
    'V' to 'В',
    'G' to 'Г',
    'D' to 'Д',
    'E' to 'Е',
    'Ë' to 'Ё',
    'Ž' to 'Ж',
    'Z' to 'З',
    'I' to 'И',
    'J' to 'Й',
    'K' to 'К',
    'L' to 'Л',
    'M' to 'М',
    'N' to 'Н',
    'O' to 'О',
    'P' to 'П',
    'R' to 'Р',
    'S' to 'С',
    'T' to 'Т',
    'U' to 'У',
    'F' to 'Ф',
    'H' to 'Х',
    'C' to 'Ц',
    'Č' to 'Ч',
    'Š' to 'Ш',
    'Ŝ' to 'Щ',
    'È' to 'Э',
    'Û' to 'Ю',
    'Â' to 'Я',
    'a' to 'а',
    'b' to 'б',
    'v' to 'в',
    'g' to 'г',
    'd' to 'д',
    'e' to 'е',
    'ë' to 'ё',
    'ž' to 'ж',
    'z' to 'з',
    'i' to 'и',
    'j' to 'й',
    'k' to 'к',
    'l' to 'л',
    'm' to 'м',
    'n' to 'н',
    'o' to 'о',
    'p' to 'п',
    'r' to 'р',
    's' to 'с',
    't' to 'т',
    'u' to 'у',
    'f' to 'ф',
    'h' to 'х',
    'c' to 'ц',
    'č' to 'ч',
    'š' to 'ш',
    'ŝ' to 'щ',
    'y' to 'ы',
    'è' to 'э',
    'û' to 'ю',
    'â' to 'я',
  ).entries.associate { it.value to it.key }
}
