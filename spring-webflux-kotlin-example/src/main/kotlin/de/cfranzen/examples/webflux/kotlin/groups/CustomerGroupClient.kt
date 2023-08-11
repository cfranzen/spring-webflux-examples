package de.cfranzen.examples.webflux.kotlin.groups

import kotlinx.coroutines.flow.single
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import org.springframework.web.util.UriBuilder

@Component
class CustomerGroupClient(
    private val webClient: WebClient
) {

    suspend fun getCustomerGroup(name: String): CustomerGroup {
        return webClient
            .get()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/api/customerGroup/{name}")
                    .build(name)
            }
            .retrieve()
            .bodyToFlow<CustomerGroup>()
            .single()
    }
}
