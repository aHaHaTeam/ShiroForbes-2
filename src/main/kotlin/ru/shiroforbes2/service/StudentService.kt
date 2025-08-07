package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.StudentProfileDTO
import ru.shiroforbes2.dto.toRatingDTO
import ru.shiroforbes2.dto.toTransactionDTO
import ru.shiroforbes2.repository.RatingRepository
import ru.shiroforbes2.repository.StudentRepository
import ru.shiroforbes2.repository.TransactionRepository
import kotlin.jvm.optionals.getOrNull

@Service
class StudentService(
  private val studentRepository: StudentRepository,
  private val ratingRepository: RatingRepository,
  private val transactionRepository: TransactionRepository,
) {
  @Transactional(readOnly = true)
  fun getStudentProfile(login: String): StudentProfileDTO? {
    val student = studentRepository.findStudentByLogin(login).getOrNull() ?: return null
    val ratings = ratingRepository.findAllByStudentId(student.id).map { it.toRatingDTO() }
    val transactions =
      transactionRepository
        .findAllByStudentIdOrderByDate(student.id)
        .map { it.toTransactionDTO() }
    return StudentProfileDTO(
      name = student.firstName + " " + student.lastName,
      group = student.group,
      login = student.login,
      score = student.score,
      total = student.total,
      ratings = ratings,
      transactions = transactions,
    )
  }

  fun updateStudentInvestingStatus(
    login: String,
    investingStatus: Boolean,
  ) = studentRepository.updateStudentInvestingStatus(login, investingStatus)
}
