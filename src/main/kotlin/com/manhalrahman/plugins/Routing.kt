package com.manhalrahman.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello! This is the expense sharing app!")
        }
        get("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if(id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid ID"
                )
                return@get
            }
        }

        get("/totalTransactions/{id}") {
            call.respondText("Hello World!")
        }
    }
}
