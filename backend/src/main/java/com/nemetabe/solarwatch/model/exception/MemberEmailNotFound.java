package com.nemetabe.solarwatch.model.exception;

public class MemberEmailNotFound extends MemberNotFoundException{
    public MemberEmailNotFound(String value) {
        super(value, "email");
    }
}
