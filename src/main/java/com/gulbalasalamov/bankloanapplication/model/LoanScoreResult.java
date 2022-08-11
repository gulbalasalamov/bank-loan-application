package com.gulbalasalamov.bankloanapplication.model;

public enum LoanScoreResult {

    NOT_RESULTED(0),
    REJECTED(500),
    APPROVED(1000);

    private Integer loanScoreLimit;


    LoanScoreResult(Integer loanScoreLimit) {
        this.loanScoreLimit = loanScoreLimit;
    }

    public Integer getLoanScoreLimit() {
        return loanScoreLimit;
    }

    public void setLoanScoreLimit(Integer loanScoreLimit) {
        this.loanScoreLimit = loanScoreLimit;
    }
}
