package com.example.bank.rest.api.controller

import com.example.bank.rest.api.consts.BANKS_API_PATH
import com.example.bank.rest.api.dto.BankDto
import com.example.bank.rest.api.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(BANKS_API_PATH)
class BankController(private val bankService: BankService) {

    @GetMapping
    fun getBanks() = bankService.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String) = bankService.getBank(accountNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: BankDto) = bankService.addBank(bank)

    @PutMapping
    fun updateBank(@RequestBody bank: BankDto) = bankService.updateBank(bank)

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable accountNumber: String) = bankService.deleteBank(accountNumber)

}