package de.cfranzen.examples.webflux;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Stream<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
