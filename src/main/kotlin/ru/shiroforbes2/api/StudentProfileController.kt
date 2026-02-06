package ru.shiroforbes2.api

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.StudentProfileDTO
import ru.shiroforbes2.dto.request.CreateAchievementRequest
import ru.shiroforbes2.dto.response.MessageResponse
import ru.shiroforbes2.service.StudentService

@RestController
@RequestMapping("/api/v2/{login}")
class StudentProfileController(
  private val studentService: StudentService,
) {
  @GetMapping("/profile")
  @PreAuthorize(
    "hasAuthority('Admin') or hasAuthority('Teacher') or " +
      "(hasAuthority('Student') and #login == authentication.principal.username)",
  )
  fun getProfile(
    @PathVariable login: String,
  ): StudentProfileDTO? = studentService.getStudentProfile(login)

  @PostMapping("/profile")
  @PreAuthorize("(hasAuthority('Student') and #login == authentication.principal.username)")
  fun changeInvestingStatus(
    @PathVariable login: String,
    @RequestBody isInvesting: Boolean,
  ) = studentService.updateStudentInvestingStatus(login, isInvesting)

  @PostMapping("/achievements")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun addAchievement(
    @PathVariable login: String,
    @RequestBody request: CreateAchievementRequest,
  ): ResponseEntity<MessageResponse> {
    studentService.addAchievement(login, request)
    return ResponseEntity.ok(MessageResponse("Achievement added successfully!"))
  }
}
