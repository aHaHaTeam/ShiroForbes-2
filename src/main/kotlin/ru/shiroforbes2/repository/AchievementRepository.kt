package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.AchievementDTO
import ru.shiroforbes2.entity.Achievement

@Repository
interface AchievementRepository : JpaRepository<Achievement, Long> {
  @Transactional(readOnly = true)
  @Query(
    """
    select new ru.shiroforbes2.dto.AchievementDTO(
      s.login, a.title, a.description, a.image, a.date 
    )
    from Student s left join Achievement a on s.id = a.user.id
    where s.login = :login
    order by a.date desc
      """,
  )
  fun findByUserLogin(
    @Param("login") login: String,
  ): List<AchievementDTO>
}
