package de.cfranzen.examples.webflux.kotlin.groups

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.common.Json
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

@WireMockTest
internal class CustomerGroupClientTest {

    @Test
    fun testRetrievingCustomerGroup(wmRuntimeInfo: WireMockRuntimeInfo) = runTest {
        // Given
        val customerGroup = CustomerGroup(
            name = "testGroup",
            entries = listOf(
                CustomerGroupEntry("Peter", "Mueller"),
                CustomerGroupEntry("Sabine", "Hochhaus"),
                CustomerGroupEntry("Heike", "Recker")
            )
        )
        stubCustomerGroupServer(customerGroup)

        // When
        val result = buildClient(wmRuntimeInfo).getCustomerGroup(customerGroup.name)

        // Then
        assertThat(result)
            .usingRecursiveComparison()
            .isEqualTo(customerGroup)
    }

    private fun buildClient(wmRuntimeInfo: WireMockRuntimeInfo): CustomerGroupClient {
        val webClient = WebClient.builder().baseUrl(wmRuntimeInfo.httpBaseUrl).build()
        return CustomerGroupClient(webClient)
    }

    private fun stubCustomerGroupServer(result: CustomerGroup) {
        stubFor(
            get("/api/customerGroup/" + result.name)
                .willReturn(okJson(Json.write(result)))
        )
    }
}