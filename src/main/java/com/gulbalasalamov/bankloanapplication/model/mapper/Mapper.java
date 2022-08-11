package com.gulbalasalamov.bankloanapplication.model.mapper;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;

public class Mapper {
    public static CustomerDTO toDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNationalIdentityNumber(customer.getNationalIdentityNumber());
        customerDTO.setFirstName(customer.getFirstname());
        customerDTO.setLastName(customer.getLastname());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMonthlyIncome(customer.getMonthlyIncome());
        customerDTO.setGender(customer.getGender());
        customerDTO.setAge(customer.getAge());
        return customerDTO;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setNationalIdentityNumber(customerDTO.getNationalIdentityNumber());
        customer.setFirstname(customerDTO.getFirstName());
        customer.setLastname(customerDTO.getLastName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setMonthlyIncome(customerDTO.getMonthlyIncome());
        customer.setGender(customerDTO.getGender());
        customer.setAge(customerDTO.getAge());
        return customer;
    }
    }
}
