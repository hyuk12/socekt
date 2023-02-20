package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.Room;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class CreateRoomRespDto {
    private List<String> roomList;

}
