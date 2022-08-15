package com.gulbalasalamov.bankloanapplication.controller;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.model.entity.Notification;
import com.gulbalasalamov.bankloanapplication.service.LoanApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/v1/loanapplication")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("/create/{nationalIdentityNumber}")
    public ResponseEntity<Integer> createLoanApplication(@PathVariable String nationalIdentityNumber) {
        loanApplicationService.createLoanApplication(nationalIdentityNumber);
        return new ResponseEntity<Integer>(HttpStatus.CREATED);
    }

    @GetMapping("/get/{loanApplicationId}")
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable Long loanApplicationId) {
        return new ResponseEntity<>(loanApplicationService.getLoanApplicationById(loanApplicationId), HttpStatus.OK);
    }


    @GetMapping(value = "/active-and-approved/{nationalIdentityNumber}")
    public ResponseEntity<Loan> getActiveAndApprovedCreditApplicationByCustomer(@PathVariable String nationalIdentityNumber) {
        ;
        return new ResponseEntity(loanApplicationService.getActiveAndApprovedLoanApplicationOfCustomer(nationalIdentityNumber), HttpStatus.OK);
    }





    //    @PutMapping("/{loanApplicationId}/loan/{loanId}")
//    public ResponseEntity addLoanToLoanApplication(@PathVariable Long loanApplicationId, @PathVariable Long loanId) {
//        loanApplicationService.addLoanToLoanApplication(loanId, loanApplicationId);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{loanApplicationId}")
//    public ResponseEntity deleteLoanApplication(@PathVariable Long loanApplicationId) {
//        loanApplicationService.deleteLoanApplication(loanApplicationId);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @PutMapping("/{loanApplicationId}/notification/{notificationId}")
//    public ResponseEntity addNotificationToLoanApplication(@PathVariable Long loanApplicationId, @PathVariable Long notificationId) {
//        loanApplicationService.addNotificationToLoanApplication(loanApplicationId, notificationId);
//        return new ResponseEntity(HttpStatus.OK);
//    }

}
