package com.nemetabe.solarwatch.model.entity;

import java.util.Arrays;
import java.util.Map;

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
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country"})
)//TODO state <-> country
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column()
    private String state;

    @Column(name = "lat", nullable = false)
    private Double latitude;

    @Column(name = "lon", nullable = false)
    private Double longitude;

//    @ElementCollection
//    @MapKeyColumn(name = "language_code")  // This is the key column in the database
//    @Column(name = "local_name")  // This is the value column in the database
//    @CollectionTable(name = "city_local_names", joinColumns = @JoinColumn(name = "city_id"))
//    private Map<String, String> localNames;

    @Column(name = "solar_times")
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SolarTimes> solarTimes = new ArrayList<>();

}
