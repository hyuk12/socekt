package org.example;

import java.net.Socket;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {

	private String nickname;
	private Socket socket;
	
}
