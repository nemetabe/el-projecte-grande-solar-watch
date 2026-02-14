package com.nemetabe.solarwatch.model.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity @Builder
@Table(name = "saved_cities", uniqueConstraints = @UniqueConstraint(columnNames = {"city_id", "member_id"}))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ElementCollection
    @CollectionTable(
            name = "solar_date_id",
            joinColumns = @JoinColumn(name = "saved_city_id")
    )
    private Set<SolarDateId> solarDateIds = new HashSet<>();

    public void addSolarDateId(SolarDateId solarDateId) {
        solarDateIds.add(solarDateId);
    }

    public void removeSolarDateId(SolarDateId solarDateId) {
        solarDateIds.remove(solarDateId);
    }
}
