package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.List;

import org.example.controller.ServerController;

import org.example.dto.request.RequestDto;
import org.example.dto.response.LoginRespDto;



import com.google.gson.Gson;

import lombok.Getter;

@Getter
public class ServerThread extends Thread{
	private List<ServerThread> socketList; 
	private Socket socket;
	private InputStream inputStream;
	private Gson gson;
	
	private ServerController serverController;
	
	private String nickname;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		this.gson = new Gson();
		this.serverController = new ServerController(socket);
	}

	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			while(true) {
				
				String request = reader.readLine();
				RequestDto requestDto = gson.fromJson(request, RequestDto.class);
				
				switch (requestDto.getResource()) {
				case "login":
					LoginRespDto loginRespDto = serverController.loginUser(requestDto);
					serverController.sendToAll(requestDto.getResource(), "ok", gson.toJson(loginRespDto));
					break;

				case "message":
				default:
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
