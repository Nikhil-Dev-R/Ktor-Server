package com.rudraksha

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.Serializable
import org.slf4j.event.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        get("/") {
            call.respondText("Hello World! at 0.0.0.0:8080")
        }
        get<Articles> { article ->
            // Get all articles ...
            call.respond("List of articles sorted starting from ${article.sort}")
        }

        get("/test1") {
            val text = "<h1>Hello From Ktor</h1>"
            val type = ContentType.parse("text/html")
            call.respondText(text, type)
        }

        get("/tasks") {
            call.respondText(
                contentType = ContentType.parse("text/html"),
                text = """
                <h3>TODO:</h3>
                <ol>
                    <li>A table of all the tasks</li>
                    <li>A form to submit new tasks</li>
                </ol>
                """.trimIndent()
            )
        }
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
