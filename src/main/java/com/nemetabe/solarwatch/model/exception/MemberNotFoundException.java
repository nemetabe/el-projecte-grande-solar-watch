package com.nemetabe.solarwatch.model.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("Member not Found");
    }
}
