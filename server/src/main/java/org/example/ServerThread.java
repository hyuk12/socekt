package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.dto.request.*;
import org.example.dto.response.CreateRoomRespDto;
import org.example.dto.response.JoinRoomRespDto;
import org.example.dto.response.LoginRespDto;

import org.example.dto.response.MessageRespDto;


import com.google.gson.Gson;

import lombok.Getter;
import org.example.entity.Room;
import org.example.util.ServerUtil;

@Getter
public class ServerThread extends Thread{
	@Getter
	private static List<ServerThread> socketList = new ArrayList<ServerThread>();
	@Getter
	private Socket socket;
	private InputStream inputStream;
	private Gson gson;


	private ServerUtil serverUtil;

	private static List<Room> rooms = new ArrayList<>();
	private Room room;
	private String nickname;
	private List<String> roomList;

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
					roomList = new ArrayList<String>();
					for (Room room : rooms) {
						String roomName = room.getRoomName();
						roomList.add(roomName);
					}

					LoginReqDto loginReqDto = gson.fromJson(requestDto.getBody(), LoginReqDto.class);
					nickname = loginReqDto.getNickname();
					LoginRespDto loginRespDto = new LoginRespDto(loginReqDto.getNickname() + "어서오세요!", roomList);
					String loginJson = gson.toJson(loginRespDto);
					serverUtil.sendToAll("login", "ok", loginJson);

					break;

				case "message":
					MessageReqDto messageReqDto = gson.fromJson(requestDto.getBody(), MessageReqDto.class);


						String message = messageReqDto.getToUser() + " > " + messageReqDto.getMessage();
						MessageRespDto messageRespDto = new MessageRespDto(message);
						serverUtil.sendToAll(requestDto.getResource(), "ok", gson.toJson(messageRespDto));

//						String message = messageReqDto.getFromUser() + "["+ messageReqDto.getToUser() + " : ]" + messageReqDto.getMessage();
//						MessageRespDto messageRespDto = new MessageRespDto(message);
//						serverUtil.sendToUser(requestDto.getResource(), "ok", gson.toJson(messageRespDto), messageReqDto.getToUser());
//					}
					break;

					case "createRoom":

						CreateRoomReqDto createRoomReqDto = gson.fromJson(requestDto.getBody(), CreateRoomReqDto.class);

						String kingName = createRoomReqDto.getKingName();
						String roomTitle = createRoomReqDto.getTitle();
						int socketNumber = socket.getPort();
						room = new Room(kingName, roomTitle, socketNumber);
						rooms.add(room);


						roomList.add(room.getRoomName());

						CreateRoomRespDto createRoomRespDto = new CreateRoomRespDto(roomList);
						System.out.println(gson.toJson(createRoomRespDto));
						serverUtil.createRoom(requestDto.getResource(), "ok", gson.toJson(createRoomRespDto));


						break;

					case "joinRoom":
						JoinRoomReqDto joinRoomReqDto = gson.fromJson(requestDto.getBody(), JoinRoomReqDto.class);

						System.out.println("join한 닉네임" + nickname);
						System.out.println("소켓 번호: " + socket.getPort());

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
