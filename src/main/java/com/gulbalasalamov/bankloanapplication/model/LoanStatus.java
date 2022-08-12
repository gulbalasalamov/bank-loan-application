package com.gulbalasalamov.bankloanapplication.model;

import com.gulbalasalamov.bankloanapplication.model.entity.Loan;

public enum LoanStatus {
    ACTIVE, INACTIVE;
    public static LoanStatus get(int index){
        return LoanStatus.values()[index];
    }
}
