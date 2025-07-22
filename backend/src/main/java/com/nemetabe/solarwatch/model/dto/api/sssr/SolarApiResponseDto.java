package com.nemetabe.solarwatch.model.dto.api.sssr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolarApiResponseDto {

    private SolarResults results;
    private String status;
    private String tzid;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SolarResults {

        private String sunrise;
        private String sunset;

        @JsonProperty("solar_noon")
        private String solarNoon;

        @JsonProperty("day_length")
        private String dayLength;

        @JsonProperty("civil_twilight_begin")
        private String civilTwilightBegin;

        @JsonProperty("civil_twilight_end")
        private String civilTwilightEnd;

        @JsonProperty("nautical_twilight_begin")
        private String nauticalTwilightBegin;

        @JsonProperty("nautical_twilight_end")
        private String nauticalTwilightEnd;

        @JsonProperty("astronomical_twilight_begin")
        private String astronomicalTwilightBegin;

        @JsonProperty("astronomical_twilight_end")
        private String astronomicalTwilightEnd;
    }
}