package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageRespDto {
	private String toUser;
	private String message;
}
