package com.nemetabe.solarwatch.controller;

import com.nemetabe.solarwatch.model.payload.MemberLoginDto;
import com.nemetabe.solarwatch.model.payload.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.payload.JwtResponseDto;
import com.nemetabe.solarwatch.security.jwt.JwtUtils;
import com.nemetabe.solarwatch.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Autowired
    public MemberController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, MemberService memberService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/register") //TODO
    public ResponseEntity<Void> createUser(@RequestBody MemberRegistrationDto memberRegistration) {
        if (memberService.register(memberRegistration, passwordEncoder)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/auth/login") //TODO
    public ResponseEntity<?> authenticateUser(@RequestBody MemberLoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity
                .ok(new JwtResponseDto(token, userDetails.getUsername(), roles));//TODO authResponse
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


    //for test purposes
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public\n";
    }
}
