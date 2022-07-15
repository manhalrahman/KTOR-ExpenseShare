package com.manhalrahman.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/totalTransactions/") {
            call.respondText("HelloWorld!")
        }

        get("/totalTransactions/{id}") {
            call.respondText("Hello World!")
        }

    }
}
