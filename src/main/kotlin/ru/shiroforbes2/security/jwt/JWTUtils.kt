package ru.shiroforbes2.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JWTUtils {
  @Value("\${shiroforbes.app.jwt.secret}")
  private lateinit var jwtSecret: String

  @Value("\${shiroforbes.app.jwt.jwtExpirationMs}")
  private var jwtExpirationMs: Long = 0

  fun generateJwtToken(
    userLogin: String,
    expirationMs: Long = jwtExpirationMs,
  ): String =
    Jwts
      .builder()
      .setSubject(userLogin)
      .setIssuedAt(Date())
      .setExpiration(Date((Date()).time + expirationMs))
      .signWith(key(), SignatureAlgorithm.HS256)
      .compact()

  private fun key(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

  fun getLoginFromJwtToken(token: String?): String? =
    Jwts
      .parserBuilder()
      .setSigningKey(key())
      .build()
      .parseClaimsJws(token)
      .getBody()
      .subject

  fun validateJwtToken(authToken: String?): Boolean {
    try {
      Jwts
        .parserBuilder()
        .setSigningKey(key())
        .build()
        .parse(authToken)
      return true
    } catch (e: MalformedJwtException) {
      logger.error("Invalid JWT token: {}", e.message)
    } catch (e: ExpiredJwtException) {
      logger.error("JWT token is expired: {}", e.message)
    } catch (e: UnsupportedJwtException) {
      logger.error("JWT token is unsupported: {}", e.message)
    } catch (e: IllegalArgumentException) {
      logger.error("JWT claims string is empty: {}", e.message)
    }

    return false
  }

  companion object {
    private val logger: Logger = LoggerFactory.getLogger(JWTUtils::class.java)
  }
}
