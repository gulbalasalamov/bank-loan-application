package com.gulbalasalamov.bankloanapplication.exception;

public class InvalidLoanApplicationException extends RuntimeException{
    public InvalidLoanApplicationException(String message){
        super("You have an already active loan application waiting for to finalize. You will be informed by SMS once the application finalized");
    }
}
