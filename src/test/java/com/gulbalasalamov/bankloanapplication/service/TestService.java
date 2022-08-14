//package com.gulbalasalamov.bankloanapplication.service;
//
//import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
//import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
//import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
// class TestService {
//
//    CustomerService customerService;
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//
//
//    @Test
//    void shouldGetCustomerByNationalIdentityNumber2() {
//        MockitoAnnotations.openMocks(this);
//        customerService = new CustomerService(customerRepository);
////        Customer customer= createDummyCustomer();
////        when(customerRepository.findByNationalIdentityNumber(nationalIdentityNumber)).thenReturn(Optional.of(customer));
//
//        String nationalIdentityNumber = "11111111111";
//
//        //   Customer customer = mock(Customer.class);
//
//        CustomerDTO customerDTO2 = createDummyCustomerDTO();
//
//        CustomerDTO customerDTO = mock(CustomerDTO.class);
////kanka o diyorki kimlik no 11 haneli olmalidir. bide yas 18 ustu olmali
//        //customerService.addCustomer(customerDTO2);
//
//        when(customerService.getCustomerByNationalIdentityNumber(nationalIdentityNumber)).thenReturn(ArgumentMatchers.any(CustomerDTO.class));
//
//        Customer actualCustomer = customerRepository.findByNationalIdentityNumber(nationalIdentityNumber).get();
//
//        assertEquals(customerDTO2.getNationalIdentityNumber(),actualCustomer.getNationalIdentityNumber());
//
//
//
//        // assertEquals(nationalIdentityNumber, customerDTO.getNationalIdentityNumber());
//        //assertEquals(nationalIdentityNumber,.getNationalIdentityNumber());
//    }
//
//    private CustomerDTO createDummyCustomerDTO() {
//        return new CustomerDTO("11111111111", "John", "Cloud", "1", "g@g.com", 10.0, "Female", 55, 1, null);
//    }
//
//
//}
