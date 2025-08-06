package com.nemetabe.solarwatch.repository;

import com.nemetabe.solarwatch.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findCityByNameIgnoreCase(String name);
    Optional<City> findCityById(Long id);
    Optional<City> findCityByLatitudeAndLongitude(double latitude, double longitude);


}
