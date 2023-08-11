package de.cfranzen.examples.webflux.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class KotlinApplication


fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
}
