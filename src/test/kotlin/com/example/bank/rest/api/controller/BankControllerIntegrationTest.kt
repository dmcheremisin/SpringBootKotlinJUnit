package com.example.bank.rest.api.controller

import com.example.bank.rest.api.consts.BANKS_API_PATH
import com.example.bank.rest.api.dto.BankDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerIntegrationTest @Autowired constructor(
        var mockMvc: MockMvc,
        var objectMapper: ObjectMapper) {

    @Test
    fun `should return all banks`() {
        // when/then
        mockMvc.get(BANKS_API_PATH)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
    }

    @Test
    fun `should return bank with provided account number`() {
        // given
        val accountNumber = 1234

        // when
        mockMvc.get("$BANKS_API_PATH/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("1234") }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.transactionFee") { value("1") }
                }
    }

    @Test
    fun `should return NOT FOUND when account number does not exist`() {
        // given
        val accountNumber = "55555"

        // when
        mockMvc.get("$BANKS_API_PATH/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
    }

    @Test
    fun `should create new bank`() {
        // given
        val bank = BankDto("3333", 4.5, 34)

        // when
        val response = mockMvc.post(BANKS_API_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bank)
        }

        // then
        response
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }

        mockMvc.get("$BANKS_API_PATH/${bank.accountNumber}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }
    }

    @Test
    fun `should return BAD REQUEST when bank with provided id already exists`() {
        // given
        val bank = BankDto("1234", 2.9, 67)

        // when
        val response = mockMvc.post(BANKS_API_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bank)
        }

        // then
        response
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
    }

    @Test
    fun `should update existing bank`() {
        // given
        val bank = BankDto("5678", 22.0, 0)

        // when
        val response = mockMvc.put(BANKS_API_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bank)
        }

        // then
        response
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }

        mockMvc.get("$BANKS_API_PATH/${bank.accountNumber}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }
    }

    @Test
    fun `should return NOT FOUND when bank for update is not found`() {
        // given
        val bank = BankDto("qwerty123", 22.0, 0)

        // when
        val response = mockMvc.put(BANKS_API_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(bank)
        }

        // then
        response
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
    }

    @Test
    fun `should delete bank by account number`() {
        // given
        val accountNumber = 1010

        // when/then
        mockMvc.delete("$BANKS_API_PATH/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

        mockMvc.get("$BANKS_API_PATH/$accountNumber")
                .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return NOT FOUND when bank for delete is not found`() {
        // given
        val wrongAccountNumber = 234234

        // when/then
        mockMvc.delete("$BANKS_API_PATH/$wrongAccountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
    }


}