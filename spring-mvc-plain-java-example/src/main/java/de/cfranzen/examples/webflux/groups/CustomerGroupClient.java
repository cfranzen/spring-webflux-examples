package de.cfranzen.examples.webflux.groups;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CustomerGroupClient {

    private final RestClient restClient;

    public CustomerGroupClient(final RestClient restClient) {
        this.restClient = restClient;
    }

    public CustomerGroup getCustomerGroup(String name) {
        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/customerGroup/{name}")
                        .build(name))
                .retrieve()
                .body(CustomerGroup.class);
    }
}
