package de.cfranzen.examples.webflux.kotlin

import de.cfranzen.examples.webflux.kotlin.groups.CustomerGroupClient
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
internal class SearchByGroupController(
    private val client: CustomerGroupClient,
    private val repository: CustomerRepository
) {

    @GetMapping("/search/customerGroup/{customerGroup}")
    fun findByCustomerGroup(@PathVariable customerGroup: String) = flow {
        val group = client.getCustomerGroup(customerGroup)
        emitAll(group
            .entries
            .map { repository.findByFirstNameAndLastName(it.firstName, it.lastName) }
            .merge())
    }
}
