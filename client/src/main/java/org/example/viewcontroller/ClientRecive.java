package org.example.viewcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.example.dto.CreateRoomRespDto;
import org.example.dto.LoginRespDto;
import org.example.dto.MessageRespDto;
import org.example.dto.ResponseDto;
import org.example.view.ChattingView;

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
						System.out.println("로그인됨");//최종때 지워도됨
						
						break;
						
					case "message" :
						MessageRespDto messageRespDto = gson.fromJson(responseDto.getBody(), MessageRespDto.class);
						ChattingView.getInstance().getContentView().append(messageRespDto.getToUser() + " :"); //채팅창
						ChattingView.getInstance().getContentView().append(messageRespDto.getMessageValue() + "\n"); //채팅창

						
						break;
						
					case "createRoom":
						CreateRoomRespDto createRoomRespDto = gson.fromJson(responseDto.getBody(), CreateRoomRespDto.class );
						LoginRespDto loginRespDto = gson.fromJson(responseDto.getBody(), LoginRespDto.class);
						
						break;
						
					case "":
						break;
				}
				
			}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	}
	
}
