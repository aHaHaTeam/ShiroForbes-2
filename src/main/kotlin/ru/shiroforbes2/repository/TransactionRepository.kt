package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.entity.Transaction

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
  @Transactional(readOnly = true)
  fun findAllByStudentIdOrderByDate(studentId: Long): List<Transaction>

  @Transactional(readOnly = true)
  @Query(
    """
      select t from Transaction t join Student s on t.studentId = s.id
      where s.group = 'Countryside'
      order by t.date
     """,
  )
  fun findAllCountrysideOrderByDate(): List<Transaction>

  @Transactional(readOnly = true)
  @Query(
    """
      select t from Transaction t join Student s on t.studentId = s.id
      where s.group = 'Urban'
      order by t.date
     """,
  )
  fun findAllUrbanOrderByDate(): List<Transaction>
}
