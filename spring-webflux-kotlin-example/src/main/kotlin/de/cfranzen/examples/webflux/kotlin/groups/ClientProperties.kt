package de.cfranzen.examples.webflux.kotlin.groups

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "groups.client")
data class ClientProperties(
    var url: String
)
