package com.projekt.VirtualBetting.exception;

public class MissingFieldException extends Exception{
    public MissingFieldException(String fieldName) {
        super(fieldName);
    }
}
