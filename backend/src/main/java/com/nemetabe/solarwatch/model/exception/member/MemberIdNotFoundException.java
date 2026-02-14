package com.nemetabe.solarwatch.model.exception.member;

public class MemberIdNotFoundException extends MemberNotFoundException {
    public MemberIdNotFoundException(Long id) {
        super(id.toString(), "id");
    }
}
