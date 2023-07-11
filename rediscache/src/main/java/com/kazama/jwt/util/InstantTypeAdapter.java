package com.kazama.jwt.util;

import java.io.IOException;
import java.time.Instant;

import org.springframework.stereotype.Component;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@Component
public class InstantTypeAdapter extends TypeAdapter<Instant> {

    @Override
    public void write(JsonWriter out, Instant value) throws IOException {
        // TODO Auto-generated method stub
        out.value(value.toString());
    }

    @Override
    public Instant read(JsonReader in) throws IOException {
        // TODO Auto-generated method stub
        return Instant.parse(in.nextString());
    }

}
