package org.example.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.example.dto.request.ExitReqDto;
import org.example.dto.request.MessageReqDto;
import org.example.dto.request.RequestDto;
import org.example.view.ChattingClient;

import com.google.gson.Gson;

public class ChattingRoom extends JPanel{
	private ChattingClient ccm = ChattingClient.getInstance();
	private JoinPanel jp = JoinPanel.getJoinPanelInstance();
	private JPanel chattingRoomPanel;
	private JLabel roomTitle;
	private JTextField messageInput;
	private JTextArea contentView;
	
	private String roomName;
	private String nickname;
	
	private Gson gson;
	
	public ChattingRoom() {
		gson = new Gson();
		chattingRoomPanel = new JPanel();
		
		ccm.getMainPanel().add(chattingRoomPanel, "chattingRoom");

		chattingRoomPanel.setLayout(null);
		chattingRoomPanel.setBackground(new Color(255, 217, 0));

		roomTitle = new JLabel();
		roomTitle.setFont(new Font("Verdana", Font.PLAIN, 16));
		roomTitle.setHorizontalAlignment(SwingConstants.CENTER);
		roomTitle.setBounds(6, 6, 372, 52);
		chattingRoomPanel.add(roomTitle);


		
		JButton exitRoomButton = new JButton("");
		exitRoomButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ExitReqDto exitReqDto = new ExitReqDto(nickname, roomName);
				
				String exitJson = gson.toJson(exitReqDto);
				System.out.println(exitJson);
				sendRequest("exitRoom", exitJson);
				
				CardLayout mainLayout = (CardLayout)ccm.getMainPanel().getLayout();
				mainLayout.show(ccm, "chattingList");
			}
		});
		
		
		exitRoomButton.setBounds(373, 0, 97, 70);
		chattingRoomPanel.add(exitRoomButton);

		ImageIcon exitButton = new ImageIcon(ChattingClient.class.getResource("../image/나가기.png"));
		exitRoomButton.setIcon(exitButton);
		exitRoomButton.setBorderPainted(false);
		exitRoomButton.setFocusPainted(false);
		exitRoomButton.setContentAreaFilled(false);


		JButton messageSendButton = new JButton("");
		messageSendButton.setBounds(398, 707, 66, 42);
		chattingRoomPanel.add(messageSendButton);

		ImageIcon sendButton = new ImageIcon(ChattingClient.class.getResource("../image/send-3.png"));
		messageSendButton.setIcon(sendButton);
		messageSendButton.setBorderPainted(false);
		messageSendButton.setFocusPainted(false);
		messageSendButton.setContentAreaFilled(false);

		
		
		messageInput = new JTextField();
		messageInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					sendMessage();
					messageInput.setText("");

				}
			}
		});
		messageInput.setBounds(6, 699, 399, 57);
		chattingRoomPanel.add(messageInput);
		messageInput.setColumns(10);
		
		contentView = new JTextArea();
		contentView.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 70, 457, 624);
		scrollPane_1.setColumnHeaderView(contentView);
		scrollPane_1.setViewportView(contentView);
		chattingRoomPanel.add(scrollPane_1);
	}	
	
	private void sendMessage() {
		if(!messageInput.getText().isBlank()) {

			/*from : room 객체에있는 유저들*/
			MessageReqDto messageReqDto = new MessageReqDto(messageInput.getText());

			sendRequest("message", gson.toJson(messageReqDto));


		}
	}

	private void sendRequest(String resource, String body){
		OutputStream outputStream;
		try {
			outputStream = jp.getSocket().getOutputStream();
			PrintWriter out = new PrintWriter(outputStream, true);

			RequestDto requestDto = new RequestDto(resource, body);


			out.println(gson.toJson(requestDto));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
