//package com.nemetabe.solarwatch.controller;
//
//import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
//import com.nemetabe.solarwatch.service.SolarService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDate;
//
//@RestController
//@RequestMapping("/aapi/solar")
//public class SolarApiController {
//
//    private final SolarService solarService;
//    private static final Logger logger = LoggerFactory.getLogger(SolarApiController.class);
//
//
//    @Autowired
//    public SolarApiController(SolarService solarService) {
//        this.solarService = solarService;
//    }
//
//    @GetMapping
//    public Mono<ResponseEntity<SolarResponseDto>> getSolarData(
//            @RequestParam(required = false, defaultValue = "Budapest")
//                String city,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//                LocalDate date
//    ) {
//        logger.info("Received request processing started in the controller method. City: {}, date: {}", city, date);
//        LocalDate queryDate = date != null? date : LocalDate.now();
//        Mono<SolarResponseDto> response = solarService.getSolarInfo(city, queryDate);
//        logger.info("Received request processing completed in the controller method. Response: {}", response.toString());
//        return response
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build())
//                .onErrorResume(e -> {
//                    logger.error("Error processing request for city: {} on date: {}", city, queryDate, e);
//                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//                });
//    }
//}
//
