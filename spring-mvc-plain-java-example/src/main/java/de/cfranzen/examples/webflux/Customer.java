package de.cfranzen.examples.webflux;


import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Customer {

    @Id
    private Long id;
    private final String firstName;
    private final String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = Objects.requireNonNull(firstName, "Firstname of customer might not be null");
        this.lastName = Objects.requireNonNull(lastName, "Lastname of customer might not be null");
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }
}