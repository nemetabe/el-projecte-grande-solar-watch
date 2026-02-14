package com.nemetabe.solarwatch.config;

import com.nemetabe.solarwatch.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CityBootstrapper {
    private CityRepository cityRepository;

    @Autowired
    public CityBootstrapper(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initBaseCities() {
        if (cityRepository.count() == 0) {
            return;
        }
    }

}
