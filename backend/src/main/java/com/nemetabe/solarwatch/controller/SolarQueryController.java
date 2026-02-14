package com.nemetabe.solarwatch.controller;


import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import com.nemetabe.solarwatch.service.SolarQueryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/solar")
public class SolarQueryController {

     private final SolarQueryService solarQueryService;
     private static final Logger logger = LoggerFactory.getLogger(SolarQueryController.class);

     @Autowired
     public SolarQueryController(SolarQueryService solarService) {
          this.solarQueryService = solarService;
     }

     @GetMapping
     public Mono<SolarResponseDto> getByCityName(
             @RequestParam(required = false, defaultValue = "Budapest") String city,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
          return solarQueryService.getSolarTimesDto(city, date);
     }

     @GetMapping("/city/{cityId}")
     public Mono<SolarResponseDto> getByCityId(
             @PathVariable Long cityId,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
          return solarQueryService.getSolarTimesDto(cityId, date);
     }

     @GetMapping("/coords")
     public Mono<SolarResponseDto> getByCoords(
             @RequestParam double lat,
             @RequestParam double lon,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
          return solarQueryService.getSolarTimesDto(lat, lon, date);
     }

     @GetMapping("/id/{solarTimesId}")
     public Mono<SolarResponseDto> getById(@PathVariable Long solarTimesId) {
          return solarQueryService.getSolarTimesDtoById(solarTimesId);
     }

}
