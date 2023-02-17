package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateRoomRespDto {
    private String title;
    private String createMessage;

}
