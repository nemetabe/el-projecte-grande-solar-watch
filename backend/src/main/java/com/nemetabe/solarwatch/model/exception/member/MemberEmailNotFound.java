package com.nemetabe.solarwatch.model.exception.member;

public class MemberEmailNotFound extends MemberNotFoundException{
    public MemberEmailNotFound(String value) {
        super(value, "email");
    }
}
