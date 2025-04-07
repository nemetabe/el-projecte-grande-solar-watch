package com.nemetabe.solarwatch.model.dto;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityDto {
    private String name;
    private String country;
    private String state;
    private double latitude;
    private double longitude;
    //private Map<String, String> localNames;
}
