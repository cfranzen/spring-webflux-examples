package de.cfranzen.examples.webflux;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    Flux<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
