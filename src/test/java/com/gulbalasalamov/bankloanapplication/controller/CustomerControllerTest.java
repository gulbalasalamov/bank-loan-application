package com.gulbalasalamov.bankloanapplication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;

import com.gulbalasalamov.bankloanapplication.service.CustomerService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper mapper;


    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new Exception())//TODO: chaneg withGeneric Exception
                .build();
    }

    @Test
    void shouldFindAllCustomers() throws Exception {
        List<CustomerDTO> dummyCustomerDTOList = createDummyCustomerDtoList();

        when(customerService.getAllCustomers()).thenReturn(dummyCustomerDTOList);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<CustomerDTO> actualCustomerDTOList = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<CustomerDTO>>() {
        });
        Assert.assertEquals(dummyCustomerDTOList.size(), actualCustomerDTOList.size());
    }

    @Test
    void shouldFindCustomerByNationalIdentityNumber() throws Exception {
        // init test values
        List<CustomerDTO> expectedCustomerDTOList = createDummyCustomerDtoList();
        String nationalIdentityNumber = "11111111111";
        String url = "http://localhost:8086/api/v1/customer/get/11111111111";

        // stub - given
        Mockito.when(customerService.getCustomerByNationalIdentityNumber(nationalIdentityNumber)).thenReturn(expectedCustomerDTOList.get(0));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CustomerDTO actualCustomerDTO = new ObjectMapper().readValue(response.getContentAsString(), CustomerDTO.class);
        Assert.assertEquals(expectedCustomerDTOList.get(0), actualCustomerDTO);
    }

    @Test
    void shouldAddCustomer() throws Exception {
        var dummyCustomerDTO = createDummyCustomerDTO();

        String url = "http://localhost:8086/api/v1/customer/add/";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(dummyCustomerDTO);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

//    @Test
//    void updateCustomer() {
//    }
//
//    @Test
//    void updateCustomerPartially() {
//    }
//
//    @Test
//    void deleteCustomer() {
//    }


    private Customer createDummyCustomer() {
        return new Customer("11111111111", "Gulbala", "Salamov", 100.0, "Male", 12, "1234", "g@gmail.com", 1, null);
    }

    private CustomerDTO createDummyCustomerDTO() {
        return new CustomerDTO("11111111111", "John", "Cloud", "1", "g@g.com", 10.0, "Female", 5, 1, null);
    }

    private List<CustomerDTO> createDummyCustomerDtoList() {

        List<CustomerDTO> customerDTOList = new ArrayList<>();

        CustomerDTO customerDTO = createDummyCustomerDTO();
        customerDTOList.add(customerDTO);

        return customerDTOList;
    }
}