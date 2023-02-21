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

import org.example.dto.request.CreateRoomReqDto;
import org.example.dto.request.JoinReqDto;
import org.example.dto.request.JoinRoomReqDto;
import org.example.dto.request.MessageReqDto;
import org.example.dto.request.RequestDto;
import org.example.dto.response.*;
import org.example.entity.Room;
import org.example.entity.RoomInfo;
import org.example.util.ServerUtil;

import com.google.gson.Gson;

import lombok.Getter;

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
	private RoomInfo roomInfo;

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

				RequestDto<String> requestDto = gson.fromJson(request, RequestDto.class);
				OutputStream outputStream;
				PrintWriter writer;
				ResponseDto responseDto;

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

					
					responseDto = new ResponseDto(requestDto.getResource(), "ok", userJson);
					
					outputStream = this.getSocket().getOutputStream();
					writer = new PrintWriter(outputStream, true);
							
					writer.println(gson.toJson(responseDto));

					break;

				case "message":
					MessageReqDto messageReqDto = gson.fromJson(requestDto.getBody(), MessageReqDto.class);
					String message = messageReqDto.getMessage();
					
					MessageRespDto messageRespDto = new MessageRespDto(message);
					String messageJson = gson.toJson(messageRespDto);
					
					responseDto = new ResponseDto(requestDto.getResource(), "ok", messageJson);
					
//					serverUtil.sendToRoom(requestDto.getResource(), "ok", createdRoomName, createRoomReqDto.getRoomName());
					
					outputStream = this.getSocket().getOutputStream();
					writer = new PrintWriter(outputStream, true);
							
					writer.println(gson.toJson(responseDto));
					
					break;

				case "createRoom":
					CreateRoomReqDto createRoomReqDto = gson.fromJson(requestDto.getBody(), CreateRoomReqDto.class);
					room = new Room(createRoomReqDto.getKingName(), createRoomReqDto.getRoomName(), users);
					roomInfo = new RoomInfo(createRoomReqDto.getKingName(), createRoomReqDto.getRoomName());
					
					roomList.add(room.getRoomName());

//					room.getUsers().add(this);
					users = room.getUsers();
					
					
					rooms.add(room);

					CreateRoomRespDto createRoomRespDto = new CreateRoomRespDto(room.getRoomName());
					String createdRoomName = gson.toJson(createRoomRespDto);
					
					responseDto = new ResponseDto(requestDto.getResource(), "ok", createdRoomName);



					outputStream = this.getSocket().getOutputStream();
					writer = new PrintWriter(outputStream, true);

					writer.println(gson.toJson(responseDto));


					
//					}
					
					break;

				case "createRoomList":


						CreateRoomListRespDto createRoomListRespDto = new CreateRoomListRespDto(roomList);
						String createdRoomNameList = gson.toJson(createRoomListRespDto);

						responseDto = new ResponseDto(requestDto.getResource(), "ok", createdRoomNameList);

						for (ServerThread s : socketList) {

							outputStream = s.getSocket().getOutputStream();
							writer = new PrintWriter(outputStream, true);

							writer.println(gson.toJson(responseDto));
						}



						break;

				case "joinRoom":
					users.add(this);
					JoinRoomReqDto joinRoomReqDto = gson.fromJson(requestDto.getBody(), JoinRoomReqDto.class);
					System.out.println( "방에 있는 유저 수: "+ room.getUsers().size());
					String roomName = joinRoomReqDto.getRoomName();

					String joinName = joinRoomReqDto.getJoinName();

					System.out.println(roomName + joinName);
					System.out.println(room.getUsers().size());
					System.out.println(this.socket.getPort());
//					room.getUsers().add(this);
//					room = new Room(joinRoomReqDto.getJoinName(), joinRoomReqDto.getRoomName(), users);
					JoinRoomRespDto joinRoomRespDto = new JoinRoomRespDto(joinName);
					String joinRoomJson = gson.toJson(joinRoomRespDto);

					responseDto = new ResponseDto(requestDto.getResource(), "ok", joinRoomJson);
					outputStream  = this.getSocket().getOutputStream();
					writer = new PrintWriter(outputStream, true);

					writer.println(gson.toJson(responseDto));
//					serverUtil.sendToRoom(requestDto.getResource(), "ok", joinRoomJson, roomName);

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
