package ru.shiroforbes2.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.shiroforbes2.entity.Group

@Component
class RangesConfig {
  @Value("\${shiroforbes.app.rating.camps.countryside}")
  private lateinit var countryside: String

  @Value("\${shiroforbes.app.rating.camps.urban1}")
  private lateinit var urban1: String

  @Value("\${shiroforbes.app.rating.camps.urban2}")
  private lateinit var urban2: String

  @PostConstruct
  fun initEnum() {
    Group.initializeFromConfig(countryside, urban1, urban2)
  }
}
