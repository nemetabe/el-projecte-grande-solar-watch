package com.nemetabe.solarwatch.repository;

import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SolarRepository extends JpaRepository<SolarTimes, Long> {


    @Query("SELECT s FROM SolarTimes s JOIN FETCH s.city WHERE s.city.name = :cityName AND s.date = :date")
    Optional<SolarTimes> findByCityNameAndDate(@Param("cityName") String cityName, @Param("date") LocalDate date);

    Optional<SolarTimes> findFirstByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(LocalDate date, String cityName);

    Optional<SolarTimes> findByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(LocalDate localDate, String cityName);

    List<SolarTimes> findAllByCity_NameAndCity_Country(String cityName, String country);
}
