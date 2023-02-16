package org.example.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRespDto {

	private String welcomeMessage;
	private List<String> connectedUsers;
	
}
