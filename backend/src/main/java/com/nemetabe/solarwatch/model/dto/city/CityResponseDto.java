package com.nemetabe.solarwatch.model.dto.city;

import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityResponseDto {
    private Long id;
    private String name;
    private String country;
    private String state;
    private double latitude;
    private double longitude;
    private List<SolarResponseDto> solarTimes;

}
