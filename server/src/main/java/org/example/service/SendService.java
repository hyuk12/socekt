package org.example.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.example.ServerThread;
import org.example.dto.response.ResponseDto;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendService {
	private Gson gson;
	private OutputStream outputStream;
	private Socket socket;
	private List<ServerThread> socketList;


	public void sendToAll(String resource, String status, String body) throws IOException {
		ResponseDto responseDto = new ResponseDto(resource, status, body);
		
		for (ServerThread thread: socketList) {
			outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream, true);
			
			writer.println(gson.toJson(responseDto));
		}
	}
		
}
