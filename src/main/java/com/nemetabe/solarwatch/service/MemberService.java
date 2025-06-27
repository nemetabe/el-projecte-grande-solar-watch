package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.model.dto.member.MemberDto;
import com.nemetabe.solarwatch.model.dto.member.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.Role;
import com.nemetabe.solarwatch.model.exception.MemberNotFoundException;
import com.nemetabe.solarwatch.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return memberRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the repository", username)));

    }

    public void addRoleFor(Member member, Role role) {
        Set<Role> oldRoles = member.getRoles();
        Set<Role> copiedRoles = new HashSet<>(oldRoles);
        copiedRoles.add(role);
        member.setRoles(copiedRoles);
        memberRepository.save(member);
    }


    public ResponseEntity<Void> register(MemberRegistrationDto signUpRequest, PasswordEncoder encoder) {
        Member user = new Member();
        user.setName(signUpRequest.name());
        user.setPassword(encoder.encode(signUpRequest.password()));
        user.setEmail(signUpRequest.email());
        user.setRoles(Set.of(Role.ROLE_USER));
        memberRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public boolean deleteMember(int id) {
        return memberRepository.deleteMemberById(id);
    }


    public Member findMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
}


