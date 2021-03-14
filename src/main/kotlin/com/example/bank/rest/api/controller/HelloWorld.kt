package com.example.bank.rest.api.controller

import com.example.bank.rest.api.consts.API_PATH
import com.example.bank.rest.api.consts.HELLOWORLD_API_PATH
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HELLOWORLD_API_PATH)
class HelloWorld {

    @GetMapping
    fun getHelloWorld() = "Hello World from Rest controller"

}

