package com.nemetabe.solarwatch.model.exception.city;

public class SavedCityNotFoundException extends IllegalArgumentException {
    public SavedCityNotFoundException() {
        super("City not found in member's saved list.");
    }
}
