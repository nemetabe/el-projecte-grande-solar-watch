package com.nemetabe.solarwatch.model.entity;

import com.nemetabe.solarwatch.model.dto.member.MemberRegistrationDto;
import com.nemetabe.solarwatch.model.dto.member.MemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name="seq", initialValue=2, allocationSize=100)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    private Integer id;
    private String name;
    private String email;
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public Member(String name) {
        this.name = name;
    }

    public Member(MemberRegistrationDto memberRegistrationDto) {
        name = memberRegistrationDto.name();
        email = memberRegistrationDto.email();
        password = memberRegistrationDto.password();
    }



    public Member(MemberDto memberDto) {
        id = memberDto.id();
        name = memberDto.name();
        email = memberDto.email();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id)
                && Objects.equals(name, member.name)
                && Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

}



