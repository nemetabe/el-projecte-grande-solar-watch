package com.nemetabe.solarwatch.controller;

import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;
import com.nemetabe.solarwatch.model.dto.member.MemberProfileDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.exception.city.CityNotFoundException;
import com.nemetabe.solarwatch.model.payload.AuthResponseDto;
import com.nemetabe.solarwatch.model.payload.MemberLoginDto;
import com.nemetabe.solarwatch.model.payload.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.payload.JwtResponseDto;
import com.nemetabe.solarwatch.repository.CityRepository;
import com.nemetabe.solarwatch.security.jwt.JwtUtils;
import com.nemetabe.solarwatch.service.MemberService;
import com.nemetabe.solarwatch.service.SolarService;
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
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final CityRepository cityRepository;

    @Autowired
    public MemberController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, MemberService memberService, PasswordEncoder passwordEncoder, CityRepository cityRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.cityRepository = cityRepository;
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(@RequestBody MemberRegistrationDto memberRegistration) {
        memberService.register(memberRegistration, passwordEncoder);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody MemberLoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
        Member member = memberService.findMemberByName(loginRequest.username());
        boolean hasFav = member.getFavouriteCity()!= null;
        City city =  member.getFavouriteCity();
        CityNameDto cityDto = hasFav ? new CityNameDto(city.getId(), city.getName(), city.getCountry()) : null;
        return ResponseEntity.ok(new AuthResponseDto(
                token,
                member.getName(),
                member.getId(),
                cityDto
                ));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public String me() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return "Hello " + user.getUsername();
    }

    @GetMapping("/{memberId}/favourite-city")
    public CityResponseDto getFavouriteCity(@PathVariable Long memberId) {
        return memberService.getFavouriteCity(memberId);
    }

    @PutMapping("/{memberId}/favourite-city/{cityId}")
    public MemberProfileDto updateFavouriteCity(@PathVariable Long memberId, @PathVariable Long cityId) {
        City favCity = cityRepository.findCityById(cityId).orElseThrow(()-> new CityNotFoundException(cityId.toString()));
        return memberService.setFavouriteCity(memberId, favCity);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }
}
