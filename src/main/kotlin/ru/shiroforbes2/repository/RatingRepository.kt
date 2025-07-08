package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.entity.Rating

@Repository
interface RatingRepository : JpaRepository<Rating, Long> {
  @Transactional(readOnly = true)
  fun findAllByStudentId(studentId: Long): List<Rating>
}
