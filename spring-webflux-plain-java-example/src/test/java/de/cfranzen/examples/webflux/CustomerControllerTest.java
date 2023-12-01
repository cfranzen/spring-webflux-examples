package de.cfranzen.examples.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void testCreationOfCustomer() {
        // Given
        final var request = new CustomerCreationRequest("Peter", "Pan");

        // When
        var result = client.post()
                .uri("/customer")
                .bodyValue(request)
                .exchange();

        // Then
        var customer = result
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        assertThat(customer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Customer("Peter", "Pan"));
        assertThat(customer.getId()).isNotNull();
    }
}