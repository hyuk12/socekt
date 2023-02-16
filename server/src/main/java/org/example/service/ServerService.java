package org.example.service;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.example.ServerThread;
import org.example.dto.request.LoginReqDto;
import org.example.dto.request.RequestDto;
import org.example.dto.response.LoginRespDto;
import org.example.dto.response.ResponseDto;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerService {
	private Gson gson;
	private OutputStream outputStream;
	private Socket socket;

	private LoginReqDto loginReqDto;
	private LoginRespDto loginRespDto;
	private String nickname;
	private List<ServerService> socketList;
	
	private List<String> userList;
	
	public ServerService (Socket socket) {
		
		this.socket = socket;
		this.socketList.add(this);
	}
	
	public LoginRespDto loginUser(RequestDto requestDto) {
		
		
		loginReqDto = gson.fromJson(requestDto.getBody(), LoginReqDto.class); 
		nickname = loginReqDto.getNickname();
		
		userList = new ArrayList<>();
		
		for(ServerService thread : socketList) {
			userList.add(thread.getNickname());
		}
		
		return new LoginRespDto(nickname + "님이 접속하셨습니다.");
	}
	
	public void sendToAll(String resource, String status, String body) throws IOException {
		
		ResponseDto responseDto = new ResponseDto(resource, status, body);
		
		for (ServerService thread: socketList) {
			outputStream = thread.getSocket().getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream, true);
			
			writer.println(gson.toJson(responseDto));
		}
	}
	
	public void sendToUser(String resource, String status, String body, String toUser) {
		
	}
	
	
	
}
