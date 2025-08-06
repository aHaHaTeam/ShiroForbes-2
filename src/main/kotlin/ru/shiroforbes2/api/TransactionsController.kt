package ru.shiroforbes2.api

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.TransactionDTO
import ru.shiroforbes2.dto.request.CreateTransactionRequest
import ru.shiroforbes2.service.TransactionService

@RestController
@RequestMapping("/api/v2/transactions")
class TransactionsController(
  val transactionsService: TransactionService,
) {
  @GetMapping("/countryside")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun getCountrysideTransactions(): List<TransactionDTO> = transactionsService.getCountrysideTransactions()

  @GetMapping("/urban")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun getUrbanTransactions(): List<TransactionDTO> = transactionsService.getUrbanTransactions()

  @PostMapping("/new")
  @PreAuthorize("hasAuthority('Admin') or hasAuthority('Teacher')")
  fun insertTransaction(
    @RequestBody request: CreateTransactionRequest,
  ) = {
    transactionsService.insertTransaction(request.names, request.amount, request.message)
  }
}
