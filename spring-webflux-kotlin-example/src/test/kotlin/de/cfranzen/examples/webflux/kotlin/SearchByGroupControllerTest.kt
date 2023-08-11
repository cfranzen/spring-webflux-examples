package de.cfranzen.examples.webflux.kotlin

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.common.Json
import de.cfranzen.examples.webflux.kotlin.groups.CustomerGroup
import de.cfranzen.examples.webflux.kotlin.groups.CustomerGroupEntry
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
internal class SearchByGroupControllerTest(
    @Autowired private val client: WebTestClient,
    @Autowired private val repository: CustomerRepository
) {

    @Test
    fun testFindingCustomersByGroupName() = runTest {
        // given
        val c1 = Customer(firstName = "Peter", lastName = "Mueller")
        val c2 = Customer(firstName = "Sabine", lastName = "Hochhaus")
        val c3 = Customer(firstName = "Heike", lastName = "Recker")
        val c4 = Customer(firstName = "Dieter", lastName = "Baum")
        repository.saveAll(listOf(c1, c2, c3, c4)).collect()

        val groupName = "testGroup"
        val customerGroup = CustomerGroup(
            name = groupName,
            entries = listOf(
                CustomerGroupEntry(c2.firstName, c2.lastName),
                CustomerGroupEntry(c4.firstName, c4.lastName)
            )
        )
        stubCustomerGroupServer(customerGroup)

        // when
        val result = client
            .get()
            .uri("/search/customerGroup/{customerGroup}", groupName)
            .exchange()


        //then
        val customers = result
            .expectStatus()
            .isOk()
            .expectBodyList(Customer::class.java)
            .returnResult()
            .responseBody!!

        Assertions.assertThat(customers)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(c2, c4)
    }

    private fun stubCustomerGroupServer(result: CustomerGroup) {
        stubFor(
            get("/api/customerGroup/" + result.name)
                .willReturn(okJson(Json.write(result)))
        )
    }
}