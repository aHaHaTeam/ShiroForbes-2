package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.googlesheets.RatingTableParser
import ru.shiroforbes2.repository.RatingRepository

@Service
class RatingService(
  private val sheetLoaderService: SheetLoaderService,
  private val ratingRepository: RatingRepository,
) {
  fun getGroupRating(group: Group): List<List<Rating>> =
    ratingRepository.getRawGroupRating(group.text)
      .groupBy { rating -> rating.episode }
      .entries
      .toList()
      .sortedBy { it.key }
      .map { it.value }


  fun getNewGroupRating(
    spreadsheet: String,
    group: Group
  ): List<List<Rating>> = sheetLoaderService.getRows(spreadsheet, group.ranges(), RatingTableParser())

}
