package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.LoanNotFoundException;

import com.gulbalasalamov.bankloanapplication.model.LoanLimit;
import com.gulbalasalamov.bankloanapplication.model.LoanScoreResult;
import com.gulbalasalamov.bankloanapplication.model.dto.CustomerDTO;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    public LoanService(LoanRepository loanRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    protected Optional<Loan> findLoanById(Long id) {
        return Optional.ofNullable(loanRepository.findById(id).orElseThrow(() ->
                new LoanNotFoundException("Related loan with id: " + id + " not found")));
    }

    public void createLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan getLoanById(Long loanId) {
        return findLoanById(loanId).get();
    }

    //TODO: known issue:     "message": "Can not set java.util.Date field com.gulbalasalamov.bankloanapplication.model.entity.Loan.loanDate to java.lang.String",
    public void updateLoanPartially(Long loanId, Map<Object, Object> objectMap) {
        var loanById = findLoanById(loanId);
        loanById.ifPresent(loan -> objectMap.forEach((key, value) -> {

            Field field = ReflectionUtils.findField(Loan.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, loan, value);
            loanRepository.save(loan);
        }));
    }

    public void updateLoan(Long loanId, Loan loan) {
        var loanById = findLoanById(loanId);
        loanById.ifPresent(updatedLoan -> {
                    updatedLoan.setId(loan.getId());
                    updatedLoan.setLoanType(loan.getLoanType());
                    updatedLoan.setLoanLimit(loan.getLoanLimit());
                    updatedLoan.setLoanDate(loan.getLoanDate());
                    updatedLoan.setLoanStatus(loan.getLoanStatus());
                    updatedLoan.setLoanScoreResult(loan.getLoanScoreResult());
                    updatedLoan.setLoanApplication(loan.getLoanApplication());
                    loanRepository.save(updatedLoan);
                }
        );
    }

    public void deleteLoan(Long loanId) {
        var loan = findLoanById(loanId);
        loan.ifPresent(loanRepository::delete);
    }




}
