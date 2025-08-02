package ru.shiroforbes2.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.shiroforbes2.security.jwt.JWTUtils
import ru.shiroforbes2.security.services.UserDetailsServiceImpl

@Configuration
@EnableMethodSecurity
class WebSecurityConfig(
  private var userDetailsService: UserDetailsServiceImpl,
  private val unauthorizedHandler: AuthEntryPointJwt,
  private val jwtUtils: JWTUtils,
) {
  @Bean
  fun authenticationJwtTokenFilter(): AuthTokenFilter = AuthTokenFilter(jwtUtils, userDetailsService)

  @Bean
  fun authenticationProvider(): DaoAuthenticationProvider {
    val authProvider = DaoAuthenticationProvider(userDetailsService)
    authProvider.setPasswordEncoder(passwordEncoder())

    return authProvider
  }

  @Bean
  fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager? =
    authConfig.authenticationManager

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .csrf { csrf -> csrf.disable() }
      .exceptionHandling { exception -> exception.authenticationEntryPoint(unauthorizedHandler) }
      .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .authorizeHttpRequests { auth ->
        auth
          .requestMatchers("/api/v2/auth/**")
          .permitAll()
          .requestMatchers("/api/v2/test/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      }

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

    return http.build()
  }
}
