package de.cfranzen.examples.webflux.kotlin

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerRepository : CoroutineCrudRepository<Customer, Long> {

    suspend fun findByFirstNameAndLastNameAllIgnoreCase(firstName: String, lastName: String): Flow<Customer>
}
