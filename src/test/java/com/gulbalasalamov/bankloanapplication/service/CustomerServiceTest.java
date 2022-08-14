package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.CustomerNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldNotFindAccountByCustomerNationalIdentityNumber_WhenId_DoesNotExist(){

        when(customerService.getCustomerByNationalIdentityNumber(null)).thenThrow(CustomerNotFoundException.class);

        assertThrows(CustomerNotFoundException.class, () -> customerRepository.findByNationalIdentityNumber(""));

        verify(customerService).getCustomerByNationalIdentityNumber(null);
    }

    @Test
    void shouldGetCustomerByNationalIdentityNumber() {

//        Customer customer= createDummyCustomer();
//        when(customerRepository.findByNationalIdentityNumber(nationalIdentityNumber)).thenReturn(Optional.of(customer));

//        String nationalIdentityNumber = "11111111111";
//
//     //   Customer customer = mock(Customer.class);
//
//        CustomerDTO customerDTO2 = createDummyCustomerDTO();
////        CustomerDTO customerDTO = mock(customerDTO2.getClass());
////kanka sen cikmna lazimdi. isini oncelikli olani yap. vaktin olrsa aksam devam ederiz. benden oturu geri dusme. tmm. bende tutorial izlemeye dvm edeym
//        //yarim saat var
//        customerService.addCustomer(customerDTO2);
//
//        when(customerService.getCustomerByNationalIdentityNumber(nationalIdentityNumber)).thenReturn(customerDTO2);
//        when(customerRepository.findByNationalIdentityNumber(nationalIdentityNumber)).thenReturn(customerDTO2);
//
//        Customer actualCustomer = customerRepository.findByNationalIdentityNumber(nationalIdentityNumber).get();
//
//        assertEquals(nationalIdentityNumber,actualCustomer.getNationalIdentityNumber());



        // assertEquals(nationalIdentityNumber, customerDTO.getNationalIdentityNumber());
        //assertEquals(nationalIdentityNumber,.getNationalIdentityNumber());
    }

    @Test
    void shouldGetAllCustomers() {
        var customer = mock(CustomerDTO.class);
        var expCustomerList = new ArrayList<CustomerDTO>();
        expCustomerList.add(customer);

        when(customerService.getAllCustomers()).thenReturn(expCustomerList);

        List<Customer> actCustomerList = customerRepository.findAll();

        assertEquals(expCustomerList.size(), actCustomerList.size());

    }



    @Test
    void addCustomer() {
//        CustomerDTO customerDTO = createDummyCustomerDTO();
//        Customer customer = createDummyCustomer();
//
//        when(customerService.getCustomerByNationalIdentityNumber("11111111111")).thenReturn(customerDTO);
//        when(customerRepository.save(customer)).thenReturn(customer);
//
//        CustomerDTO result = customerService.addCustomer(customerDTO);
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomerPartially() {
    }


    private Customer createDummyCustomer() {
        return new Customer("11111111111", "Gulbala", "Salamov", 100.0, "Male", 12, "1234", "g@gmail.com", 1, null);
    }

    private CustomerDTO createDummyCustomerDTO() {
        return new CustomerDTO("11111111111", "John", "Cloud", "1", "g@g.com", 10.0, "Female", 5, 1, null);
    }

    private List<CustomerDTO> createDummyCustomerDtoList(){

        List<CustomerDTO>  customerDTOList = new ArrayList<>();

        CustomerDTO customerDTO = createDummyCustomerDTO();
        customerDTOList.add(customerDTO);

        return customerDTOList;
    }
}