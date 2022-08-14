package com.gulbalasalamov.bankloanapplication.repository;

import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFindByNationalIdentityNumber() {

        //given
        String nationalIdentityNumber = "11111111111";
        Customer expected = createDummyCustomer();
        customerRepository.save(expected);

        //when
        Customer actual = customerRepository.findByNationalIdentityNumber(nationalIdentityNumber).get();

        //then
        assertEquals(expected,actual);

    }

    private Customer createDummyCustomer() {
        return new Customer("11111111111", "Gulbala", "Salamov", 100.0, "Male", 12, "1234", "g@gmail.com", 1, null);
    }

}