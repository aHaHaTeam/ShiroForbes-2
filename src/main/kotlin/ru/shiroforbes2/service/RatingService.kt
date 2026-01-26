package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.googlesheets.reader.PerformanceStatisticsParser
import ru.shiroforbes2.googlesheets.reader.RatingTableParser
import ru.shiroforbes2.googlesheets.reader.SheetReaderService
import ru.shiroforbes2.repository.RatingRepository

@Service
class RatingService(
  private val ratingRepository: RatingRepository,
  private val sheetReaderService: SheetReaderService,
  private val performanceStatisticsParser: PerformanceStatisticsParser,
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

  @Transactional(readOnly = false)
  fun updateRating(
    spreadsheet: String,
    group: Group,
  ) {
    val rating = sheetReaderService.getRows(spreadsheet, group.ratingRange, performanceStatisticsParser)
    val episode = rating.first().episode
    require(rating.all { it.episode == episode }) { "All PerformanceStatistics should be from the same episode" }
    ratingRepository.deleteAllByEpisode(episode, group)
    ratingRepository.saveAll(rating)
  }
}
