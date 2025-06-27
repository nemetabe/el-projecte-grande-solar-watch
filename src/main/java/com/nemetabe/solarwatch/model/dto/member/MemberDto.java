package com.nemetabe.solarwatch.model.dto.member;

import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.City;

import java.util.List;

public record MemberDto(Integer id, String name, String email) {
    public MemberDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail());
    }
}
