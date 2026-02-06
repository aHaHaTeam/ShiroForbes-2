package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "achievement")
class Achievement(
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null,
  @Column(name = "title", length = 200, nullable = false) var title: String,
  @Column(name = "description", length = 1000, nullable = false) var description: String,
  @Column(name = "image", length = 1000, nullable = false) var image: String,
  @Column(name = "date", nullable = true) var date: LocalDateTime,
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false) var user: User,
)
