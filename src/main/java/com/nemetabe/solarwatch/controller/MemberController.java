package com.nemetabe.solarwatch.controller;

import com.nemetabe.solarwatch.model.dto.member.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.payload.JwtResponse;
import com.nemetabe.solarwatch.model.payload.UserRequest;
import com.nemetabe.solarwatch.security.jwt.JwtUtils;
import com.nemetabe.solarwatch.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final MemberService memberService;

    public MemberController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.encoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody MemberRegistrationDto memberRegistration) {
        return memberService.register(memberRegistration, encoder);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
        Member loggedMember = memberService.findMemberByEmail(userDetails.getUsername());
        return ResponseEntity
                .ok(new JwtResponse(jwt, loggedMember.getName(), roles));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public String me() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return "Hello " + user.getUsername();
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        return memberService.deleteMember(id);
    }


    //Currently for test purposes
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public\n";
    }
}
