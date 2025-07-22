package com.nemetabe.solarwatch.mapper.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationToStringSerializer extends JsonSerializer<Duration> {
    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formatted = String.format("%02d:%02d:%02d",
                value.toHours(),
                value.toMinutesPart(),
                value.toSecondsPart());
        gen.writeString(formatted);
    }
}
