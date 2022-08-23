package com.gulbalasalamov.bankloanapplication.repository;

import com.gulbalasalamov.bankloanapplication.model.entity.Customer;


import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepositoryUnderTest;

    @Test
    void shouldFindByNationalIdentityNumber() {
        //Given
        String nationalIdentityNumber = "11111111111";
        Customer expected = createDummyCustomer();

        //When
        customerRepositoryUnderTest.save(expected);

        //Then
        Optional<Customer> actual = customerRepositoryUnderTest.findByNationalIdentityNumber(nationalIdentityNumber);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying( c-> {
                    assertThat(c).isEqualToComparingFieldByField(expected);
                });
    }
    @Test
    void shouldNotFindCustomerByNationalIdentityNumberWhenNumberDoesNotExist(){

        //Given
        String nationalIdentityNumber = "11";

        //When
        Optional<Customer> actualCustomer = customerRepositoryUnderTest.findByNationalIdentityNumber(nationalIdentityNumber);

        //Then
        assertThat(actualCustomer).isNotPresent();

    }

    @Test
    void shouldSaveCustomer(){
        //Given
        String nationalIdentityNumber = "11111111111";
        Customer expectedCustomer = createDummyCustomer();

        //When
        customerRepositoryUnderTest.save(expectedCustomer);

        //Then
        Optional<Customer> actualCustomer = customerRepositoryUnderTest.findByNationalIdentityNumber(nationalIdentityNumber);
        assertThat(actualCustomer)
                .isPresent()
                .hasValueSatisfying(customer -> {
                    assertThat(customer).isEqualToComparingFieldByField(expectedCustomer);
                });
    }


    private Customer createDummyCustomer() {
        return new Customer("11111111111", "Gulbala", "Salamov", 100.0, "Male", 19, "1234", "g@gmail.com", 1, null);
    }

}