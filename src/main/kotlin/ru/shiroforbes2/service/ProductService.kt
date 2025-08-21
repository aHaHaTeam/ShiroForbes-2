package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.ProductDTO
import ru.shiroforbes2.dto.toProduct
import ru.shiroforbes2.dto.toProductDTO
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.repository.ProductRepository

@Service
class ProductService(
  private val productRepository: ProductRepository,
) {
  fun getGroupProducts(group: Group): List<ProductDTO> =
    productRepository.findAllByGroup(group).map { it.toProductDTO() }

  fun addProducts(products: List<ProductDTO>) {
    productRepository.saveAll(products.map { it.toProduct() })
  }

  fun removeProducts(ids: List<Long>) {
    productRepository.deleteAllById(ids)
  }
}
