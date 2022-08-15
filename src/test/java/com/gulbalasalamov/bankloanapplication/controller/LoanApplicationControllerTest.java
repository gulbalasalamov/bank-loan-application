package com.gulbalasalamov.bankloanapplication.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gulbalasalamov.bankloanapplication.model.LoanScoreResult;
import com.gulbalasalamov.bankloanapplication.model.LoanStatus;
import com.gulbalasalamov.bankloanapplication.model.LoanType;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;

import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.service.CustomerService;
import com.gulbalasalamov.bankloanapplication.service.LoanApplicationService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class LoanApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanApplicationService loanApplicationService;
    @InjectMocks
    private LoanApplicationController loanApplicationController;

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(loanApplicationController)
                .setControllerAdvice(new Exception())//TODO: chaneg withGeneric Exception
                .build();
    }


    @Test
    void shouldCreateLoanApplication() throws Exception {
        String nationalIdentityNumber = "11111111111";
        String url = "http://localhost:8086/api/v1/loanapplication/create/{nationalIdentityNumber}";
        mockMvc
                .perform(post(url,nationalIdentityNumber))
                .andExpect(status().isCreated());
                //.andExpect(content().string(containsString("The LoanApplication successfully created")));
    }

    @Test
    void shouldGetLoanApplicationById() throws Exception {
        List<LoanApplication> expectedLoanApplicationList = createDummyLoanApplicationList();
        Long id = 1L;

        // stub - given
        Mockito.when(loanApplicationService.getLoanApplicationById(id)).thenReturn(expectedLoanApplicationList.get(0));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8086/api/v1/loanapplication/get/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        LoanApplication actualLoanApplication = new ObjectMapper().readValue(response.getContentAsString(), LoanApplication.class);

        Assert.assertEquals(expectedLoanApplicationList.get(0).getId(), actualLoanApplication.getId());
    }

    @Test
    void shouldGetActiveAndApprovedCreditApplicationByCustomer() throws Exception {
        String nationalIdentityNumber = "11111111111";
        String url = "http://localhost:8086/api/v1/loanapplication/active-and-approved/11111111111";
        mockMvc
                .perform(get(url))
                .andExpect(status().isOk());

    }

    private LoanApplication createDummyLoanApplication(){
       LoanApplication lo = new LoanApplication(createDummyCustomer(),createDummyLoan());
       lo.setId(1L);
       return lo;
    }

    private Loan createDummyLoan(){
        return new Loan(LoanType.AUTOMOBILE,0.0, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE,new Date());
    }

    private Customer createDummyCustomer() {
        return new Customer("11111111111", "Gulbala", "Salamov", 100.0, "Male", 12, "1234", "g@gmail.com", 1, null);
    }

    private CustomerDTO createDummyCustomerDTO() {
        return new CustomerDTO("11111111111", "John", "Cloud", "1", "g@g.com", 10.0, "Female", 5, 1, null);
    }

    private List<LoanApplication> createDummyLoanApplicationList() {

        List<LoanApplication> loanApplicationList = new ArrayList<>();

        LoanApplication loanApplication = createDummyLoanApplication();
        loanApplicationList.add(loanApplication);

        return loanApplicationList;
    }
}