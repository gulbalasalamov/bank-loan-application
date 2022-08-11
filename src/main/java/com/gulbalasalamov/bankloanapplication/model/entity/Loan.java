package com.gulbalasalamov.bankloanapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gulbalasalamov.bankloanapplication.model.LoanLimit;
import com.gulbalasalamov.bankloanapplication.model.LoanResult;
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

    @Enumerated(EnumType.STRING)
    private LoanLimit loanLimit;

    @Enumerated(EnumType.STRING)
    private LoanResult loanResult;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    //@Column(updatable = false, nullable = false)
    private Date loanDate;

    public Loan(LoanType loanType, LoanLimit loanLimit, LoanResult loanResult) {
        this.loanType = loanType;
        this.loanLimit = loanLimit;
        this.loanResult = loanResult;
    }
}
