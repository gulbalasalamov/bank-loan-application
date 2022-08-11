package com.gulbalasalamov.bankloanapplication.repository;

import com.gulbalasalamov.bankloanapplication.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {


}
