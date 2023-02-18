package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class RequestDto {
	private String resource;
	private String body;
}
