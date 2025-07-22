package com.nemetabe.solarwatch.repository;

import com.nemetabe.solarwatch.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
//    List<City> findByCountry(String country);
//    List<City> findByState(String state);
//    List<City> findByCountryAndState(String country, String state);
//    List<City> findCitiesByName(String name);
    Optional<City> findCityById(Long id);
    Optional<City> findCityByNameIgnoreCase(String name);
    Optional<City> findCityByLatitudeAndLongitude(double latitude, double longitude);


}
