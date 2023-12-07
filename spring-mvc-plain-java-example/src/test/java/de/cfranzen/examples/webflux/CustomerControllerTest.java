package de.cfranzen.examples.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreationOfCustomer() {
        // Given
        final var request = new CustomerCreationRequest("Peter", "Pan");

        // When
        var customer = restTemplate.postForObject("/customer", request, Customer.class);

        // Then
        assertThat(customer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Customer("Peter", "Pan"));
        assertThat(customer.getId()).isNotNull();
    }
}