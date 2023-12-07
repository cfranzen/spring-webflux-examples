package de.cfranzen.examples.webflux.groups;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "groups.client")
record ClientProperties(
        String url
) {
}
