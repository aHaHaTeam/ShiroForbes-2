package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
  @Transactional(readOnly = true)
  fun findAllByGroup(group: Group): List<Product>
}
