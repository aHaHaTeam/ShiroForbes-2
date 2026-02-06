package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.shiroforbes2.dto.StudentProfileDTO
import ru.shiroforbes2.dto.request.CreateAchievementRequest
import ru.shiroforbes2.dto.toRatingDTO
import ru.shiroforbes2.entity.Achievement
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.repository.AchievementRepository
import ru.shiroforbes2.repository.RatingRepository
import ru.shiroforbes2.repository.StudentRepository
import ru.shiroforbes2.repository.UserRepository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class StudentService(
  private val studentRepository: StudentRepository,
  private val userRepository: UserRepository,
  private val ratingRepository: RatingRepository,
  private val achievementRepository: AchievementRepository,
) {
  @Transactional(readOnly = true)
  fun getStudentProfile(login: String): StudentProfileDTO? {
    val student = studentRepository.findStudentByLogin(login).getOrNull() ?: return null
    val ratings = ratingRepository.findAllByStudentId(student.id).map { it.toRatingDTO() }
    val achievements = achievementRepository.findByUserLogin(login)

    return StudentProfileDTO(
      name = student.firstName + " " + student.lastName,
      group = student.group,
      login = student.login,
      score = student.score,
      total = student.total,
      ratings = ratings,
      achievements = achievements,
    )
  }

  fun getStudentGroup(login: String): Group? = studentRepository.findStudentByLogin(login).getOrNull()?.group

  fun updateStudentInvestingStatus(
    login: String,
    investingStatus: Boolean,
  ) = studentRepository.updateStudentInvestingStatus(login, investingStatus)

  @Transactional
  fun addAchievement(
    login: String,
    request: CreateAchievementRequest,
  ) {
    val user =
      userRepository
        .findByLogin(login)
        .orElseThrow { IllegalArgumentException("User with login '$login' not found") }

    val achievement =
      Achievement(
        title = request.title,
        description = request.description,
        image = request.image,
        date = request.date ?: LocalDateTime.now(),
        user = user,
      )

    achievementRepository.save(achievement)
  }
}
