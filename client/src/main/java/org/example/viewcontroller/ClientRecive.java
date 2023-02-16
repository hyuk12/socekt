package org.example.viewcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.example.dto.LoginRespDto;
import org.example.dto.MessageRespDto;
import org.example.dto.ResponseDto;

import com.google.gson.Gson;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ClientRecive extends Thread{

	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;

	
	@Override
	public void run() {
		try {
			
			inputStream = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			gson = new Gson();
			
			while(true) {
				String request = in.readLine();
				ResponseDto responseDto = gson.fromJson(request, ResponseDto.class);
				
				switch(responseDto.getResource()) {
				case "login" :
					LoginRespDto loginRespDto = 
						gson.fromJson(responseDto.getBody(), LoginRespDto.class);
					//Chatti? ngView가 싱글톤, ChattingView에 연결한 뒤 welcomemessage띄워줘야함
					break;
				case "message" :
					MessageRespDto messageRespDto = gson.fromJson(responseDto.getBody(), MessageRespDto.class);
					//ChattingView의 메세지 출력 화면에 append 해줘야함
					break;
				}
				
			}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	}
	
}
