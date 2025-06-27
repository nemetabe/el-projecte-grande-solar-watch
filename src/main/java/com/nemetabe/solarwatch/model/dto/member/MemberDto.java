package com.nemetabe.solarwatch.model.dto.member;

import com.nemetabe.solarwatch.model.entity.Member;

public record MemberDto(Integer id, String name, String email) {
    public MemberDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail());
    }
}
