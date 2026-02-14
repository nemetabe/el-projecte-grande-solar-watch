package com.nemetabe.solarwatch.model.exception.solar;

public class SolarTimesNotFoundException extends RuntimeException {
    public SolarTimesNotFoundException(String value, String specifier) {
        super("Could not find solar times with " + specifier + ": "  + value);
    }
}
