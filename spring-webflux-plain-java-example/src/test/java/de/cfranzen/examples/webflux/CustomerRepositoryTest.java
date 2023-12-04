package de.cfranzen.examples.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataR2dbcTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void testFindingByFirstAndLastName() {
        // given
        final var c1 = new Customer("Peter", "Mueller");
        final var c2 = new Customer("Peter", "Mueller");
        final var c3 = new Customer("Sabine", "Hochhaus");
        repository.saveAll(List.of(c1, c2, c3)).blockLast();

        // when
        final var result = repository.findByFirstNameAndLastName(c1.getFirstName(), c1.getLastName());

        // then
        assertThat(result.toStream().toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(c1, c2);
    }

}