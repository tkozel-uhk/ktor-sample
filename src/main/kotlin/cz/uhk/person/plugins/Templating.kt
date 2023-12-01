package cz.uhk.person.plugins

import cz.uhk.person.data.Osoba
import cz.uhk.person.data.personRepository
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import io.ktor.server.util.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
    routing {
        get("/html/osoby") {
            call.respond(ThymeleafContent("osoby", mapOf("osoby" to personRepository.getAll())))
        }
        get("/html/osoby/add") {
            call.respond(ThymeleafContent("osobaForm", mapOf("osoba" to Osoba(0, "", "", ""))))
        }
        get("/html/osoby/{id}") {
            val id = call.parameters.getOrFail("id").toLong()
            val osoba = personRepository.getById(id) ?: throw NotFoundException()
            call.respond(ThymeleafContent("osobaForm", mapOf("osoba" to osoba)))
        }
        post ("/html/osoby/save") {
            val params = call.receiveParameters()
            val id = params.getOrFail("id").toLong()
            val name = params.getOrFail("jmeno")
            val surname = params.getOrFail("prijmeni")
            val email = params.getOrFail("email")
            if (personRepository.getById(id) != null) {
                personRepository.update(Osoba(id, name, surname, email))
            } else {
                personRepository.add(Osoba(id, name, surname, email))
            }
            call.respondRedirect("/html/osoby")
        }
        get("/html/osoby/{id}/delete") {
            val id = call.parameters.getOrFail("id").toLong()
            personRepository.delete(id)
            call.respondRedirect("/html/osoby")
        }
    }
}

data class ThymeleafUser(val id: Int, val name: String)
