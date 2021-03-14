package com.example.bank.rest.api.datasource

import com.example.bank.rest.api.dto.BankDto

interface BankDataSource {

    fun getBanks(): Collection<BankDto>
    fun getBank(accountNumber: String): BankDto
    fun createBank(bank: BankDto): BankDto
    fun updateBank(bank: BankDto): BankDto
    fun deleteBank(accountNumber: String)
}