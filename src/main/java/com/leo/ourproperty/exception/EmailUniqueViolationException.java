package com.leo.ourproperty.exception;

public class EmailUniqueViolationException extends RuntimeException{
    public EmailUniqueViolationException(String message){
        super(message);
    }
}
