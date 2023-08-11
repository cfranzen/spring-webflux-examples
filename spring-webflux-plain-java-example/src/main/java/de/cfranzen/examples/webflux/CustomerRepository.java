package de.cfranzen.examples.webflux;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends R2dbcRepository<Customer, Long> {

    Flux<Customer> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
}
