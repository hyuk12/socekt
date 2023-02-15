package org.example.controller;

import java.io.IOException;
import java.net.Socket;

import org.example.dto.request.RequestDto;
import org.example.dto.response.LoginRespDto;
import org.example.service.ServerService;


public class ServerController {
	
	private ServerService serverService;
	
	
	public ServerController(Socket socket) {
		this.serverService = new ServerService(socket);
	}
	
	public LoginRespDto loginUser(RequestDto requestDto) {
		return serverService.loginUser(requestDto);
	}
	
	public void sendToAll(String resource, String status, String body) throws IOException {
		serverService.sendToAll(resource, status, body);
	}

}
