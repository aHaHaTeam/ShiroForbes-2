package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.shiroforbes2.entity.User
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
  fun findByLogin(login: String): Optional<User>

  fun existsByLogin(login: String): Boolean
}
