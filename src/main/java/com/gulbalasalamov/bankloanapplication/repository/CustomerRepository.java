package com.gulbalasalamov.bankloanapplication.repository;

import com.gulbalasalamov.bankloanapplication.model.dto.CustomerLoanApplicationResponse;
import com.gulbalasalamov.bankloanapplication.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String>{
    Optional<Customer> findByNationalIdentityNumber(String nationalIdentityNumber);

//    @Query(value = "select l from customer c " +
//            "            inner join customer_loan_applications l" +
//            "            on l.customer_id = c.id where (c.id = :customerId)" , nativeQuery = true)
//    List<LoanApplication> findLoanApplication(String customerId);

//    /**
//     * We fetch multiple columns. So return type either should be String[] or a class with specified fields.
//     * This query return a new response DTO object. The query maps the result columns.
//      * @return List<CustomerLoanApplicationResponse>
//     */
//    @Query("SELECT new com.gulbalasalamov.bankloanapplication.model.dto.CustomerLoanApplicationResponse " +
//            "(c.firstName, l.description) " +
//            "FROM Customer c " +
//            "JOIN c.loanApplications l")
//    public List<CustomerLoanApplicationResponse> getJoinInformation();



}
