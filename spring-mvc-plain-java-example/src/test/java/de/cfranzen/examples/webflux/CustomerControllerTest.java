package de.cfranzen.examples.webflux;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
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