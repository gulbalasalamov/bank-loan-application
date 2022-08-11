package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.CustomerNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanApplicationRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanRepository;
import com.gulbalasalamov.bankloanapplication.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {
//    private final LoanApplicationRepository loanApplicationRepository;
//    private final CustomerRepository customerRepository;
//    private final LoanRepository loanRepository;
//    private final NotificationRepository notificationRepository;
//
//    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository,
//                                  CustomerRepository customerRepository,
//                                  LoanRepository loanRepository,
//                                  NotificationRepository notificationRepository) {
//        this.loanApplicationRepository = loanApplicationRepository;
//        this.customerRepository = customerRepository;
//        this.loanRepository = loanRepository;
//        this.notificationRepository = notificationRepository;
//    }
    private final CustomerService customerService;
    private final LoanService loanService;
    private final NotificationService notificationService;
    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationService(CustomerService customerService,
                                  LoanService loanService,
                                  NotificationService notificationService,
                                  LoanApplicationRepository loanApplicationRepository) {
        this.customerService = customerService;
        this.loanService = loanService;
        this.notificationService = notificationService;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public void createLoanApplication(LoanApplication loanApplication){
        //LoanApplication loanApplication = new LoanApplication();
        loanApplicationRepository.save(loanApplication);
    }

    public void addLoanToLoanApplication(Long loanId, Long loanApplicationId) {
        var loanApplicationById = loanApplicationRepository.findById(loanApplicationId);
        var loanById = loanService.findLoanById(loanId);
        loanApplicationById.ifPresent(loanApplication -> {
            Loan loan = loanById.get();
            loanApplication.getLoans().add(loan);
            loanApplication.setLoans(loanApplication.getLoans());
            loanApplicationRepository.save(loanApplication);
        });
    }



//    boolean isThereAnyActiveApplicationByCustomer(){}
//
//    void UpdateNotResultedApplication{}
//    void deleteCreditApplication(){}
//



}
