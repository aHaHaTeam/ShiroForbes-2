package ru.shiroforbes2.security.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.entity.User
import ru.shiroforbes2.repository.UserRepository

@Service
class UserDetailsServiceImpl : UserDetailsService {
  @Autowired
  var userRepository: UserRepository? = null

  @Transactional
  override fun loadUserByUsername(login: String): UserDetails? {
    val user: User? =
      userRepository
        ?.findByLogin(login)
        ?.orElseThrow { UsernameNotFoundException("User Not Found with login: $login") }

    return user?.let { UserDetailsImpl.build(it) }
  }
}
