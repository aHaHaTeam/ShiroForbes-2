package ru.shiroforbes2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class Shiroforbes2Application

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  System.setProperty("spring.config.encoding", "UTF-8")
  runApplication<Shiroforbes2Application>(*args)
}
