package com.nemetabe.solarwatch.model.exception.city;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String id) {
        super("City with id " + id + " not found");
    }
}
