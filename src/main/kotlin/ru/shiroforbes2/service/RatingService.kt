package ru.shiroforbes2.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.PerformanceStatistics
import ru.shiroforbes2.googlesheets.reader.RatingTableParser
import ru.shiroforbes2.googlesheets.reader.SheetReaderService
import ru.shiroforbes2.repository.RatingRepository
import ru.shiroforbes2.repository.StudentRepository
import java.time.LocalDateTime

@Service
class RatingService(
  private val studentRepository: StudentRepository,
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
    spreadsheetId: String,
    group: Group,
  ): List<List<Rating>> = sheetReaderService.getRows(spreadsheetId, group.ratingRange, RatingTableParser())

  @Transactional
  fun updateRatingForGroup(
    group: Group,
    ratings: List<Rating>,
  ) {
    ratingRepository.deleteAllByGroup(group)

    val studentsByName =
      studentRepository
        .findAll()
        .associateBy { "${it.firstName} ${it.lastName}" }

    val newPerformanceStats =
      ratings.mapNotNull { dto ->
        val student = studentsByName["${dto.firstName} ${dto.lastName}"]

        student?.let {
          PerformanceStatistics(
            id = 0,
            date = LocalDateTime.now(),
            episode = dto.episode,
            student = it,
            totalSolved = dto.solved,
            totalRating = dto.rating,
            position = ratings.indexOf(dto) + 1,
            algebra = 0.0f,
            numbersTheory = 0.0f,
            geometry = 0.0f,
            combinatorics = 0.0f,
            totalSolvedPercent = 0.0f,
            algebraSolvedPercent = 0.0f,
            numbersTheorySolvedPercent = 0.0f,
            geometrySolvedPercent = 0.0f,
            combinatoricsSolvedPercent = 0.0f,
            grobs = 0,
          )
        }
      }

    if (newPerformanceStats.isNotEmpty()) {
      ratingRepository.saveAll(newPerformanceStats)
    }
  }
}
