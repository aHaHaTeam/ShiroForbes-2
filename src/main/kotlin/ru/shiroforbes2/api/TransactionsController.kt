package ru.shiroforbes2.api

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shiroforbes2.dto.TransactionDTO
import ru.shiroforbes2.dto.response.MessageResponse

@RestController
@RequestMapping("/api/v2/transaction")
class TransactionsController {
  @GetMapping("/all")
  @PreAuthorize("hasAuthority('Teacher')")
  fun getAllTransactions(): List<TransactionDTO> {
    TODO()
  }

  @PostMapping("/new")
  @PreAuthorize("hasAuthority('Teacher')")
  fun createNewTransaction(): MessageResponse {
    TODO()
  }
}
