package com.nemetabe.solarwatch.model.exception.date;

public class InvalidDateException extends IllegalArgumentException {

    public InvalidDateException() {
        super("Invalid date");
    }

}
