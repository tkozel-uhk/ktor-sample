package cz.uhk.person.data

import kotlinx.serialization.Serializable

/**
 * POKO osoby s emailem
 */
@Serializable
class Osoba(var id: Long, var jmeno: String, var prijmeni: String, var email: String)

/**
 * In memory repozitář pro práci s osobami (singleton)
 */
object personRepository {
    private val persons = mutableListOf<Osoba>()

    init {
        //nejaka demo data do zacatku
        persons.add(Osoba(1L, "Jan", "Novak", "jan@novak.cz"))
        persons.add(Osoba(2L, "Anna", "Zimova", "annazm@seznam.cz"))
        persons.add(Osoba(3L, "Petr", "Kratky", "petross@email.cz"))
    }

    fun getAll(): List<Osoba> {
        return persons
    }

    fun getById(id: Long): Osoba? {
        return persons.find { it.id == id }
    }

    fun add(person: Osoba) {
        val lastId = persons.maxOfOrNull { it.id } ?: 0
        person.id = lastId + 1
        persons.add(person)
    }

    fun delete(id: Long) {
        persons.removeIf { it.id == id }
    }

    fun update(person: Osoba) {
        val index = persons.indexOfFirst { it.id == person.id }
        if (index >= 0) {
            persons[index] = person
        }
    }
}
