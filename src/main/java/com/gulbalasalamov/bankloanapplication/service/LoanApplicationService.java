package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.InvalidLoanApplicationException;
import com.gulbalasalamov.bankloanapplication.exception.LoanApplicationNotFoundException;
import com.gulbalasalamov.bankloanapplication.exception.LoanNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.LoanLimit;
import com.gulbalasalamov.bankloanapplication.model.LoanScoreResult;
import com.gulbalasalamov.bankloanapplication.model.LoanStatus;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.repository.LoanApplicationRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LoanApplicationService {

    private final CustomerService customerService;
    private final LoanService loanService;
    private final NotificationService notificationService;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanRepository loanRepository;

    public LoanApplicationService(CustomerService customerService,
                                  LoanService loanService,
                                  NotificationService notificationService,
                                  LoanApplicationRepository loanApplicationRepository,
                                  LoanRepository loanRepository) {
        this.customerService = customerService;
        this.loanService = loanService;
        this.notificationService = notificationService;
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanRepository = loanRepository;
    }

    protected Optional<LoanApplication> findLoanApplicationById(Long id) {
        return Optional.ofNullable(loanApplicationRepository.findById(id).orElseThrow(() ->
                new LoanNotFoundException("Related loan with id: " + id + " not found")));
    }

    public void createLoanApplication(String customerNationalIdentityNumber, Long loanId) {
        var customerByNationalIdentityNumber = customerService.findCustomerByNationalIdentityNumber(customerNationalIdentityNumber);
        var loanById = loanService.getLoanById(loanId);

        if (customerByNationalIdentityNumber.isPresent()) {
            LoanApplication loanApplication = new LoanApplication(customerByNationalIdentityNumber.get(), loanById);
            loanApplicationRepository.save(loanApplication);
        }

    }

    public LoanApplication getLoanApplicationById(Long loanApplicationId) {
        return findLoanApplicationById(loanApplicationId).get();
    }

    public void deleteLoanApplication(Long loanApplicationId) {
        var loanApplication = findLoanApplicationById(loanApplicationId);
        loanApplication.ifPresent(loanApplicationRepository::delete);
    }

    private Loan getNotResultedLoanApplicationOfCustomer(Customer customer) {

        var optionalLoan =
                customer.getLoanApplications().stream()
                        .filter(c -> c.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED))
                        .findFirst();

        return optionalLoan.isPresent()?optionalLoan.get().getLoan():null;

    }

    public Loan loanLimitCalculator(LoanApplication loanApplication) {

        Loan loan = loanApplication.getLoan();
        Customer loanCustomer = loanApplication.getCustomer();
        Integer loanScore = loanCustomer.getLoanScore();
        Double income = loanCustomer.getMonthlyIncome();
        Integer loanMultiplier = loan.getCreditMultiplier();

        boolean loanLimitCheck = (income >= LoanLimit.HIGHER.getLoanLimitAmount());
        boolean loanScoreCheck = (loanScore >= LoanScoreResult.APPROVED.getLoanScoreLimit());

        if (loanScoreCheck) {
            LoanLimit.SPECIAL.setLoanLimitAmount(income * loanMultiplier);
            loan.setLoanLimit(LoanLimit.SPECIAL.getLoanLimitAmount());
        } else if (loanLimitCheck) {
            loan.setLoanLimit(LoanLimit.HIGHER.getLoanLimitAmount());
        } else {
            loan.setLoanLimit(LoanLimit.LOWER.getLoanLimitAmount());
        }
        return loan;
    }

    private void finalizeLoanApplication(LoanApplication loanApplication) {

        Customer loanCustomer = loanApplication.getCustomer();

        var loanToUpdate = getNotResultedLoanApplicationOfCustomer(loanCustomer);
        if (loanToUpdate == null) return;
        log.info("Getting loan application for result");

        Integer loanScore = loanCustomer.getLoanScore();
        boolean loanScoreForApproval = (loanScore >= LoanScoreResult.REJECTED.getLoanScoreLimit());

        if (loanScoreForApproval) {
            loanToUpdate.setLoanScoreResult(LoanScoreResult.APPROVED);
            loanApplication.setLoan(loanToUpdate);
            loanToUpdate = loanLimitCalculator(loanApplication);
            //loanApplication.setLoan(loanToUpdate);
        } else {
            loanToUpdate.setLoanScoreResult(LoanScoreResult.REJECTED);
            loanToUpdate.setLoanStatus(LoanStatus.INACTIVE);
        }
        loanRepository.save(loanToUpdate);
        log.info("resulted the application");
        //TODO: modify sms
        log.info("Sent sms result");
//        });
    }

    private LoanApplication getActiveLoanApplicationOfCustomer(String nationalIdentityNumber) {
        Optional<Customer> customerByNationalIdentityNumber = customerService.findCustomerByNationalIdentityNumber(nationalIdentityNumber);

        LoanApplication loanApplication1 = customerByNationalIdentityNumber.get().getLoanApplications().stream()
                .findFirst()
                .get();
        finalizeLoanApplication(loanApplication1); // it fails here

        return customerByNationalIdentityNumber.get().getLoanApplications().stream()
                .filter(loanApplication -> loanApplication.getCustomer() == customerByNationalIdentityNumber.get())
                .filter(loanApplication -> loanApplication.getLoan().getLoanStatus() == LoanStatus.ACTIVE)
                .findAny()
                .orElseThrow(() -> new InvalidLoanApplicationException("."));
    }

    public Loan getActiveAndApprovedLoanApplicationOfCustomer(String nationalIdentityNumber) {
        LoanApplication activeLoanApplicationOfCustomer = getActiveLoanApplicationOfCustomer(nationalIdentityNumber);

        if (activeLoanApplicationOfCustomer.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED)) {
            throw new InvalidLoanApplicationException("!");
        }
        return activeLoanApplicationOfCustomer.getLoan();

    }


    /**
     * Manual adding. NOT TO USE AT THE MOMENT
     *
     * @param loanId
     * @param loanApplicationId
     */

    //TODO: known issue - the same loan can be assigned to multiple loan application although relation is 1-1
    public void addLoanToLoanApplication(Long loanId, Long loanApplicationId) {
        var loanApplicationById = loanApplicationRepository.findById(loanApplicationId);
        var loanById = loanService.findLoanById(loanId);
        loanApplicationById.ifPresent(loanApplication -> {
            Loan loan = loanById.get();
            loanApplication.setLoan(loan);
            loanApplicationRepository.save(loanApplication);
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

}
