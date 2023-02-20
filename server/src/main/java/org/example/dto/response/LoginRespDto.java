package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class LoginRespDto {
	private String welcomeMessage;
	private List<String> roomList;
}
