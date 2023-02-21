package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;

import lombok.Getter;
import org.example.dto.request.CreateRoomReqDto;
import org.example.dto.request.JoinReqDto;
import org.example.dto.request.JoinRoomReqDto;
import org.example.dto.request.RequestDto;
import org.example.dto.response.CreateRoomRespDto;
import org.example.dto.response.JoinRespDto;
import org.example.dto.response.JoinRoomRespDto;
import org.example.dto.response.ResponseDto;
import org.example.entity.Room;
import org.example.util.ServerUtil;

@Getter
public class ServerThread extends Thread{
	@Getter
	private static List<ServerThread> socketList = new ArrayList<ServerThread>();
	@Getter
	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;

	private String username;

	private ServerUtil serverUtil;


	private static List<Room> rooms = new ArrayList<>();
	private List<String> roomList = new ArrayList<String>();
	private List<ServerThread> users = new ArrayList<>();
	private Room room;


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

				RequestDto<String> requestDto = gson.fromJson(request, RequestDto.class);

				switch (requestDto.getResource()) {
				case "join":
					JoinReqDto joinReqDto = gson.fromJson(requestDto.getBody(), JoinReqDto.class);
					username = joinReqDto.getNickname();

					for (Room room : rooms) {
						roomList.add(room.getRoomName());
					}
					System.out.println(roomList.size());

					JoinRespDto joinRespDto = new JoinRespDto(username, roomList);
					String userJson = gson.toJson(joinRespDto);

					serverUtil.sendToAll(requestDto.getResource(), "ok", userJson);

					break;

				case "message":


					break;

				case "createRoom":
					CreateRoomReqDto createRoomReqDto = gson.fromJson(requestDto.getBody(), CreateRoomReqDto.class);
					room = new Room(createRoomReqDto.getKingName(), createRoomReqDto.getRoomName(), users);
					roomList.add(room.getRoomName());

					room.getUsers().add(this);
					System.out.println(this.socket.getPort());
					users = room.getUsers();
					System.out.println("최초 방을 만들 때 유저:" + users.size());

					rooms.add(room);

					CreateRoomRespDto createRoomRespDto = new CreateRoomRespDto(room.getRoomName());
					String createdRoomName = gson.toJson(createRoomRespDto);


					serverUtil.sendToRoom(requestDto.getResource(), "ok", createdRoomName, room.getRoomName());


					break;

				case "joinRoom":
					JoinRoomReqDto joinRoomReqDto = gson.fromJson(requestDto.getBody(), JoinRoomReqDto.class);

					System.out.println( "방에 있는 유저 수: "+ room.getUsers().size());
					String roomName = joinRoomReqDto.getRoomName();

					String joinName = joinRoomReqDto.getJoinName();

					System.out.println(roomName + joinName);
					System.out.println(room.getUsers().size());
					System.out.println(this.socket.getPort());
					room.getUsers().add(this);
					JoinRoomRespDto joinRoomRespDto = new JoinRoomRespDto(joinName);
					String joinRoomJson = gson.toJson(joinRoomRespDto);

					serverUtil.sendToRoom(requestDto.getResource(), "ok", joinRoomJson, roomName);

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
