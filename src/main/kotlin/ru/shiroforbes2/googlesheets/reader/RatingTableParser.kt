package ru.shiroforbes2.googlesheets.reader

import ru.shiroforbes2.dto.Rating
import java.util.LinkedList

class RatingTableParser : TableParser<List<Rating>> {
  override fun parse(table: List<List<String>>): List<List<Rating>> =
    table
      .drop(HEADER_HEIGHT)
      .map { table.first().zip(it) }
      .map { it.parsePrices() }
      .map {
        table
          .first()
          .zip(table[PRICES_POSITION])
          .parsePrices()
          .zip(it)
      }.map { student -> student.map { it.first.zip(it.second) } }
      .map { student -> student.map { it.accumulateEpisode() } }
      .map {
        it.runningFold(0f to 0f) { acc, pair -> acc.first + pair.first to acc.second + pair.second }
      }.zip(table.parseNames())
      .map { student ->
        student.first.ratings(student.second, table.first())
      }.drop(1)

  private fun List<Pair<Float, Float>>.ratings(
    names: Pair<String, String>,
    episodes: List<String>,
  ): List<Rating> =
    zip(episodes.withZeroth()).map {
      Rating(
        names.second,
        names.first,
        it.first.second,
        it.first.first,
        it.second,
      )
    }

  private fun List<String>.withZeroth(): List<Int> {
    val extended = LinkedList(this.mapNotNull { it.toIntOrNull() })
    extended.addFirst(-1)
    return extended.distinct()
  }

  private fun List<Pair<Float, Float>>.accumulateEpisode(): Pair<Float, Float> =
    map { it.first * it.second to it.second }
      .reduce { acc, pair -> acc.first + pair.first * pair.second to acc.second + pair.second }

  private fun List<Pair<String, String>>.parsePrices(): List<List<Float>> =
    filter { it.first.isNotEmpty() }
      .map { it.first.toIntOrNull() to it.second }
      .filter { it.first != null }
      .map { it.first!! to it.second }
      .groupBy { it.first }
      .entries
      .sortedBy { it.key }
      .map { it.value.parseEpisode() }

  private fun List<Pair<Int, String>>.parseEpisode(): List<Float> = map { it.second.parseGoogleFloat() }
}
