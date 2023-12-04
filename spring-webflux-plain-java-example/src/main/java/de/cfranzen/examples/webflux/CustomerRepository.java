package de.cfranzen.examples.webflux;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Long> {

    Flux<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
