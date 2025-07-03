package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "rights", discriminatorType = DiscriminatorType.STRING)
@DynamicUpdate
open class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  open var id: Long = 0L,
  @Column(name = "login", nullable = false, unique = true, length = 255)
  open var login: String,
  @Column(name = "password", nullable = false, length = 255)
  open var password: String,
  @Column(name = "rights", nullable = false, insertable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  open val rights: Rights,
) {
  override fun equals(other: Any?): Boolean =
    when (other) {
      is User -> (this.login == other.login)
      is Int -> other != 0
      else -> false
    }

  override fun hashCode(): Int = javaClass.hashCode()
}
