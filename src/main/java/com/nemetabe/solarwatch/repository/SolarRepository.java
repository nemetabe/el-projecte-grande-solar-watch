package com.nemetabe.solarwatch.repository;

import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SolarRepository extends JpaRepository<SolarTimes, Long> {

    @Override
    Optional<SolarTimes> findById(Long id);

//    @Query( value = "Select c.solarTimes from SolarTimes st JOIN City c ON st.city.id = c.id " +
//    "WHERE c.name = ?1")

    Optional<SolarTimes> findByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(LocalDate localDate, String cityName);
    Optional<SolarTimes> findByCity_AndDate(City city, LocalDate date);
    List<SolarTimes> findFirstByCity_NameContainingIgnoreCase(String cityName);
    List<SolarTimes> findAllByCity_NameAndCity_Country(String cityName, String country);


    SolarTimes save(SolarTimes solarTimes);
}
