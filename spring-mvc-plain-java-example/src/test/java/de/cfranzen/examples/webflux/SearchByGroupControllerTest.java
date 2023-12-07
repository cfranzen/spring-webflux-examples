package de.cfranzen.examples.webflux;

import com.github.tomakehurst.wiremock.common.Json;
import de.cfranzen.examples.webflux.groups.CustomerGroup;
import de.cfranzen.examples.webflux.groups.CustomerGroupEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"groups.client.url=http://localhost:${wiremock.server.port}"}
)
@AutoConfigureWireMock(port = 0)
class SearchByGroupControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository repository;

    @Test
    void testFindingCustomersByGroupName() {
        // given
        final var c1 = new Customer("Peter", "Mueller");
        final var c2 = new Customer("Sabine", "Hochhaus");
        final var c3 = new Customer("Heike", "Recker");
        final var c4 = new Customer("Dieter", "Baum");
        repository.saveAll(List.of(c1, c2, c3, c4));

        final var groupName = "testGroup";
        final var customerGroup = new CustomerGroup(
                groupName,
                List.of(
                        new CustomerGroupEntry(c2.getFirstName(), c2.getLastName()),
                        new CustomerGroupEntry(c4.getFirstName(), c4.getLastName())
                )
        );
        stubCustomerGroupServer(customerGroup);

        // when
        var customers = restTemplate
                .exchange("/search/customerGroup/{customerGroup}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Customer>>() {
                        },
                        groupName)
                .getBody();

        //then
        assertThat(customers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(c2, c4);
    }

    private void stubCustomerGroupServer(final CustomerGroup result) {
        stubFor(get("/api/customerGroup/" + result.name())
                .willReturn(okJson(Json.write(result))));
    }
}