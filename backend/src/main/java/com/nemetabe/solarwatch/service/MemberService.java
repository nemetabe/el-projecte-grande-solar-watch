package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.mapper.CityMapper;
import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;
import com.nemetabe.solarwatch.model.dto.member.MemberProfileDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.exception.member.MemberEmailNotFound;
import com.nemetabe.solarwatch.model.exception.member.MemberIdNotFoundException;
import com.nemetabe.solarwatch.model.exception.member.MemberNameNotFound;
import com.nemetabe.solarwatch.model.payload.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.Role;
import com.nemetabe.solarwatch.repository.CityRepository;
import com.nemetabe.solarwatch.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class MemberService {

    private final CityRepository cityRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, CityRepository cityRepository) {
        this.memberRepository = memberRepository;
        this.cityRepository = cityRepository;
    }

    public Member findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return memberRepository.findByName(username)
                .orElseThrow(() -> new MemberNameNotFound(username));
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberIdNotFoundException(id));
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberEmailNotFound(email));
    }

    public Member findMemberByName(String username) {
        return memberRepository.findByName(username)
                .orElseThrow(() -> new MemberNameNotFound(username));
    }

    public void addRoleFor(Member member, Role role) {
        Set<Role> oldRoles = member.getRoles();
        Set<Role> copiedRoles = new HashSet<>(oldRoles);
        copiedRoles.add(role);
        member.setRoles(copiedRoles);
        memberRepository.save(member);
    }

    public MemberProfileDto setFavouriteCity(Long memberId, City city) {
        Member member = getMember(memberId);
        member.setFavouriteCity(city);
        memberRepository.save(member);

        return new MemberProfileDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                new CityNameDto(city.getId(), city.getName(), city.getCountry())
        );
    }

    public CityResponseDto getFavouriteCity(Long memberId) {
        Member member = getMember(memberId);
        return CityMapper.toDto(member.getFavouriteCity());
    }

    private Member getMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new MemberIdNotFoundException(memberId);
        }
        return optionalMember.get();
    }

    public boolean deleteMember(Long memberId) {
        getMember(memberId);
        return memberRepository.deleteMemberById(memberId);
    }

//TODO
    public boolean register(MemberRegistrationDto registrationDto, PasswordEncoder passwordEncoder) {
        if (memberRepository.findByName(registrationDto.name()).isPresent()) {
            return false;
        }
        City city;
        if (cityRepository.findCityByNameIgnoreCase(registrationDto.favouriteCity()).isPresent()) {
            city = cityRepository.findCityByNameIgnoreCase(registrationDto.favouriteCity()).get();
        } else {
            city = null;
        }
        Member member = new Member();
        member.setName(registrationDto.name());
        member.setPassword(passwordEncoder.encode(registrationDto.password()));
        member.setEmail(registrationDto.email());
        member.setRoles(Set.of(Role.ROLE_USER));
        member.setFavouriteCity(city);
        memberRepository.save(member);
        return true;
    }
}


