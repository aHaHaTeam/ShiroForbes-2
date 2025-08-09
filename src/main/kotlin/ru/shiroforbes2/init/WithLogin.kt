package ru.shiroforbes2.init

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

interface WithLogin {
  fun login(): String

  fun storedPassword(): String
}

fun WithLogin.password(): String {
  if (storedPassword().isNotBlank()) {
    return storedPassword()
  }
  val password = randomPassword()
  println("${login()}: $password")
  return BCryptPasswordEncoder().encode(password)
}
