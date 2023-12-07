package de.cfranzen.examples.webflux.groups;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class RestClientConfiguration {

    @Bean
    public RestClient restClient(RestClient.Builder builder, ClientProperties properties) {
        return builder.baseUrl(properties.url()).build();
    }

}
