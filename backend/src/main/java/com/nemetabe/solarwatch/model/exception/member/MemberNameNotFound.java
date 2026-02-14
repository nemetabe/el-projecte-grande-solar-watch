package com.nemetabe.solarwatch.model.exception.member;

public class MemberNameNotFound extends MemberNotFoundException {
    public MemberNameNotFound(String value ) {
        super(value, "name");
    }
}
