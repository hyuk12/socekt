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

	private List<String> roomList = new ArrayList<>();
	Map<Integer, String> roomIds = new HashMap<>();

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
					case "join" :

						LoginRespDto loginRespDto = gson.fromJson(responseDto.getBody(), LoginRespDto.class);
						if(loginRespDto.getRoomList() == null) {
							ChattingClient.getInstance().getChattingListPanel();
						}
//						 (loginRespDto.getRoomList() != null)
						else {
							
							
								ChattingClient.getInstance().getModel().clear();
								ChattingClient.getInstance().getModel().addAll(roomList);
							
//								ChattingClient.getInstance().getChattingListPanel(); //없애
							
						}
						
						break;
						
					case "message" :
						System.out.println(responseDto.getBody());
						MessageRespDto messageRespDto = gson.fromJson(responseDto.getBody(), MessageRespDto.class);
						String message = messageRespDto.getMessage();
						
						ChattingClient.getInstance().getContentView().append(message + "\n");

						break;
						
					case "createRoom":
						try {
							System.out.println(responseDto.getBody());
							CreateRoomRespDto createRoomRespDto = gson.fromJson(responseDto.getBody(), CreateRoomRespDto.class);

//							roomList.add(ChattingClient.getInstance().getTitle());
							roomList.add(createRoomRespDto.getRoomName());
							System.out.println("방잇냐?" + roomList.get(0));
							for (String s : roomList) {
								System.out.println("방잇냐?" + s);
							}

//							ChattingClient.getInstance().getContentView().setText("");

//							ChattingClient.getInstance().getModel().clear();
							
							ChattingClient.getInstance().getModel().addAll(roomList);
							ChattingClient.getInstance().getRoomTitle().setText("제목: "+ createRoomRespDto.getRoomName() + "의 방입니다.");
//							ChattingClient.getInstance().getContentView().setText("");
							ChattingClient.getInstance().getContentView().append(createRoomRespDto.getRoomName() + "방이 생성되었습니다."+"\n");
							System.out.println(ChattingClient.getInstance().getModel());
						} catch (JsonIOException e) {
							e.printStackTrace();

						}

						break;
					case "joinRoom":
						JoinRoomRespDto joinRoomRespDto = gson.fromJson(responseDto.getBody(), JoinRoomRespDto.class);

						String joinName = joinRoomRespDto.getJoinName();

						CardLayout mainLayout = (CardLayout)ChattingClient.getInstance().getMainPanel().getLayout();
						mainLayout.show(ChattingClient.getInstance().getMainPanel(), "chattingRoom");
						ChattingClient.getInstance().getRoomTitle().setText("제목: "+ roomTitle + "의 방입니다.");
						ChattingClient.getInstance().getContentView().setText("");
						ChattingClient.getInstance().getContentView().append(joinName + "님이 방에 입장하셨습니다.");
						break;
				
				}
				
			}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	}


	
}
