package com.ginda.tictactoe.exception;

public class ConflictException extends Exception{

    private final String fieldName;

    public ConflictException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
