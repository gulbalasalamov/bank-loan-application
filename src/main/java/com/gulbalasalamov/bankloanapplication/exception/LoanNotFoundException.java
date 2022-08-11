package com.gulbalasalamov.bankloanapplication.exception;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(String message){
         super(message);
    }
}
