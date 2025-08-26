package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Product

data class ProductDTO(
  val id: Int,
  val group: Group,
  val name: String,
  val price: Long,
)

fun Product.toProductDTO(): ProductDTO = ProductDTO(id, group, name, price)

fun ProductDTO.toProduct(): Product = Product(id, group, name, price)
