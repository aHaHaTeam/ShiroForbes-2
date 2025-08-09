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
  println("${login()} ${encode(password)} $password")
  return encode(password)!!
}

private fun encode(password: String): String? = BCryptPasswordEncoder().encode(password)
