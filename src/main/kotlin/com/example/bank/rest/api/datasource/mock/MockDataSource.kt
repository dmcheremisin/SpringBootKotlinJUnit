package com.example.bank.rest.api.datasource.mock

import com.example.bank.rest.api.datasource.BankDataSource
import com.example.bank.rest.api.dto.BankDto
import org.springframework.stereotype.Repository

@Repository
class MockDataSource : BankDataSource {

    val bankList = mutableListOf(
            BankDto("1234", 3.14, 1),
            BankDto("5678", 10.0, 0),
            BankDto("1010", 0.0, 19)
    )

    override fun getBanks() = bankList

    override fun getBank(accountNumber: String) =
            bankList.firstOrNull() { it.accountNumber == accountNumber }
                    ?: throw NoSuchElementException("Bank with account with number $accountNumber doesn't exist")

    override fun createBank(bank: BankDto): BankDto {
        if (bankList.any() { it.accountNumber == bank.accountNumber })
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")

        bankList.add(bank)
        return bank
    }

    override fun updateBank(bank: BankDto): BankDto {
        val existingBank = getBank(bank.accountNumber)
        existingBank.transactionFee = bank.transactionFee
        existingBank.trust = bank.trust

        return existingBank
    }

    override fun deleteBank(accountNumber: String) {
        val bank = getBank(accountNumber)
        bankList.remove(bank)
    }

}