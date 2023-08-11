package de.cfranzen.examples.webflux;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(final CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/customer")
    public Mono<Customer> createNewCustomer(@RequestBody Mono<CustomerCreationRequest> request) {
        return request.flatMap(dto -> repository.save(new Customer(dto.firstName(), dto.lastName())));
    }
}

record CustomerCreationRequest(
        String firstName,
        String lastName
) {
}
