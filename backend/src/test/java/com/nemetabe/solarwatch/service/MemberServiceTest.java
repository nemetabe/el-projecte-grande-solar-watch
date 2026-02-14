package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.model.exception.member.MemberIdNotFoundException;
import com.nemetabe.solarwatch.repository.CityRepository;
import com.nemetabe.solarwatch.repository.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nemetabe.solarwatch.model.payload.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.Role;
import com.nemetabe.solarwatch.model.exception.MemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private CityRepository cityRepository;

    private MemberService memberService;
    private Member testMember;
    private MemberRegistrationDto registrationDto;

    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        memberService = new MemberService(memberRepository, cityRepository);
        testMember = new Member();
        testMember.setName("testuser");
        testMember.setEmail("test@example.com");
        testMember.setPassword("encodedPassword");
        testMember.setRoles(Set.of(Role.ROLE_USER));

        registrationDto = new MemberRegistrationDto(
                "newuser", "new@example.com", "password123", "Budapest");
    }

    @Test
    void findMemberByName_ShouldReturnMember_WhenUserExists() {
        String username = "testuser";
        when(memberRepository.findByName(username)).thenReturn(Optional.of(testMember));

        Member result = memberService.findMemberByName(username);

        assertNotNull(result);
        assertEquals(username, result.getName());
        verify(memberRepository).findByName(username);
    }

    @Test
    void findMemberByName_ShouldThrowException_WhenUserNotFound() {
        String username = "nonexistentuser";
        when(memberRepository.findByName(username)).thenReturn(Optional.empty());

        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class,
                () -> memberService.findMemberByName(username));

        assertTrue(exception.getMessage().contains(username));
        verify(memberRepository).findByName(username);
    }

    @Test
    void findCurrentUser_ShouldReturnMember_WhenUserExists() {
        String username = "testuser";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        when(memberRepository.findByName(username)).thenReturn(Optional.of(testMember));

        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Member result = memberService.findCurrentUser();

            assertNotNull(result);
            assertEquals("testuser", result.getName());
            assertEquals("test@example.com", result.getEmail());
        }

        verify(memberRepository).findByName(username);
    }

    @Test
    void findCurrentUser_ShouldThrowException_WhenUserNotFound() {
        String username = "nonexistentuser";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        when(memberRepository.findByName(username)).thenReturn(Optional.empty());

        try (MockedStatic<SecurityContextHolder> mockedSecurityContext = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            MemberNotFoundException exception = assertThrows(MemberNotFoundException.class,
                    () -> memberService.findCurrentUser());

        }
    }

    @Test
    void addRoleFor_ShouldAddRoleToMember() {
        Set<Role> initialRoles = new HashSet<>(Set.of(Role.ROLE_USER));
        testMember.setRoles(initialRoles);
        Role newRole = Role.ROLE_ADMIN;

        memberService.addRoleFor(testMember, newRole);

        assertTrue(testMember.getRoles().contains(Role.ROLE_USER));
        assertTrue(testMember.getRoles().contains(Role.ROLE_ADMIN));
        assertEquals(2, testMember.getRoles().size());
        verify(memberRepository).save(testMember);
    }

    @Test
    void register_ShouldReturnTrue_WhenUserDoesNotExist() {
        String encodedPassword = "encodedPassword123";
        when(memberRepository.findByName("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);

        boolean result = memberService.register(registrationDto, passwordEncoder);

        assertTrue(result);
        verify(memberRepository).findByName("newuser");
        verify(passwordEncoder).encode("password123");
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void register_ShouldReturnFalse_WhenUserAlreadyExists() {
        when(memberRepository.findByName("newuser")).thenReturn(Optional.of(testMember));

        boolean result = memberService.register(registrationDto, passwordEncoder);

        assertFalse(result);
        verify(memberRepository).findByName("newuser");
        verify(passwordEncoder, never()).encode(anyString());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void findMemberByEmail_ShouldReturnMember_WhenEmailExists() {
        String email = "test@example.com";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(testMember));

        Member result = memberService.findMemberByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(memberRepository).findByEmail(email);
    }

    @Test
    void findMemberByEmail_ShouldThrowException_WhenEmailNotFound() {
        String email = "nonexistent@example.com";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class,
                () -> memberService.findMemberByEmail(email));

        //assertEquals(email, exception.getMessage());
        verify(memberRepository).findByEmail(email);
    }

    @Test
    void deleteMember_ShouldReturnTrue_WhenDeletionSuccessful() {
        int memberId = 1;
        when(memberRepository.deleteMemberById(memberId)).thenReturn(true);

        boolean result = memberService.deleteMember(memberId);

        assertTrue(result);
        verify(memberRepository).deleteMemberById(memberId);
    }

    @Test
    void deleteMember_ShouldReturnFalse_WhenDeletionFails() {
        int memberId = 999;
        when(memberRepository.deleteMemberById(memberId)).thenReturn(false);

        boolean result = memberService.deleteMember(memberId);

        assertFalse(result);
        verify(memberRepository).deleteMemberById(memberId);
    }
}