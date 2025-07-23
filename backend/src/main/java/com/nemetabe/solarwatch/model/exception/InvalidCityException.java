package com.nemetabe.solarwatch.model.exception;

public class InvalidCityException extends IllegalArgumentException {
    public InvalidCityException() {
        super("City with name not found");
    }
}
