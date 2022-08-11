package com.gulbalasalamov.bankloanapplication.repository;

import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import com.gulbalasalamov.bankloanapplication.model.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String>{
    Optional<Customer> findByNationalIdentityNumber(String nationalIdentityNumber);

    @Query(value = "select l from customer c " +
            "            inner join customer_loan_applications l" +
            "            on l.customer_id = c.id where (c.id = :customerId)" , nativeQuery = true)
    List<LoanApplication> findLoanApplication(String customerId);



}
