package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.example.dto.request.LoginReqDto;
import org.example.dto.request.RequestDto;
import org.example.dto.response.LoginRespDto;
import org.example.dto.response.ResponseDto;
import org.example.service.SendService;

import com.google.gson.Gson;

import lombok.Getter;

@Getter
public class ServerThread extends Thread{
	private List<ServerThread> socketList; 
	private Socket socket;
	private InputStream inputStream;
	private Gson gson;
	private SendService sendService;
	
	private String nickname;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		this.gson = new Gson();
		socketList.add(this);
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
					LoginReqDto loginReqDto = gson.fromJson(requestDto.getBody(), LoginReqDto.class);
					nickname = loginReqDto.getNickname();
					
					List<String> userList = new ArrayList<>();
					
					for(ServerThread thread : socketList) {
						userList.add(thread.getNickname());
					}
					
					LoginRespDto loginRespDto = new LoginRespDto(nickname + "님이 접속 하셨습니다.");
					sendService.sendToAll(requestDto.getResource(), "ok", gson.toJson(loginRespDto));
					
					break;

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
