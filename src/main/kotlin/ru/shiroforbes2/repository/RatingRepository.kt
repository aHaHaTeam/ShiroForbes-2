package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.Rating
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.PerformanceStatistics

@Repository
interface RatingRepository : JpaRepository<PerformanceStatistics, Long> {
  @Transactional(readOnly = true)
  fun findAllByStudentId(studentId: Long): List<PerformanceStatistics>

  @Transactional(readOnly = true)
  @Query(
    """
      select new ru.shiroforbes2.dto.Rating(
        s.firstName, s.lastName,  coalesce(r.totalSolved, 0.0f), coalesce(r.totalRating, 0.0f), coalesce(r.episode, 0) 
      )
      from Student s left join PerformanceStatistics r on s.id = r.student.id
      where s.group = :group 
    order by r.episode, coalesce(r.position, 0)
      """,
  )
  fun getRawGroupRating(
    @Param("group") group: Group,
  ): List<Rating>
}
