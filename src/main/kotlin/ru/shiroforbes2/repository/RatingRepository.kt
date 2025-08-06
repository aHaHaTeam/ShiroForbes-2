package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.PerformanceStatistics

@Repository
interface RatingRepository : JpaRepository<PerformanceStatistics, Long> {
  @Transactional(readOnly = true)
  fun findAllByStudentId(studentId: Long): List<PerformanceStatistics>

  @Transactional(readOnly = true)
  @Query(
    """
      select new ru.shiroforbes2.dto.Rating(
        s.firstName, s.lastName, s.login,
        coalesce(r.position, 0), coalesce(r.totalSolved, 0.0f), coalesce(r.totalRating, 0.0f)
      )
      from Student s left join PerformanceStatistics r on s.id = r.student.id
      where s.group = 'Countryside' and r.episode = (
        select max(r2.episode) from PerformanceStatistics r2 where r2.student.id = s.id
      )
      """,
  )
  fun getCountrysideRating(): List<Rating>

  @Transactional(readOnly = true)
  @Query(
    """
      select new ru.shiroforbes2.dto.Rating(
        s.firstName, s.lastName, s.login,
        coalesce(r.position, 0), coalesce(r.totalSolved, 0.0f), coalesce(r.totalRating, 0.0f)
      )
      from Student s left join PerformanceStatistics r on s.id = r.student.id
      where s.group = 'Urban' and r.episode = (
        select max(r2.episode) from PerformanceStatistics r2 where r2.student.id = s.id
      )
      """,
  )
  fun getUrbanRating(): List<Rating>
}
