package com.nemetabe.solarwatch.security.service;

import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.repository.MemberRepository;
import com.nemetabe.solarwatch.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Member foundMember = memberRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role role : foundMember.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.name()));
        }

        return new User(foundMember.getName(), foundMember.getPassword(), roles);
    }
}