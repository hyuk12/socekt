package org.example.viewcontroller;

import java.awt.*;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.JsonIOException;
import org.example.dto.response.*;
import org.example.entity.Room;
import org.example.entity.RoomInfo;
import org.example.view.ChattingClient;


import com.google.gson.Gson;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRecive extends Thread{

	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;

	private CardLayout mainLayout;
	
	private String roomTitle;
	private String kingNick;

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
					case "message" :
						System.out.println(responseDto.getBody());
						MessageRespDto messageRespDto = gson.fromJson(responseDto.getBody(), MessageRespDto.class);
						String message = messageRespDto.getMessage();
						
						ChattingClient.getInstance().getContentView().append(message + "\n");

						break;
						
					case "createRoom":
						try {

							CreateRoomRespDto createRoomRespDto = gson.fromJson(responseDto.getBody(), CreateRoomRespDto.class);

							ChattingClient.getInstance().getRoomTitle().setText("제목: "+ createRoomRespDto.getRoomName()+ "의 방입니다.");
							ChattingClient.getInstance().getContentView().append(createRoomRespDto.getRoomName() + "방이 생성되었습니다."+"\n");
							ChattingClient.getInstance().getContentView().setText("");

						} catch (JsonIOException e) {
							e.printStackTrace();

						}

						break;
					case "joinRoom":
						JoinRoomRespDto joinRoomRespDto = gson.fromJson(responseDto.getBody(), JoinRoomRespDto.class);

						String joinName = joinRoomRespDto.getJoinName();
						String roomName = joinRoomRespDto.getRoomName();
			

						mainLayout = (CardLayout)ChattingClient.getInstance().getMainPanel().getLayout();
						mainLayout.show(ChattingClient.getInstance().getMainPanel(), "chattingRoom");
						ChattingClient.getInstance().getRoomTitle().setText("제목: "+ roomName + "의 방입니다.");
						ChattingClient.getInstance().getContentView().setText("");
						ChattingClient.getInstance().getContentView().append(joinName + "님이 방에 입장하셨습니다."+"\n");
						break;
						
					case "exitRoom":
						mainLayout = (CardLayout)ChattingClient.getInstance().getMainPanel().getLayout();
						mainLayout.show(ChattingClient.getInstance().getMainPanel(), "chattingList");
						
						break;
					case "reflashRoom":
						List<String> roomNames = gson.fromJson(responseDto.getBody(), List.class);
						
						ChattingClient.getInstance().getModel().clear();
						ChattingClient.getInstance().getModel().addAll(roomNames);
						
						break;
				
				}
				
			}
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	}


	
}
