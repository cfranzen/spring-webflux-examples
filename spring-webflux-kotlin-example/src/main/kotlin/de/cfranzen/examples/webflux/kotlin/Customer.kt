package de.cfranzen.examples.webflux.kotlin

import org.springframework.data.annotation.Id

data class Customer(
    val firstName: String,
    val lastName: String
) {
    @Id
    private var id: Long = 0

    fun getId() = id

    override fun toString(): String {
        return "Customer{id=$id, firstName='$firstName', lastName='$lastName'}"
    }
}