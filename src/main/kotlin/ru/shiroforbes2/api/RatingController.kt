package ru.shiroforbes2.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.service.RatingService

@RestController
@RequestMapping("/api/v2/rating")
class RatingController(
  private val ratingService: RatingService,
) {
  @Value("\${shiroforbes.app.rating.spreadsheetId}")
  val spreadsheetId: String = ""

  @Value("\${shiroforbes.app.rating.countrysideRanges}")
  val countrysideRanges: List<String> = emptyList()

  @Value("\${shiroforbes.app.rating.urban1Ranges}")
  val urban1Ranges: List<String> = emptyList()

  @Value("\${shiroforbes.app.rating.urban2Ranges}")
  val urban2Ranges: List<String> = emptyList()

  @GetMapping("/{group}")
  @PreAuthorize("hasAuthority('Admin')")
  fun forceUpdateGroupRating(
    @PathVariable group: Group,
  ): List<List<Rating>> = ratingService.getNewGroupRating(spreadsheetId, group)

  @GetMapping("/{group}")
  fun getRating(
    @PathVariable group: Group,
  ): List<List<Rating>> = ratingService.getGroupRating(group)
}
