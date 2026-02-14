package com.nemetabe.solarwatch.model.dto.member;

import com.nemetabe.solarwatch.model.entity.Member;
//TODO
public record MemberDto(Long id, String name, String email) {
    public MemberDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail());
    }
}
