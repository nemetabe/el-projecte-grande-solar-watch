package com.nemetabe.solarwatch.model.exception.city;

public class AlreadyExistInDatabaseException extends RuntimeException {
    public AlreadyExistInDatabaseException(String type, String value, String specifier) {
        super(type + " with " + specifier + value + " already exists in the database" );
    }
}
