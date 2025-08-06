package com.nemetabe.solarwatch.repository;

import com.nemetabe.solarwatch.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    boolean deleteMemberById(Integer id);
}
