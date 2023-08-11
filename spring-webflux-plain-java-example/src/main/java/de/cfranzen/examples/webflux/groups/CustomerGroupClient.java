package de.cfranzen.examples.webflux.groups;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerGroupClient {

    private final WebClient webClient;

    public CustomerGroupClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CustomerGroup> getCustomerGroup(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customerGroup/{name}")
                        .build(name))
                .retrieve()
                .bodyToMono(CustomerGroup.class);
    }
}
