package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.PrimaryKeyJoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "admin")
@DiscriminatorValue("Admin")
@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "user_id")
class Admin(
  login: String,
  password: String,
  @Column(name = "name", length = 200, nullable = false) var name: String,
) : User(login = login, password = password, rights = Rights.Admin)
