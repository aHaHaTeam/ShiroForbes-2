package ru.shiroforbes2.dto

import ru.shiroforbes2.entity.Admin

data class AdminDTO(val login: String, val name: String)

fun Admin.toAdminDTO(): AdminDTO = AdminDTO(login = login, name = name)
