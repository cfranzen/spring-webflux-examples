package de.cfranzen.examples.webflux;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomerController {

    private final CustomerRepository repository;

    CustomerController(final CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/customer")
    Customer createNewCustomer(@RequestBody CustomerCreationRequest request) {
        return repository.save(new Customer(request.firstName(), request.lastName()));
    }
}

record CustomerCreationRequest(
        String firstName,
        String lastName
) {
}
