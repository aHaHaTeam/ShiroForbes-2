package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.PrimaryKeyJoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "teacher")
@DiscriminatorValue("Teacher")
@PrimaryKeyJoinColumn(name = "teacher_id", referencedColumnName = "user_id")
class Teacher(
  login: String,
  password: String,
  @Column(name = "name", length = 200, nullable = false) var name: String,
) : User(login = login, password = password, rights = Rights.Teacher)
