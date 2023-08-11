package de.cfranzen.examples.webflux.kotlin

import de.cfranzen.examples.webflux.kotlin.groups.CustomerGroupClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchByGroupController(
    private val client: CustomerGroupClient,
    private val repository: CustomerRepository
) {

    @GetMapping("/search/customerGroup/{customerGroup}")
    suspend fun findByCustomerGroup(@PathVariable customerGroup: String): Flow<Customer> {
        val group = client.getCustomerGroup(customerGroup)
        return group
            .entries
            .map { repository.findByFirstNameAndLastNameAllIgnoreCase(it.firstName, it.lastName) }
            .merge()
    }
}
