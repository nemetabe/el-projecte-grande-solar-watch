package com.nemetabe.solarwatch.model.entity;


import com.nemetabe.solarwatch.model.entity.SolarData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "cities",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "state"}) // avoid duplicate cities in same country
)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // will be used in the unique constraint
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    @Column(name = "lat", nullable = false)
    private double latitude;

    @Column(name = "lon", nullable = false)
    private double longitude;

    //TODO add localnames

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SolarData> solarDataList = new ArrayList<>();

}