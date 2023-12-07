package de.cfranzen.examples.webflux;

import de.cfranzen.examples.webflux.groups.CustomerGroupClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class SearchByGroupController {

    private final CustomerGroupClient client;

    private final CustomerRepository repository;

    SearchByGroupController(final CustomerGroupClient client, final CustomerRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    @GetMapping("/search/customerGroup/{customerGroup}")
    List<Customer> findByCustomerGroup(@PathVariable final String customerGroup) {
        final var group = client.getCustomerGroup(customerGroup);
        return group.entries()
                .stream()
                .flatMap(entry -> repository.findByFirstNameAndLastName(entry.firstName(), entry.lastName()))
                .toList();
    }
}
