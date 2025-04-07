package com.nemetabe.solarwatch.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityNameDto {
    private String name;
    private String country;
    private String state;
}
