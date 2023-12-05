package de.cfranzen.examples.webflux.kotlin

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CoroutineCrudRepository<Customer, Long> {

    fun findByFirstNameAndLastName(firstName: String, lastName: String): Flow<Customer>
}
