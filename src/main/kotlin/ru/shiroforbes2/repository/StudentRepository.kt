package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.shiroforbes2.entity.Student
import java.util.Optional

@Repository
interface StudentRepository : JpaRepository<Student, Long> {
  fun findStudentByLogin(login: String): Optional<Student>

  @Modifying
  @Query(
    """
    UPDATE Student s
    SET s.isInvesting = :isInvesting
    WHERE s.login = :login
    """,
  )
  fun updateStudentInvestingStatus(
    @Param("login") login: String,
    @Param("isInvesting") investingStatus: Boolean,
  )
}
