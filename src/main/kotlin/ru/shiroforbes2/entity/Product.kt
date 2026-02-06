package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product")
class Product(
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int,
  @Column(name = "group", nullable = false)
  var group: Group,
  @Column(name = "name", nullable = false)
  var name: String,
  @Column(name = "price", nullable = false)
  var price: Long,
)
