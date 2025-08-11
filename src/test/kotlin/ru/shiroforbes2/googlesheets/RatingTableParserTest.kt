package ru.shiroforbes2.googlesheets

import ru.shiroforbes2.dto.Rating
import kotlin.test.Test
import kotlin.test.assertContentEquals

class RatingTableParserTest {
  @Test
  fun `parse episode`() = assertContentEquals(oneEpisodeRating(), RatingTableParser().parse(oneEpisodeTable()))

  private fun oneEpisodeTable(): List<List<String>> =
    listOf(
      listOf("", "", "", "", "1", "1", "2"),
      listOf("", "", "", "", "", "", ""),
      listOf("", "", "", "", "2", "3", "4"),
      listOf("", "", "", "", "", "", ""),
      listOf("", "Алатаев", "Руслан", "", "1", "-", ""),
    )

  private fun oneEpisodeRating(): List<List<Rating>> =
    listOf(
      listOf(
        Rating("Руслан", "Алатаев", 0f, 0f, -1),
        Rating("Руслан", "Алатаев", 1f, 2f, 1),
        Rating("Руслан", "Алатаев", 1f, 2f, 2),
      ),
    )
}
