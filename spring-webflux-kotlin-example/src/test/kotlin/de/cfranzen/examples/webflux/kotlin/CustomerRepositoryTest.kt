package de.cfranzen.examples.webflux.kotlin

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest

@DataR2dbcTest
internal class CustomerRepositoryTest(
    @Autowired private val repository: CustomerRepository
) {

    @Test
    fun testFindingByFirstAndLastName() = runTest {
        // given
        val c1 = Customer(firstName = "Peter", lastName = "Mueller")
        val c2 = Customer(firstName = "Peter", lastName = "Mueller")
        val c3 = Customer(firstName = "Sabine", lastName = "Hochhaus")
        repository.saveAll(listOf(c1, c2, c3)).collect()

        // when
        val result = repository.findByFirstNameAndLastName(
            firstName = c1.firstName,
            lastName = c1.lastName
        )

        // then
        assertThat(result.toList())
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(c1, c2)
    }
}