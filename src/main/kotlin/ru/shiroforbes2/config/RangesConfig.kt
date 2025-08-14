package ru.shiroforbes2.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RangesConfig(
  @Value("\${shiroforbes.app.rating.camps.countryside}")
  val countryside: String,
  @Value("\${shiroforbes.app.rating.camps.urban1}")
  val urban1: String,
  @Value("\${shiroforbes.app.rating.camps.urban2}")
  val urban2: String,
)
