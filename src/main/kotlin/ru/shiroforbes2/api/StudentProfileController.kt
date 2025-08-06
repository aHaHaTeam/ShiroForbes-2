package ru.shiroforbes2.api

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.StudentDTO
import ru.shiroforbes2.dto.StudentProfileDTO
import ru.shiroforbes2.service.StudentService

@RestController
@RequestMapping("/api/v2/profile")
class StudentProfileController(
  private val studentService: StudentService,
) {
  @GetMapping("/all")
  @PreAuthorize("hasAuthority('Admin')")
  fun getAllProfiles(): List<StudentDTO> = studentService.getAllStudents()

  @GetMapping("/{login}")
  @PreAuthorize(
    "hasAuthority('Admin') or hasAuthority('Teacher') or " +
      "(hasAuthority('Student') and #login == authentication.principal.username)",
  )
  fun getProfile(
    @PathVariable login: String,
  ): StudentProfileDTO? = studentService.getStudentProfile(login)
}
