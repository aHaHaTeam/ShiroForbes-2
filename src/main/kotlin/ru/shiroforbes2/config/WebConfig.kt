package ru.shiroforbes2.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.shiroforbes2.entity.StringToGroup

@Configuration
class WebConfig : WebMvcConfigurer {
  override fun addFormatters(registry: FormatterRegistry) {
    registry.addConverter(StringToGroup())
    super.addFormatters(registry)
  }
}
