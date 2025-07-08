package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long = 0,
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  val user: User,
  @Column(nullable = false, unique = true)
  val token: String,
  @Column(nullable = false)
  val expirationDate: Instant,
)
