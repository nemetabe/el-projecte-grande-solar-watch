package com.nemetabe.solarwatch.model.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String value, String specifier ) {
        super("could not find member with "+ specifier + " " + value);
    }
}
