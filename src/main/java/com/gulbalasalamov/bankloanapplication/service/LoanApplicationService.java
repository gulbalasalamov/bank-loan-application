package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.LoanNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.model.entity.Notification;
import com.gulbalasalamov.bankloanapplication.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationService {

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

    protected Optional<LoanApplication> findLoanApplicationById(Long id) {
        return Optional.ofNullable(loanApplicationRepository.findById(id).orElseThrow(() ->
                new LoanNotFoundException("Related loan with id: " + id + " not found")));
    }

    public void createLoanApplication(LoanApplication loanApplication) {
        //LoanApplication loanApplication = new LoanApplication();
        loanApplicationRepository.save(loanApplication);
    }

    public LoanApplication getLoanApplicationById(Long loanApplicationId) {
        return findLoanApplicationById(loanApplicationId).get();
    }

    public void deleteLoanApplication(Long loanApplicationId) {
        var loanApplication = findLoanApplicationById(loanApplicationId);
        loanApplication.ifPresent(loanApplicationRepository::delete);
    }

    //TODO: known issue - the same loan can be assigned to multiple loan application although relation is 1-1
    public void addLoanToLoanApplication(Long loanId, Long loanApplicationId) {
        var loanApplicationById = loanApplicationRepository.findById(loanApplicationId);
        var loanById = loanService.findLoanById(loanId);
        loanApplicationById.ifPresent(loanApplication -> {
            Loan loan = loanById.get();
            loanApplication.setLoan(loan);
            loanApplicationRepository.save(loanApplication);
//            loanApplication.getLoans().add(loan);
//            loanApplication.setLoans(loanApplication.getLoans());
//            loanApplicationRepository.save(loanApplication);
        });
    }

    public void addNotificationToLoanApplication(Long loanApplicationId, Long notificationId) {
        var loanApplicationById = findLoanApplicationById(loanApplicationId);
        var notification = notificationService.getNotificationById(notificationId);

        loanApplicationById.ifPresent(loanApplication -> {
            loanApplication.setNotification(notification);
            loanApplicationRepository.save(loanApplication);
        });

    }


    public void isTHereAnyActiveApplicationByCustomer(String nationalIdentityNumber) {
        Optional<Customer> customerByNationalIdentityNumber = customerService.findCustomerByNationalIdentityNumber(nationalIdentityNumber);
        List<LoanApplication> loanApplications = customerByNationalIdentityNumber.get().getLoanApplications();

    }


//    boolean isThereAnyActiveApplicationByCustomer(){}
//
//    void UpdateNotResultedApplication{}
//    void deleteCreditApplication(){}
//


}
