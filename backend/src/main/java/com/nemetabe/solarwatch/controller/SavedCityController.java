package com.nemetabe.solarwatch.controller;


import com.nemetabe.solarwatch.model.dto.member.MemberProfileDto;
import com.nemetabe.solarwatch.model.dto.savedCity.SaveDateRequest;
import com.nemetabe.solarwatch.model.dto.savedCity.SavedCityDto;
import com.nemetabe.solarwatch.service.SavedCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/members/{memberId}")
public class SavedCityController {

    SavedCityService savedCityService;

    @Autowired
    public SavedCityController(SavedCityService savedCityService) {
        this.savedCityService = savedCityService;
    }

    @PostMapping("/saved-cities")
    @ResponseStatus(HttpStatus.CREATED)
    public SavedCityDto saveCity(@PathVariable Long memberId, @RequestBody SaveDateRequest request) {
        return savedCityService.saveDateForMember(memberId, request.cityId(), request.savedDate());
    }

    @GetMapping("/saved-cities")
    public List<SavedCityDto> getSavedCities(@PathVariable Long memberId) {
        return savedCityService.getSavedCitiesByMemberId(memberId);
    }

    @GetMapping("/saved-cities/{cityId}/dates")
    public List<LocalDate> getDatesForCity(@PathVariable Long memberId, @PathVariable Long cityId) {
        return savedCityService.getSavedDatesForCityByMember(memberId, cityId);
    }

    //todo
    @PutMapping("/favourite-city/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberProfileDto setFavouriteCity(@PathVariable Long memberId, @PathVariable Long cityId) {
        return savedCityService.setFavouriteCity(memberId, cityId);
    }

//    @GetMapping("/favourite-city")
//    public MemberProfileDto getFavouriteCity(@PathVariable Long memberId) {
//        return savedCityService.
//    }

}
