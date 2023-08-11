package de.cfranzen.examples.webflux.kotlin.groups


data class CustomerGroup(
    val name: String,
    val entries: List<CustomerGroupEntry>
)
