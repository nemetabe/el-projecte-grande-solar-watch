package com.nemetabe.solarwatch.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolarDateId {
    private LocalDate solarDate;
    private Long solarId;

    public SolarDateId(SolarTimes solarTimes) {
        this.solarId = solarTimes.getId();
        this.solarDate = solarTimes.getDate();
    }
}
