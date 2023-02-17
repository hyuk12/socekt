package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.example.dto.request.CreateRoomReqDto;
import org.example.dto.request.MessageReqDto;
import org.example.dto.request.RequestDto;
import org.example.dto.response.CreateRoomRespDto;
import org.example.dto.response.JoinRoomRespDto;
import org.example.dto.response.LoginRespDto;

import org.example.dto.response.MessageRespDto;


import com.google.gson.Gson;

import lombok.Getter;
import org.example.util.ServerUtil;

@Getter
public class ServerThread extends Thread{
	@Getter
	private static List<ServerThread> socketList = new ArrayList<ServerThread>();
	private Socket socket;
	private InputStream inputStream;
	private Gson gson;
	

	private ServerUtil serverUtil;

	
	private String nickname;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		this.gson = new Gson();
		socketList.add(this);

		this.serverUtil = new ServerUtil(gson);

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
					LoginRespDto loginRespDto = serverUtil.loginUser(requestDto);
					serverUtil.sendToAll(requestDto.getResource(), "ok", gson.toJson(loginRespDto));
					break;

				case "message":
					MessageReqDto messageReqDto = gson.fromJson(request, MessageReqDto.class);

					if(requestDto.getResource().equalsIgnoreCase("all")) {
						String message = messageReqDto.getToUser() + " > " + messageReqDto.getMessage();
						MessageRespDto messageRespDto = new MessageRespDto(message);
						serverUtil.sendToAll(requestDto.getResource(), "ok", gson.toJson(messageRespDto));
					} else {
						String message = messageReqDto.getFromUser() + "["+ messageReqDto.getToUser() + " : ]" + messageReqDto.getMessage();
						MessageRespDto messageRespDto = new MessageRespDto(message);
						serverUtil.sendToUser(requestDto.getResource(), "ok", gson.toJson(messageRespDto), messageReqDto.getToUser());
					}
					break;

					case "createRoom":
						CreateRoomReqDto createRoomReqDto = gson.fromJson(request, CreateRoomReqDto.class);

						String createRoomName = createRoomReqDto.getTitle();
						String createMessage = createRoomName + "방이 개설되었습니다.";
						CreateRoomRespDto createRoomRespDto = new CreateRoomRespDto(createRoomName, createMessage);

						serverUtil.createRoom(requestDto.getResource(), "ok", gson.toJson(createRoomRespDto));
						break;

					case "joinRoom":
						JoinRoomRespDto joinRoomRespDto = new JoinRoomRespDto(nickname);

						break;

					case "deleteRoom":


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
