package com.gulbalasalamov.bankloanapplication.model;

public enum LoanLimit {
    LOWER(10000.00,5000.00),
    HIGHER(20000.00,5000.00),
    SPECIAL(0.00,0.00);

    private Double loanLimitAmount;
    private Double incomeLimit;

    LoanLimit(Double loanLimitAmount, Double incomeLimit) {
        this.loanLimitAmount = loanLimitAmount;
        this.incomeLimit = incomeLimit;
    }

    public Double getLoanLimitAmount() {
        return loanLimitAmount;
    }

    public void setLoanLimitAmount(Double loanLimitAmount) {
        this.loanLimitAmount = loanLimitAmount;
    }

    public Double getIncomeLimit() {
        return incomeLimit;
    }

    public void setIncomeLimit(Double incomeLimit) {
        this.incomeLimit = incomeLimit;
    }
}
