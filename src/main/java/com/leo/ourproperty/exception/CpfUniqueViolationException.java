package com.leo.ourproperty.exception;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String message){
        super(message);
    }
}
