package de.cfranzen.examples.webflux.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class CustomerControllerTest(
    @Autowired private val client: WebTestClient
) {

    @Test
    fun testCreationOfCustomer() {
        // Given
        val request = CustomerCreationRequest(firstName = "Peter", lastName = "Pan")

        // When
        val result = client
            .post()
            .uri("/customer")
            .bodyValue(request)
            .exchange()

        // Then
        val customer = result
            .expectStatus()
            .isOk()
            .expectBody(Customer::class.java)
            .returnResult()
            .responseBody!!

        assertThat(customer)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(Customer(firstName = "Peter", lastName = "Pan"))
        assertThat(customer.getId()).isNotNull()
    }
}