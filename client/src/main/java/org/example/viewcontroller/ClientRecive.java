package org.example.viewcontroller;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import org.example.dto.*;
import org.example.entity.Room;
import org.example.view.ChattingClient;


import com.google.gson.Gson;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRecive extends Thread{

	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;

	private List<String> roomList = new ArrayList<String>();
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

						LoginRespDto loginRespDto = gson.fromJson(responseDto.getBody(), LoginRespDto.class);

						if (loginRespDto.getRoomList() != null) {

							ChattingClient.getInstance().getModel().clear();
							ChattingClient.getInstance().getModel().addAll(loginRespDto.getRoomList());
						}

						break;
						
					case "message" :
						MessageRespDto messageRespDto = gson.fromJson(responseDto.getBody(), MessageRespDto.class);
						String message = messageRespDto.getMessage();
						ChattingClient.getInstance().getContentView().append(message + "\n");

						break;
						
					case "createRoom":

						try {

							CreateRoomRespDto createRoomRespDto = gson.fromJson(responseDto.getBody(), CreateRoomRespDto.class);

							roomList.addAll(createRoomRespDto.getRoomList());
							ChattingClient.getInstance().getContentView().setText("");

							ChattingClient.getInstance().getModel().clear();
							ChattingClient.getInstance().getModel().addAll(roomList);


						} catch (JsonIOException e) {
							e.printStackTrace();

						}
//						for (String title : rooms.getTitle()) {
//							ChattingClient.getInstance().
//						}

						break;
					case "joinRoom":

						break;
				
				}
				
			}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	}


	
}
