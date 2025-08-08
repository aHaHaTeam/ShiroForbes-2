package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.dto.RatingDelta
import ru.shiroforbes2.googlesheets.SheetsRatingRow
import ru.shiroforbes2.repository.RatingRepository

@Service
class RatingService(
  private val sheetLoaderService: SheetLoaderService,
  private val ratingRepository: RatingRepository,
) {
  fun getCountrysideRating(): List<Rating> = ratingRepository.getCountrysideRating()

  fun getUrbanRating(): List<Rating> = ratingRepository.getUrbanRating()

  fun getCountrysideRatingDiff(
    spreadsheetId: String,
    countrysideRanges: List<String>,
  ): List<RatingDelta> {
    val newRatings = sheetLoaderService.getRows(spreadsheetId, countrysideRanges, SheetsRatingRow::class)
    val storedRatings =
      ratingRepository
        .getCountrysideRating()
        .associateBy { it.lastName.trim() + " " + it.firstName.trim() }
    return computeDelta(newRatings, storedRatings)
  }

  fun getUrbanRatingDiff(
    spreadsheetId: String,
    urbanRanges: List<String>,
  ): List<RatingDelta> {
    val newRatings = sheetLoaderService.getRows(spreadsheetId, urbanRanges, SheetsRatingRow::class)
    val storedRatings =
      ratingRepository
        .getUrbanRating()
        .associateBy { it.lastName.trim() + " " + it.firstName.trim() }
    return computeDelta(newRatings, storedRatings)
  }

  private fun computeDelta(
    newRatings: List<SheetsRatingRow>,
    storedRatings: Map<String, Rating>,
  ): List<RatingDelta> =
    newRatings
      .sortedByDescending { it.rating }
      .mapIndexedNotNull { index, new ->
        val old = storedRatings[new.lastName.trim() + " " + new.firstName.trim()] ?: return@mapIndexedNotNull null
        RatingDelta(
          firstName = new.firstName,
          lastName = new.lastName,
          login = old.login,
          oldRank = old.rank,
          newRank = index + 1,
          solved = new.solvedProblems,
          solvedDelta = new.solvedProblems - old.solved,
          rating = new.rating,
          ratingDelta = new.rating - old.rating,
        )
      }
}
