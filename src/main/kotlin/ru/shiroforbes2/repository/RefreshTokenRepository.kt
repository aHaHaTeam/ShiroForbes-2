package ru.shiroforbes2.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.shiroforbes2.entity.RefreshToken
import ru.shiroforbes2.entity.User
import java.util.Optional

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
  fun findByToken(token: String): Optional<RefreshToken>

  @Transactional
  fun deleteByUser(user: User)
}
