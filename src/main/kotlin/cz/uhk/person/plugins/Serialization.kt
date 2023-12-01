package cz.uhk.person.plugins

import cz.uhk.person.data.PersonRepository
import cz.uhk.person.data.personRepository
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

/**
 * Konfigurace pluginu pro serializaci do JSONu
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/json/osoby") {
            call.respond(personRepository.getAll())
        }
        get("/json/osoby/{id}") {
            val id = call.parameters.getOrFail("id").toLong()
            val osoba = personRepository.getById(id) ?: throw NotFoundException()
            call.respond(osoba)
        }
    }
}
