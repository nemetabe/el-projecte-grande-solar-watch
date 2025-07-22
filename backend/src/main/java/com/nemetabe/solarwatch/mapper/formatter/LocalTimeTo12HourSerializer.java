package com.nemetabe.solarwatch.mapper.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeTo12HourSerializer extends JsonSerializer<LocalTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:mm:ss a");

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(FORMATTER));
    }
}
