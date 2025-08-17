package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.googlesheets.reader.RatingTableParser
import ru.shiroforbes2.googlesheets.reader.SheetReaderService
import ru.shiroforbes2.repository.RatingRepository

@Service
class RatingService(
  private val ratingRepository: RatingRepository,
  private val sheetReaderService: SheetReaderService,
) {
  fun getGroupRating(group: Group): List<List<Rating>> =
    ratingRepository
      .getRawGroupRating(group)
      .groupBy { rating -> rating.episode }
      .entries
      .toList()
      .sortedBy { it.key }
      .map { it.value }

  fun getNewGroupRating(
    spreadsheet: String,
    group: Group,
  ): List<List<Rating>> = sheetReaderService.getRows(spreadsheet, group.ratingRange, RatingTableParser())

  fun updateRating(
    spreadsheet: String,
    group: Group
  ): Boolean {
    val rating = getNewGroupRating(spreadsheet, group)

    return true
  }
}
