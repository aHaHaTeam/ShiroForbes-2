package ru.shiroforbes2.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.shiroforbes2.entity.RefreshToken
import ru.shiroforbes2.repository.RefreshTokenRepository
import ru.shiroforbes2.repository.UserRepository
import java.time.Instant
import java.util.Optional
import java.util.UUID

@Service
class RefreshTokenService(
  private val refreshTokenRepository: RefreshTokenRepository,
  private val userRepository: UserRepository,
) {
  @Value("\${shiroforbes.app.jwt.refreshExpirationMs}")
  private var refreshExpirationMs: Long = 0

  fun findByToken(token: String): Optional<RefreshToken> = refreshTokenRepository.findByToken(token)

  fun createRefreshToken(userId: Long): RefreshToken {
    val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }
    refreshTokenRepository.deleteByUser(user)
    val refreshToken =
      RefreshToken(
        user = user,
        token = UUID.randomUUID().toString(),
        expirationDate = Instant.now().plusMillis(refreshExpirationMs),
      )
    return refreshTokenRepository.save(refreshToken)
  }

  fun verifyExpiration(token: RefreshToken): Boolean {
    if (token.expirationDate.isBefore(Instant.now())) {
      refreshTokenRepository.delete(token)
      return false
    }
    return true
  }
}
