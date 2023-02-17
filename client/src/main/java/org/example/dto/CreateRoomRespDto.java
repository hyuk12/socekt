package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateRoomRespDto {
	private String title;
	private String createMessage;
}
