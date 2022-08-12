package com.gulbalasalamov.bankloanapplication.controller;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.service.LoanApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loanapplication")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("/create")
    public ResponseEntity createLoanApplication(@RequestBody LoanApplication loanApplication) {
        loanApplicationService.createLoanApplication(loanApplication);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/get/{loanApplicationId}")
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable Long loanApplicationId) {
        return new ResponseEntity(loanApplicationService.getLoanApplicationById(loanApplicationId), HttpStatus.OK);
    }

    @PutMapping("/{loanApplicationId}/loan/{loanId}")
    public ResponseEntity addLoanToLoanApplication(@PathVariable Long loanApplicationId, @PathVariable Long loanId) {
        loanApplicationService.addLoanToLoanApplication(loanId, loanApplicationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loanApplicationId}")
    public ResponseEntity deleteLoanApplication(@PathVariable Long loanApplicationId) {
        loanApplicationService.deleteLoanApplication(loanApplicationId);
        return new ResponseEntity(HttpStatus.OK);
    }


}
