package com.example.bank.rest.api.dto

data class BankDto(
        val accountNumber: String,
        var trust: Double,
        var transactionFee: Int
)