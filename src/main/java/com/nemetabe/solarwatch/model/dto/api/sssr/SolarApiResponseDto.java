package com.nemetabe.solarwatch.model.dto.api.sssr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
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
        private String solar_noon;

        @JsonProperty("day_length")
        private String day_length;

        @JsonProperty("civil_twilight_begin")
        private String civil_twilight_begin;

        @JsonProperty("civil_twilight_end")
        private String civil_twilight_end;

        @JsonProperty("nautical_twilight_begin")
        private String nautical_twilight_begin;

        @JsonProperty("nautical_twilight_end")
        private String nautical_twilight_end;

        @JsonProperty("astronomical_twilight_begin")
        private String astronomical_twilight_begin;

        @JsonProperty("astronomical_twilight_end")
        private String astronomical_twilight_end;
    }

}
