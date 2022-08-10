package com.gulbalasalamov.bankloanapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gulbalasalamov.bankloanapplication.model.LoanLimit;
import com.gulbalasalamov.bankloanapplication.model.LoanResult;
import com.gulbalasalamov.bankloanapplication.model.LoanType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private LoanType loanType;

    private LoanLimit loanLimit;

    private LoanResult loanResult;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(updatable = false, nullable = false)
    private Date loanDate;
}
