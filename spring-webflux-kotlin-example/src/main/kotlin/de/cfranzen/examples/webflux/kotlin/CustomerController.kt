package de.cfranzen.examples.webflux.kotlin

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CustomerController(
    private val repository: CustomerRepository
) {

    @PostMapping("/customer")
    suspend fun createNewCustomer(@RequestBody request: Flow<CustomerCreationRequest>): Customer {
        val dto = request.single()
        return repository.save(Customer(dto.firstName, dto.lastName))
    }
}

internal data class CustomerCreationRequest(
    val firstName: String,
    val lastName: String
)
