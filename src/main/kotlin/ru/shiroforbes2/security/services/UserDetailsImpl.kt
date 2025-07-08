package ru.shiroforbes2.security.services

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.shiroforbes2.entity.User

class UserDetailsImpl(
  val userId: Long,
  private val login: String,
  @field:JsonIgnore
  private val password: String,
  private val authority: GrantedAuthority,
) : UserDetails {
  override fun getAuthorities(): Collection<GrantedAuthority?>? = listOf(authority)

  override fun getPassword(): String = password

  override fun getUsername(): String = login

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = true

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val user = other as UserDetailsImpl
    return userId == user.userId
  }

  companion object {
    fun build(user: User): UserDetailsImpl {
      val authority: GrantedAuthority = SimpleGrantedAuthority(user.rights.name)

      return UserDetailsImpl(
        user.id,
        user.login,
        user.password,
        authority,
      )
    }
  }

  override fun hashCode(): Int {
    var result = userId.hashCode()
    result = 31 * result + login.hashCode()
    result = 31 * result + password.hashCode()
    result = 31 * result + authority.hashCode()
    return result
  }
}
