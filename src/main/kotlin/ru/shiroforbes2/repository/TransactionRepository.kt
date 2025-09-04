package ru.shiroforbes2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.LoginWealthStatistics
import ru.shiroforbes2.dto.WealthStatistics
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.entity.Transaction

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
  @Transactional(readOnly = true)
  fun findAllByStudentIdOrderByDate(studentId: Long): List<Transaction>

  @Transactional(readOnly = true)
  @Query(
    """
      select t from Transaction t join Student s on t.studentId = s.id
      where s.login = :login
      order by t.date
     """,
  )
  fun findAllByStudentLoginOrderByDate(
    @Param("login") login: String,
  ): List<Transaction>

  @Transactional(readOnly = true)
  @Query(
    """
      select t from Transaction t join Student s on t.studentId = s.id
      where s.group = :group
      order by t.date
     """,
  )
  fun findAllOrderByDate(
    @Param("group") group: Group,
  ): List<Transaction>

  @Modifying
  @Transactional(readOnly = false)
  @Query(
    """
      with new_transactions as (
        select now(), s.student_id, ?2, ?3
        from student s
        join "user" u on s.student_id = u.user_id
        where u.login in ?1
      )
      insert into "transaction" (date, student_id, amount, message)
      select * from new_transactions
     """,
    nativeQuery = true,
  )
  fun insertTransactions(
    logins: List<String>,
    amount: Long,
    message: String,
  )

  @Transactional(readOnly = true)
  @Query(
    """
    select new ru.shiroforbes2.dto.WealthStatistics(
      coalesce(sum(t.amount), 0), 
      coalesce(sum(case when t.amount < 0 then -t.amount else 0 end), 0), 
      count(t),
      max(s.firstName),
      max(s.lastName)
    ) 
    from Transaction t join Student s on t.studentId = s.id
    where s.login = :login
  """,
  )
  fun getStudentWealth(
    @Param("login") login: String,
  ): WealthStatistics

  @Transactional(readOnly = true)
  @Query(
    """
    select new ru.shiroforbes2.dto.LoginWealthStatistics(
      s.login,
      coalesce(sum(t.amount), 0),
      coalesce(sum(case when t.amount < 0 then -t.amount else 0 end), 0),
      count(t),
      s.firstName,
      s.lastName
    )
    from Transaction t join Student s on t.studentId = s.id
    where s.group = :group
    group by s.login
  """,
  )
  fun getGroupWealth(
    @Param("group") group: Group,
  ): List<LoginWealthStatistics>
}
