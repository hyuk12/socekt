package org.example.utill;


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
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServerUtill {
	private final Gson gson;
	
	public LoginRespDto loginUser(RequestDto requestDto) {
		
		LoginReqDto loginReqDto = gson.fromJson(requestDto.getBody(), LoginReqDto.class); 

		return new LoginRespDto(loginReqDto.getNickname() + "님이 접속하셨습니다.");
	}
	
	public void sendToAll(String resource, String status, String body) throws IOException {
		
		ResponseDto responseDto = new ResponseDto(resource, status, body);
		
		for (ServerThread thread: ServerThread.getSocketList()) {
			OutputStream outputStream = thread.getSocket().getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream, true);
			
			writer.println(gson.toJson(responseDto));
		}
	}
	
	public void sendToUser(String resource, String status, String body, String toUser) {
		
	}
	
	
	
}
