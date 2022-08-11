package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.CustomerNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.mapper.Mapper;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    protected Optional<Customer> findCustomerByNationalIdentityNumber(String nationalIdentityNumber) {
        return Optional.ofNullable(customerRepository.findByNationalIdentityNumber(nationalIdentityNumber).orElseThrow(() ->
                new CustomerNotFoundException("Related customer with National Identity Number: " + nationalIdentityNumber + " not found")));
    }

    //TODO: Check if findById() works instead of findByNationalIdentityNumber() declared in CustomerRepository
    public CustomerDTO getCustomerByNationalIdentityNumber(String nationalIdentityNumber) {
        return Mapper.toDto(findCustomerByNationalIdentityNumber(nationalIdentityNumber).get());

    }

    public List<CustomerDTO> getAllCustomers() {
        return Optional.ofNullable(customerRepository.findAll())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }

    public void addCustomer(CustomerDTO customerDTO) {
        customerRepository.save(Mapper.toEntity(customerDTO));
    }

    public void updateCustomer(String nationalIdentityNumber, CustomerDTO customerDTO) {
        var customerByNationalIdentityNumber = findCustomerByNationalIdentityNumber(nationalIdentityNumber);
        customerByNationalIdentityNumber.ifPresent(customer -> {
            customer.setNationalIdentityNumber(customerDTO.getNationalIdentityNumber());
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setPhone(customerDTO.getPhone());
            customer.setEmail(customerDTO.getEmail());
            customer.setMonthlyIncome(customerDTO.getMonthlyIncome());
            customer.setGender(customerDTO.getGender());
            customer.setAge(customerDTO.getAge());
            customerRepository.save(customer);
        });
    }

    public void deleteCustomer(String nationalIdentityNumber) {
        var customerByNationalIdentityNumber = findCustomerByNationalIdentityNumber(nationalIdentityNumber);
        customerByNationalIdentityNumber.ifPresent(customerRepository::delete);
    }

//    public void patch(String nationalIdentityNumber, Map<Object, Object> fields) {
//        var customerByNationalIdentityNumber = findCustomerByNationalIdentityNumber(nationalIdentityNumber);
//        customerByNationalIdentityNumber.ifPresent(customer -> fields.forEach((key, value) -> {
//            //use reflection to get field and set it to value
//            Field field = ReflectionUtils.findField(Customer.class, (String) key);
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, customerByNationalIdentityNumber.get(), value);
//        }));
//        customerRepository.save(customerByNationalIdentityNumber.get())
//    }

    public void updateCustomerWithMap(String nationalIdentityNumber, Map<Object, Object> objectMap) {
        var customerByNationalIdentityNumber = findCustomerByNationalIdentityNumber(nationalIdentityNumber);
        Customer customer = customerByNationalIdentityNumber.get();
//        customerByNationalIdentityNumber.ifPresent(customer -> objectMap.forEach((key, value) -> {
//            //use reflection to get field and set it to value
//            Field field = ReflectionUtils.findField(Customer.class, (String) key);
//            //field.setAccessible(true);
//            ReflectionUtils.setField(field, customer, value);
//        }));
        objectMap.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Customer.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,customer,value);
        });
        customerRepository.save(customer);

    }
}
