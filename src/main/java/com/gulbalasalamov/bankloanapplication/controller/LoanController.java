package com.gulbalasalamov.bankloanapplication.controller;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.service.LoanService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {

    final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    //    @GetMapping("/{nationalIdentityNumber}")
//    public ResponseEntity<Loan> getActiveAndApprovedLoanApplicationByCustomer
    @PostMapping("/create")
    public ResponseEntity createLoan(@RequestBody Loan loan) {
        loanService.createLoan(loan);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
