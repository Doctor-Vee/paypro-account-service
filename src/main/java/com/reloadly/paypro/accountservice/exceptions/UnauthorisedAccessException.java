package com.reloadly.paypro.accountservice.exceptions;

public class UnauthorisedAccessException extends RuntimeException{

    public UnauthorisedAccessException(String message){
        super(message);
    }
}
