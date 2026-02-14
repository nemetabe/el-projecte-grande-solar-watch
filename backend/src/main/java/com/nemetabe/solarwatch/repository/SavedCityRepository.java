package com.nemetabe.solarwatch.repository;

import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.SavedCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedCityRepository extends JpaRepository<SavedCity, Long> {
    List<SavedCity> findAllByMember(Member member);
    Optional<SavedCity> findByMemberAndCity(Member member, City city);
    Optional<SavedCity> findById(Long savedCityId);
}
