package com.nemetabe.solarwatch.service;


import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.member.MemberProfileDto;
import com.nemetabe.solarwatch.model.dto.savedCity.SavedCityDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.Member;
import com.nemetabe.solarwatch.model.entity.SavedCity;
import com.nemetabe.solarwatch.model.exception.city.CityNotFoundException;
import com.nemetabe.solarwatch.model.exception.city.SavedCityNotFoundException;
import com.nemetabe.solarwatch.repository.CityRepository;
import com.nemetabe.solarwatch.repository.SavedCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SavedCityService {

    CityRepository cityRepository;
    SavedCityRepository savedCityRepository;
    MemberService memberService;

    @Autowired
    public SavedCityService(CityRepository cityRepository, MemberService memberService, SavedCityRepository savedCityRepository){
        this.cityRepository = cityRepository;
        this.memberService = memberService;
        this.savedCityRepository = savedCityRepository;
    }

    private static SavedCityDto getDtoFrom(SavedCity savedCity) {
        return new SavedCityDto(
                savedCity.getId(), savedCity.getMember().getName(), savedCity.getCity().getName(), savedCity.getSavedDates());
    }

    private Member getMemberById(Long memberId) {
        return memberService.findMemberById(memberId);
    }

    private City getCityById(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId.toString()));
    }

    @Transactional
    public SavedCityDto saveDateForMember(Long memberId, Long cityId, LocalDate date) {
        Member member = getMemberById(memberId);
        City city = getCityById(cityId);

        Optional<SavedCity> existingSavedCity = savedCityRepository.findByMemberAndCity(member, city);

        SavedCity savedCity;
        if (existingSavedCity.isPresent()) {
            savedCity = existingSavedCity.get();
            savedCity.getSavedDates().add(date);
        } else {
            savedCity = new SavedCity();
            savedCity.setMember(member);
            savedCity.setCity(city);
            Set<LocalDate> savedDates = new HashSet<>();
            savedDates.add(date);
            savedCity.setSavedDates(savedDates);
        }

        SavedCity savedEntity = savedCityRepository.save(savedCity);
        return getDtoFrom(savedEntity);
    }

    public List<SavedCityDto> getSavedCitiesByMemberId(Long memberId) {
        Member member = getMemberById(memberId);
        List<SavedCity> savedCities = savedCityRepository.findByMember(member);
        return savedCities
                .stream()
                .map(SavedCityService::getDtoFrom)
                .collect(Collectors.toList());
    }


    public List<LocalDate> getSavedDatesForCityByMember(Long memberId, Long cityId) {
        Member member = getMemberById(memberId);
        City city = getCityById(cityId);

        SavedCity savedCity = savedCityRepository.findByMemberAndCity(member, city)
                .orElseThrow(SavedCityNotFoundException::new);
        return List.copyOf(savedCity.getSavedDates());
    }

    public MemberProfileDto setFavouriteCity(Long memberId, Long cityId) {
        Member member = memberService.setFavouriteCity(memberId, getCityById(cityId));
        City favouriteCity = member.getFavouriteCity();

        return new MemberProfileDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                new CityNameDto(favouriteCity.getId(), favouriteCity.getName(), favouriteCity.getCountry())
        );
    }

}
