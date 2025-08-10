package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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
      where s.group = :group
      order by t.date
     """,
  )
  fun findAllOrderByDate(@Param("group") group: String): List<Transaction>

  @Modifying
  @Transactional(readOnly = false)
  @Query(
    """
      with new_transactions as (
        select now(), student_id, ?2, ?3 from Student s where s.login in ?1
      )
      insert into Transaction (date, student_id, amount, message) values new_transactions
     """,
    nativeQuery = true,
  )
  fun insertTransactions(
    logins: List<String>,
    amount: Long,
    message: String,
  )
}
