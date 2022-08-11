package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanApplicationRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanRepository;
import com.gulbalasalamov.bankloanapplication.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final NotificationRepository notificationRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository,
                                  CustomerRepository customerRepository,
                                  LoanRepository loanRepository,
                                  NotificationRepository notificationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.customerRepository = customerRepository;
        this.loanRepository = loanRepository;
        this.notificationRepository = notificationRepository;
    }

    public void addLoanToLoanApplication(Long loanId, Long loanApplicationId) {
        var loanApplicationById = loanApplicationRepository.findById(loanId);
        var loanById = loanRepository.findById(loanId);
        loanApplicationById.ifPresent(loanApplication -> {
            Loan loan = loanById.get();
            loanApplication.getLoans().add(loan);
            loanApplication.setLoans(loanApplication.getLoans());
            loanApplicationRepository.save(loanApplication);
        });
    }


}
