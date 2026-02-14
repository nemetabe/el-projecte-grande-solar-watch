package com.nemetabe.solarwatch.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
@SequenceGenerator(name="seq", initialValue=2, allocationSize=100)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    private Long id;
    private String name;
    private String email;
    private String password;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "favourite_city_id")
    private City favouriteCity;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


}



