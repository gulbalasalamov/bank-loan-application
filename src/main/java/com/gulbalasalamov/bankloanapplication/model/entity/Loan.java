package com.gulbalasalamov.bankloanapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gulbalasalamov.bankloanapplication.model.LoanStatus;
import com.gulbalasalamov.bankloanapplication.model.LoanLimit;
import com.gulbalasalamov.bankloanapplication.model.LoanScoreResult;
import com.gulbalasalamov.bankloanapplication.model.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private Double loanLimit;

    @Enumerated(EnumType.STRING)
    private LoanScoreResult loanScoreResult;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date loanDate;

    private final Integer creditMultiplier = 4;


    public Loan(LoanType loanType, Double loanLimit, LoanScoreResult loanScoreResult, LoanStatus loanStatus, Date loanDate) {
        this.loanType = loanType;
        this.loanLimit = loanLimit;
        this.loanScoreResult = loanScoreResult;
        this.loanStatus = loanStatus;
        this.loanDate = loanDate;
    }

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "loan")
    private LoanApplication loanApplication;

}
