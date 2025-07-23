package com.nemetabe.solarwatch.model.exception;

public class InvalidDateException extends IllegalArgumentException {

    public InvalidDateException() {
        super("Invalid date");
    }

}
