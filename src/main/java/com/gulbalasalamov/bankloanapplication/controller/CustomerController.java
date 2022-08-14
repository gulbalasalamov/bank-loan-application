package com.gulbalasalamov.bankloanapplication.controller;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerLoanApplicationResponse;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity(customerService.getAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/get/{nationalIdentityNumber}")
    public ResponseEntity<CustomerDTO> getCustomerByNationalIdentityNumber(@PathVariable String nationalIdentityNumber) {
        return new ResponseEntity(customerService.getCustomerByNationalIdentityNumber(nationalIdentityNumber), HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.addCustomer(customerDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/update/{nationalIdentityNumber}")
    public ResponseEntity updateCustomer(@PathVariable String nationalIdentityNumber, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(nationalIdentityNumber, customerDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/update/{nationalIdentityNumber}")
    public ResponseEntity updateCustomerPartially(@PathVariable String nationalIdentityNumber, @RequestBody Map<Object, Object> objectMap) {
        customerService.updateCustomerPartially(nationalIdentityNumber, objectMap);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{nationalIdentityNumber}")
    public ResponseEntity deleteCustomer(@PathVariable String nationalIdentityNumber) {
        customerService.deleteCustomer(nationalIdentityNumber);
        return new ResponseEntity(HttpStatus.OK);
    }


}
