package com.ginda.tictactoe.exception;

public class BadRequestException extends Exception{

    private final String fieldName;

    public BadRequestException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
