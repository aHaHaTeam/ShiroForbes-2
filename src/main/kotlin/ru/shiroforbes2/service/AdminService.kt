package ru.shiroforbes2.service

import org.springframework.stereotype.Service
import ru.shiroforbes2.dto.AdminDTO
import ru.shiroforbes2.repository.AdminRepository

@Service
class AdminService(
  private val adminRepository: AdminRepository,
) {
  fun getAllAdmins(): List<AdminDTO> =
    adminRepository.findAll().map { admin ->
      AdminDTO(login = admin.login, name = admin.name)
    }
}
