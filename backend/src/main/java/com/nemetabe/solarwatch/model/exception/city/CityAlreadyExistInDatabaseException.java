package com.nemetabe.solarwatch.model.exception.city;

public class CityAlreadyExistInDatabaseException extends AlreadyExistInDatabaseException {
    public CityAlreadyExistInDatabaseException(String value, String specifier) {
        super("City", value, specifier);
    }
}
