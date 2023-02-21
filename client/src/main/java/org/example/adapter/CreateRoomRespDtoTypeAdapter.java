package org.example.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.dto.response.CreateRoomRespDto;

import java.io.IOException;

public class CreateRoomRespDtoTypeAdapter extends TypeAdapter<CreateRoomRespDto> {
    @Override
    public void write(JsonWriter out, CreateRoomRespDto value) throws IOException {

    }

    @Override
    public CreateRoomRespDto read(JsonReader in) throws IOException {
        return null;
    }
}
