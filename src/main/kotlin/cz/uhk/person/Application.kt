package cz.uhk.person

import cz.uhk.person.plugins.configureSerialization
import cz.uhk.person.plugins.configureTemplating
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    configureTemplating()
    configureSerialization()
}
