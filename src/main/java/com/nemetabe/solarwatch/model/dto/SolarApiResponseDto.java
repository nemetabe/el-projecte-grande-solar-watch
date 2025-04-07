package com.nemetabe.solarwatch.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SolarApiResponseDto {
    private SolarResults results;
    private String status;
    private String tzid;

    @Getter
    @Setter
    public static class SolarResults {
        private String sunrise;
        private String sunset;
        private String solar_noon;
        private String day_length;

        private String civil_twilight_begin;
        private String civil_twilight_end;
        private String nautical_twilight_begin;
        private String nautical_twilight_end;
        private String astronomical_twilight_begin;
        private String astronomical_twilight_end;
    }

}
