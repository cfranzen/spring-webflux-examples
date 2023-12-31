package de.cfranzen.examples.webflux;

import de.cfranzen.examples.webflux.groups.CustomerGroupClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
class SearchByGroupController {

    private final CustomerGroupClient client;

    private final CustomerRepository repository;

    SearchByGroupController(final CustomerGroupClient client, final CustomerRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    @GetMapping("/search/customerGroup/{customerGroup}")
    Flux<Customer> findByCustomerGroup(@PathVariable final String customerGroup) {
        return client.getCustomerGroup(customerGroup)
                .flatMapMany(group ->
                        Flux.concat(
                                group.entries()
                                        .stream()
                                        .map(entry -> repository.findByFirstNameAndLastName(entry.firstName(), entry.lastName()))
                                        .toList()
                        )
                );
    }
}
