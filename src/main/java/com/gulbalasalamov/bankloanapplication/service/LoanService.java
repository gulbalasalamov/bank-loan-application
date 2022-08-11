package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.LoanNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.LoanStatus;
import com.gulbalasalamov.bankloanapplication.model.LoanScoreResult;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    public LoanService(LoanRepository loanRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    public void createLoan(Loan loan) {
        //Loan loan = new Loan(null, null, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE, new Date());
        loanRepository.save(loan);
    }

    protected Optional<Loan> findLoanById(Long id) {
        return Optional.ofNullable(loanRepository.findById(id).orElseThrow(() ->
                new LoanNotFoundException("Related loan with id: " + id + " not found")));
    }

    private void creditLimitCalculator() {
    }


}
