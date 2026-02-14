package com.nemetabe.solarwatch.service;


import com.nemetabe.solarwatch.model.entity.*;
import com.nemetabe.solarwatch.model.exception.city.SavedCityNotFoundException;
import com.nemetabe.solarwatch.repository.SavedCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SavedCityService {


    private final SavedCityRepository savedCityRepository;

    @Autowired
    public SavedCityService(SavedCityRepository savedCityRepository) {
        this.savedCityRepository = savedCityRepository;
    }

    @Transactional
    public SavedCity saveSolarDate(Member member, City city, SolarTimes solarTimes) {
        SavedCity savedCity = findOrCreate(member, city);
        savedCity.addSolarDateId(new SolarDateId(solarTimes));

        return savedCityRepository.save(savedCity);
    }

    private SavedCity findOrCreate(Member member, City city) {
        return savedCityRepository
                .findByMemberAndCity(member, city)
                .orElseGet(() -> {
                        SavedCity newSaved = new SavedCity();
                        newSaved.setMember(member);
                        newSaved.setCity(city);
                        return newSaved;
                    }
                );
    }

    public SavedCity getById(Long savedCityId) {
        return savedCityRepository
                .findById(savedCityId)
                .orElseThrow(SavedCityNotFoundException::new);
    }

    public SavedCity getByMemberAndCity(Member member, City city) {
        return savedCityRepository
                .findByMemberAndCity(member, city)
                .orElseThrow(SavedCityNotFoundException::new);
    }
    public List<SavedCity> getAllSavedCities(Member member) {
        return savedCityRepository.findAllByMember(member);
    }

    public Set<SolarDateId> getSolarDateIds(Member member, City city) {
            return savedCityRepository.findByMemberAndCity(member, city)
                    .orElseThrow(SavedCityNotFoundException::new)
                    .getSolarDateIds();
    }

    public void deleteSavedCityById(Long id) {
        SavedCity savedCity = getById(id);
        savedCityRepository.delete(savedCity);
    }

    public void deleteSavedCityByMemberAndCity(Member member, City city) {
        SavedCity savedCity = savedCityRepository.findByMemberAndCity(member, city)
                .orElseThrow(SavedCityNotFoundException::new);
        savedCityRepository.delete(savedCity);
    }

//    public void deleteSolarDateId(SolarDateId solarDateId) {
//        Member member
//    }
}
