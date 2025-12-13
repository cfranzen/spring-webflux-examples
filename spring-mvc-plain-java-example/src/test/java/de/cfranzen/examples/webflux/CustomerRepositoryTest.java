package de.cfranzen.examples.webflux;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;


@DataJdbcTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  @Test
  void testFindingByFirstAndLastName() {
    // given
    final var c1 = new Customer("Peter", "Mueller");
    final var c2 = new Customer("Peter", "Mueller");
    final var c3 = new Customer("Sabine", "Hochhaus");
    repository.saveAll(List.of(c1, c2, c3));

    // when
    final var result = repository.findByFirstNameAndLastName(c1.getFirstName(), c1.getLastName());

    // then
    assertThat(result.toList())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactly(c1, c2);
  }

}