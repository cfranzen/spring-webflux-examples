package de.cfranzen.examples.webflux.groups;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
class CustomerGroupClientTest {

    @Test
    void testRetrievingCustomerGroup(final WireMockRuntimeInfo wmRuntimeInfo) {
        // Given
        final var customerGroup = new CustomerGroup(
                "testGroup",
                List.of(
                        new CustomerGroupEntry("Peter", "Mueller"),
                        new CustomerGroupEntry("Sabine", "Hochhaus"),
                        new CustomerGroupEntry("Heike", "Recker")
                )
        );
        stubCustomerGroupServer(customerGroup);

        // When
        final var result = buildClient(wmRuntimeInfo).getCustomerGroup(customerGroup.name());

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(customerGroup);

    }

    private CustomerGroupClient buildClient(final WireMockRuntimeInfo wmRuntimeInfo) {
        final var restClient = RestClient.create(wmRuntimeInfo.getHttpBaseUrl());
        return new CustomerGroupClient(restClient);
    }

    private void stubCustomerGroupServer(final CustomerGroup result) {
        stubFor(get("/api/customerGroup/" + result.name())
                .willReturn(okJson(Json.write(result))));
    }
}