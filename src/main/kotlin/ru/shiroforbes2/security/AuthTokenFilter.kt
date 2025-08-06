package ru.shiroforbes2.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import ru.shiroforbes2.security.jwt.JWTUtils
import ru.shiroforbes2.security.services.UserDetailsServiceImpl
import java.io.IOException

class AuthTokenFilter(
  private val jwtUtils: JWTUtils,
  private val userDetailsService: UserDetailsServiceImpl,
) : OncePerRequestFilter() {
  @Suppress("TooGenericExceptionCaught")
  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain,
  ) {
    try {
      val jwt = parseJwt(request)
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        val username = jwtUtils.getLoginFromJwtToken(jwt)!!

        val userDetails: UserDetails? = userDetailsService.loadUserByUsername(username)
        if (userDetails != null) {
          val authentication =
            UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.authorities,
            )
          authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

          SecurityContextHolder.getContext().authentication = authentication
        }
      }
    } catch (e: Exception) {
      Companion.logger.error("Cannot set user authentication: $e")
    }

    filterChain.doFilter(request, response)
  }

  private fun parseJwt(request: HttpServletRequest): String? {
    val headerAuth = request.getHeader("Authorization")

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring("Bearer ".length)
    }

    return null
  }

  companion object {
    private val logger: Logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)
  }
}
