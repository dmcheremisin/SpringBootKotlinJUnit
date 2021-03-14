package com.example.bank.rest.api.service

import com.example.bank.rest.api.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val bankDataSource: BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(bankDataSource)

    @Test
    fun `should return banks from datasource`() {
        // given
        // when relaxed true -> default return values may be ommited
        // every { bankDataSource.getBanks() } returns emptyList()

        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) { bankDataSource.getBanks() }
    }

}