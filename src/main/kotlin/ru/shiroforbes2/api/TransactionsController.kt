package ru.shiroforbes2.api

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.TransactionDTO
import ru.shiroforbes2.dto.request.CreateTransactionRequest
import ru.shiroforbes2.entity.Group
import ru.shiroforbes2.service.TransactionService

@RestController
@RequestMapping("/api/v2/transactions")
class TransactionsController(
  val transactionsService: TransactionService,
) {
  @GetMapping("/{group}")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun getGroupTransactions(
    @PathVariable group: Group,
  ): List<TransactionDTO> = transactionsService.getGroupTransactions(group)

  @GetMapping("/student/{login}")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun getStudentTransactions(
    @PathVariable login: String,
  ): List<TransactionDTO> = transactionsService.getStudentTransactions(login)

  @PostMapping("/new")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun insertTransactions(
    @RequestBody request: CreateTransactionRequest,
  ) = transactionsService.insertTransactions(request.logins, request.amount, request.message)
}
