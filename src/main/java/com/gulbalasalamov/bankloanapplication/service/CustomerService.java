package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(String nationalIdentityNumber) {
        Optional<Customer> customerByNationalIdentityNumber = customerRepository.findByNationalIdentityNumber(nationalIdentityNumber);
        customerByNationalIdentityNumber.ifPresent(customerRepository::delete);
    }


}
