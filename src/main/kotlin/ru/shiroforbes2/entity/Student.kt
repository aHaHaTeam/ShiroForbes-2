package ru.shiroforbes2.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.PrimaryKeyJoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "student")
@DiscriminatorValue("Student")
@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "user_id")
class Student(
  login: String,
  password: String,
  @Column(name = "name", length = 200, nullable = false)
  var name: String,

  @Column(name = "group", nullable = false)
  @Enumerated(EnumType.STRING)
  val group: Group,

  @Column(name = "score", nullable = false)
  var score: Int,

  @Column(name = "total", nullable = false)
  var total: Float,
) : User(login = login, password = password, rights = Rights.Student)
