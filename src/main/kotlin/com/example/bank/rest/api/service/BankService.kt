package com.example.bank.rest.api.service

import com.example.bank.rest.api.datasource.BankDataSource
import com.example.bank.rest.api.dto.BankDto
import org.springframework.stereotype.Service

@Service
class BankService(private val bankDataSource: BankDataSource) {

    fun getBanks() = bankDataSource.getBanks()

    fun getBank(accountNumber: String) = bankDataSource.getBank(accountNumber)

    fun addBank(bank: BankDto) = bankDataSource.createBank(bank)

    fun updateBank(bank: BankDto): BankDto = bankDataSource.updateBank(bank)

    fun deleteBank(accountNumber: String) = bankDataSource.deleteBank(accountNumber)
}