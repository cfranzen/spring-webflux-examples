package de.cfranzen.examples.webflux.groups;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(WebClient.Builder builder, ClientProperties properties) {
        return builder
                .baseUrl(properties.url())
                .build();
    }

}
