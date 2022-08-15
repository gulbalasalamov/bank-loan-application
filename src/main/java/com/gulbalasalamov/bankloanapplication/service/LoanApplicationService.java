package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.InvalidLoanApplicationException;
import com.gulbalasalamov.bankloanapplication.exception.LoanNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.LoanLimit;
import com.gulbalasalamov.bankloanapplication.model.LoanScoreResult;
import com.gulbalasalamov.bankloanapplication.model.LoanStatus;
import com.gulbalasalamov.bankloanapplication.model.LoanType;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import com.gulbalasalamov.bankloanapplication.repository.CustomerRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanApplicationRepository;
import com.gulbalasalamov.bankloanapplication.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class LoanApplicationService {

    private  LoanRepository loanRepository;
    private CustomerRepository customerRepository;
    private LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationService(LoanRepository loanRepository, CustomerRepository customerRepository, LoanApplicationRepository loanApplicationRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    private Loan createLoan() {
        var newLoan = new Loan(LoanType.PERSONAL, 0.0, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE, new Date());
        loanRepository.save(newLoan);
        return newLoan;
    }

    public void createLoanApplication(String nationalIdentityNumber) {
        var customerByNationalIdentityNumber = customerRepository.findByNationalIdentityNumber(nationalIdentityNumber);
        customerByNationalIdentityNumber.ifPresent(customer -> {
            LoanApplication loanApplication = new LoanApplication(customerByNationalIdentityNumber.get(), createLoan());
            loanApplicationRepository.save(loanApplication);
        });
    }



    public Loan getLoanApplicationResult(String nationalIdentityNumber) {
        LoanApplication finalizedApplication = finalizeLoanApplication(nationalIdentityNumber);

        if (finalizedApplication.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED)) {
            throw new InvalidLoanApplicationException("!");
        } else if (finalizedApplication.getLoan().getLoanScoreResult().equals(LoanScoreResult.REJECTED)) {
            return finalizedApplication.getLoan();
        }
        return finalizedApplication.getLoan();
    }


    private Loan loanLimitCalculator(LoanApplication loanApplication) {

        var loan = loanApplication.getLoan();
        var loanCustomer = loanApplication.getCustomer();
        var loanScore = loanCustomer.getLoanScore();
        var income = loanCustomer.getMonthlyIncome();
        var loanMultiplier = loan.getCreditMultiplier();

        var loanLimitCheck = (income >= LoanLimit.HIGHER.getLoanLimitAmount());
        var loanScoreCheck = (loanScore >= LoanScoreResult.APPROVED.getLoanScoreLimit());

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

    private void verifyLoan(LoanApplication loanApplication) {

        var loanCustomer = loanApplication.getCustomer();

        var loanToUpdate = getNotResultedLoanApplicationOfCustomer(loanCustomer);
        if (loanToUpdate == null) return;
        log.info("Getting loan application for result");

        var loanScore = loanCustomer.getLoanScore();
        var loanScoreForApproval = (loanScore >= LoanScoreResult.REJECTED.getLoanScoreLimit());

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
    }

    private Loan getNotResultedLoanApplicationOfCustomer(Customer customer) {

        var optionalLoan =
                customer.getLoanApplications().stream()
                        .filter(c -> c.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED))
                        .findFirst();

        return optionalLoan.isPresent() ? optionalLoan.get().getLoan() : null;

    }

    private LoanApplication finalizeLoanApplication(String nationalIdentityNumber) {
        Optional<Customer> customerByNationalIdentityNumber = customerRepository.findByNationalIdentityNumber(nationalIdentityNumber);

        if (customerByNationalIdentityNumber.isPresent()) {
            LoanApplication loanApplication1 = customerByNationalIdentityNumber.get().getLoanApplications().stream()
                    .findFirst()
                    .get();
            verifyLoan(loanApplication1);
        }

        return customerByNationalIdentityNumber.get().getLoanApplications().stream()
                .filter(loanApplication -> loanApplication.getCustomer() == customerByNationalIdentityNumber.get())
                //.filter(loanApplication -> loanApplication.getLoan().getLoanStatus() == LoanStatus.ACTIVE)
                .findAny()
                .orElseThrow(() -> new InvalidLoanApplicationException("."));
    }




    protected Optional<LoanApplication> findLoanApplicationById(Long id) {
        return Optional.ofNullable(loanApplicationRepository.findById(id).orElseThrow(() ->
                new LoanNotFoundException("Related loan with id: " + id + " not found")));
    }

    public LoanApplication getLoanApplicationById(Long loanApplicationId) {
        return findLoanApplicationById(loanApplicationId).get();
    }



}
