package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.example.dto.request.RequestDto;
import org.example.dto.response.LoginRespDto;
import org.example.util.ServerUtil;

import com.google.gson.Gson;

import lombok.Getter;

@Getter
public class ServerThread extends Thread{
	@Getter
	private static List<ServerThread> socketList = new ArrayList<ServerThread>();
	private Socket socket;
	private InputStream inputStream;
	private Gson gson;
	

	private ServerUtil serverService;

	
	private String nickname;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		this.gson = new Gson();
		socketList.add(this);
		this.serverService = new ServerUtil(gson);

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
					LoginRespDto loginRespDto = serverService.loginUser(requestDto);
					serverService.sendToAll(requestDto.getResource(), "ok", gson.toJson(loginRespDto));
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
