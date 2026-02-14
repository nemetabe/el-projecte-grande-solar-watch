package com.nemetabe.solarwatch.controller;


import com.nemetabe.solarwatch.service.SolarService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/solar")
public class SolarController {

     private final SolarService solarService;
     private static final Logger logger = LoggerFactory.getLogger(SolarController.class);

     @Autowired
     public SolarController(SolarService solarService) {
          this.solarService = solarService;
     }


}
