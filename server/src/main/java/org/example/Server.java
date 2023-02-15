package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	//Main이 도는 순간 돌아가기 위해 server 를 싱글톤으로 제작한다.
	
	private static Server instance;
	public static Server getInstance() {
		if(instance == null) {
			instance = new Server();
		}
		return instance;
	}
	
	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private Socket socket;
	private String nickname;
	
	private Server() {}
	
	public void start() {
		try {
			serverSocket = new ServerSocket(8888);
			System.out.println(" 서버 시작 ");
			serverThread = new ServerThread(socket);
			serverThread.start();
			
			while(true) {
				socket = serverSocket.accept();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(" 서버 종료 ");
		}
		
	}
	
	

}
