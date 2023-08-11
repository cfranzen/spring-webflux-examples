package de.cfranzen.examples.webflux.kotlin.groups

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    @Bean
    fun webClient(builder: WebClient.Builder, properties: ClientProperties) =
        builder
            .baseUrl(properties.url)
            .build()
}
