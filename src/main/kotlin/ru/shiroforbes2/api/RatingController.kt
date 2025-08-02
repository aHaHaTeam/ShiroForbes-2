package ru.shiroforbes2.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.googlesheets.RatingRow
import ru.shiroforbes2.service.RatingLoaderService

@RestController
@RequestMapping("/api/v2/rating")
class RatingController(
  private val ratingService: RatingLoaderService,
) {
  @Value("\${shiroforbes.app.rating.spreadsheetId}")
  val spreadsheetId: String = ""

  @Value("#{'\${shiroforbes.app.rating.countrysideRanges}'.split(',')}")
  val countrysideRanges: List<String> = emptyList()

  @Value("#{'\${shiroforbes.app.rating.urbanRanges}'.split(',')}")
  val urbanRanges: List<String> = emptyList()

  @GetMapping("/countryside")
  fun getCountrysideRating(): List<RatingRow> = ratingService.getRating(spreadsheetId, countrysideRanges)

  @PostMapping("/countryside")
  fun updateCountrysideRating(): List<RatingRow> = ratingService.getRating(spreadsheetId, urbanRanges)
}
