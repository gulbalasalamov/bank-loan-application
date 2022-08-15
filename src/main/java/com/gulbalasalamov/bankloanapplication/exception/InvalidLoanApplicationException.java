package com.gulbalasalamov.bankloanapplication.exception;

public class InvalidLoanApplicationException extends RuntimeException{
    public InvalidLoanApplicationException(String message){
        super("You have an active loan application. "+message);
    }
}
