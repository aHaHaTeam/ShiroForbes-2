package ru.shiroforbes2.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.ProductDTO
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.service.ProductService

@RestController
@RequestMapping("/api/v2/price-list")
class ProductController(
  private val productService: ProductService,
) {
  @GetMapping("/{group}")
  fun getRating(
    @PathVariable group: Group,
  ): List<ProductDTO> = productService.getGroupProducts(group)
}
