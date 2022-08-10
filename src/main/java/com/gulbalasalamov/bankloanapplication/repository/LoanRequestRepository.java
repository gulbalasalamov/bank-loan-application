package com.gulbalasalamov.bankloanapplication.repository;

import com.gulbalasalamov.bankloanapplication.model.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {
}
