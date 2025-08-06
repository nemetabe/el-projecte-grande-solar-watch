package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.model.exception.MemberEmailNotFound;
import com.nemetabe.solarwatch.model.exception.MemberNameNotFound;
import com.nemetabe.solarwatch.model.payload.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.Role;
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


    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return memberRepository.findByName(username)
                .orElseThrow(() -> new MemberNameNotFound(username));

    }

    public Member findMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberEmailNotFound(email));
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

//TODO
    public boolean register(MemberRegistrationDto registrationDto, PasswordEncoder passwordEncoder) {
        if (memberRepository.findByName(registrationDto.name()).isPresent()) {
            return false;
        }
        Member member = new Member();
        member.setName(registrationDto.name());
        member.setPassword(passwordEncoder.encode(registrationDto.password()));
        member.setEmail(registrationDto.email());
        member.setRoles(Set.of(Role.ROLE_USER));
        memberRepository.save(member);
        return true;
    }



    //TODO
    public boolean deleteMember(int id) {
        return memberRepository.deleteMemberById(id);
    }



}


