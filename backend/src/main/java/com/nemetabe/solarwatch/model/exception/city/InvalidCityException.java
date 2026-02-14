package com.nemetabe.solarwatch.model.exception.city;

public class InvalidCityException extends IllegalArgumentException {
    public InvalidCityException(String cityName) {
        super("City with name" + cityName + ":  not found");
    }
}
