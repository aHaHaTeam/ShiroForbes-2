package ru.shiroforbes2.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.dto.RatingDelta
import ru.shiroforbes2.service.RatingService

@RestController
@RequestMapping("/api/v2/rating")
class RatingController(
  private val ratingService: RatingService,
) {
  @Value("\${shiroforbes.app.rating.spreadsheetId}")
  val spreadsheetId: String = ""

  @Value("#{'\${shiroforbes.app.rating.countrysideRanges}'.split(',')}")
  val countrysideRanges: List<String> = emptyList()

  @Value("#{'\${shiroforbes.app.rating.urbanRanges}'.split(',')}")
  val urbanRanges: List<String> = emptyList()

  @GetMapping("/countryside/diff")
  fun getCountrysideRatingDiff(): List<RatingDelta> =
    ratingService.getCountrysideRatingDiff(spreadsheetId, countrysideRanges)

  @GetMapping("/countryside")
  fun getCountrysideRating(): List<Rating> = ratingService.getCountrysideRating()

  @PostMapping("/countryside")
  @PreAuthorize("hasAuthority('Admin')")
  fun forceUpdateCountrysideRating(): List<RatingDelta> =
    ratingService.getCountrysideRatingDiff(spreadsheetId, countrysideRanges)

  @GetMapping("/urban/diff")
  fun getUrbanRatingDiff(): List<RatingDelta> = ratingService.getUrbanRatingDiff(spreadsheetId, urbanRanges)

  @GetMapping("/urban")
  fun getUrbanRating(): List<Rating> = ratingService.getUrbanRating()

  @PostMapping("/urban")
  @PreAuthorize("hasAuthority('Admin')")
  fun forceUpdateUrbanRating(): List<RatingDelta> = ratingService.getUrbanRatingDiff(spreadsheetId, urbanRanges)
}
