package org.example.view;

import com.google.gson.Gson;

import org.example.dto.LoginReqDto;
import org.example.entity.Room;
import org.example.viewcontroller.ClientRecive;

import java.awt.EventQueue;


import javax.swing.*;
import javax.swing.border.EmptyBorder;


import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChattingClient extends JFrame {
	private static final long serialVersionUID = 1L;

	private static ChattingClient instance;
	
	public static ChattingClient getInstance() {
		if(instance == null) {
			instance = new ChattingClient();
		}
		return instance;
	}

	private Socket socket;
	private Gson gson;
	private List<Room> rooms;
	
	private JPanel mainPanel;

	private JLabel nicknameLabel;
	private JPanel joinPanel ;
	private JButton joinButton;
	private JTextField joinNickname;
	private ImageIcon kakaoImage;

	private String roomName;
	private List<JPanel> chattingRoomPanels;
	private JPanel chattingRoomPanel;
	private JTextField messageInput;
	private JList<String> roomList;
	private JScrollPane scrollpane;
	private DefaultListModel<String> model;

	private JPanel chattingListPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChattingClient frame = ChattingClient.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private ChattingClient() {
		gson = new Gson();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
		


		
		joinPanel = new JPanel();
		joinPanel.setBackground(new Color(255, 217, 0));
		mainPanel.add(joinPanel, "name_49023625943041");
		joinPanel.setLayout(null);
		
		kakaoImage = new ImageIcon(ChattingClient.class.getResource("../image/카카오메인이미지.png"));
		joinPanel.setLayout(null);
		JLabel background = new JLabel(kakaoImage);
		background.setLocation(62, 90);
		background.setSize(328, 312);
		joinPanel.add(background);
		

		nicknameLabel = new JLabel();
		joinNickname = new JTextField();
		
		joinNickname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String ip = "127.0.0.1";
					int port = 8888;

					try {
						socket = new Socket(ip, port);
						ClientRecive clientRecive = new ClientRecive(socket);
						clientRecive.start();

						LoginReqDto loginReqDto = new LoginReqDto("login", joinNickname.getText());
						System.out.println(joinNickname.getText());
						System.out.println(loginReqDto);

						String joinJson = gson.toJson(loginReqDto);
						OutputStream outputStream = socket.getOutputStream();
						PrintWriter out = new PrintWriter(outputStream, true);

						out.println(joinJson);

						JOptionPane.showMessageDialog(null, loginReqDto.getNickname() + "님 환영합니다.", "환영메세지", JOptionPane.INFORMATION_MESSAGE);

					} catch (ConnectException ex) {
						JOptionPane.showMessageDialog(null, "접속오류", "접속오류", JOptionPane.ERROR_MESSAGE);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		joinNickname.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (socket != null) {
					CardLayout mainLayout = (CardLayout)mainPanel.getLayout();
					mainLayout.show(mainPanel, "chattingList");
				}
			}
		});

		joinButton = new JButton("");
		joinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ip = "127.0.0.1";
				int port = 8888;

				try {
					socket = new Socket(ip, port);
					ClientRecive clientRecive = new ClientRecive(socket);
					clientRecive.start();

					LoginReqDto loginReqDto = new LoginReqDto("login", joinNickname.getText());
					System.out.println(joinNickname.getText());
					System.out.println(loginReqDto);

					String joinJson = gson.toJson(loginReqDto);
					OutputStream outputStream = socket.getOutputStream();
					PrintWriter out = new PrintWriter(outputStream, true);

					out.println(joinJson);

					JOptionPane.showMessageDialog(null, loginReqDto.getNickname()+ "님 환영합니다.", "환영메세지", JOptionPane.INFORMATION_MESSAGE);

				} catch (ConnectException ex) {
					JOptionPane.showMessageDialog(null, "접속오류", "접속오류", JOptionPane.ERROR_MESSAGE);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (socket != null) {
					CardLayout mainLayout = (CardLayout)mainPanel.getLayout();
					mainLayout.show(mainPanel, "chattingList");
				}
			}
		});
		joinButton.setBounds(62, 559, 328, 49);
		joinPanel.add(joinButton);

		ImageIcon joinButtonImg = new ImageIcon(ChattingClient.class.getResource("../image/로그인.png"));
		joinButton.setIcon(joinButtonImg);
		joinButton.setBorderPainted(false);
		joinButton.setFocusPainted(false);
		joinButton.setContentAreaFilled(false);

		joinNickname.setBounds(62, 473, 328, 49);
		joinPanel.add(joinNickname);
		joinNickname.setColumns(10);

		chattingListPanel = new JPanel();
		mainPanel.add(chattingListPanel, "chattingList");
		chattingListPanel.setLayout(null);
		chattingListPanel.setBackground(new Color(255, 217, 0));

		ImageIcon plusButton = new ImageIcon(ChattingClient.class.getResource("../image/플러스버튼이미지.png"));
		JButton roomPlusButton = new JButton("");
		
		rooms = new ArrayList<>();
		roomPlusButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomId = UUID.randomUUID().toString();
				
				String title = JOptionPane.showInputDialog(null, "방제목을 입력해주세요.");
				rooms.add(new Room(title, roomId));
				updateRoomList();
				

			}
		});
		
		chattingRoomPanels = new ArrayList<>();
		chattingRoomPanel = new JPanel();
		roomPlusButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chattingRoomPanels.add(chattingListPanel);
				
				
			}
		});
		
		
		
		roomPlusButton.setBounds(0, 102, 101, 98);
		chattingListPanel.add(roomPlusButton);
		roomPlusButton.setIcon(plusButton);
		roomPlusButton.setBorderPainted(false);
		roomPlusButton.setFocusPainted(false);
		roomPlusButton.setContentAreaFilled(false);

		JLabel kakaoImg = new JLabel(kakaoImage);
		kakaoImg.setBounds(0, 17, 101, 81);
		chattingListPanel.add(kakaoImg);

		
		roomList = new JList<>();
		
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int index = roomList.locationToIndex(e.getPoint());
					roomName = roomList.getModel().getElementAt(index);
					int result = JOptionPane.showConfirmDialog(null, 
							"방에 입장 하시겠습니까?", 
							"Enter Room", 
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {						
						CardLayout mainLayout = (CardLayout)mainPanel.getLayout();
						mainLayout.show(mainPanel, "roomName");
					} 
					
					
				}
			}
		});
		
		scrollpane = new JScrollPane(roomList);

		roomList.setBounds(0, 212, 324, -81);

		scrollpane.setBounds(113, 0, 351, 762);
		
		chattingListPanel.add(scrollpane);



		mainPanel.add(chattingRoomPanel,"roomName");
		
		chattingRoomPanel.setLayout(null);
		chattingRoomPanel.setBackground(new Color(255, 217, 0));
		System.out.println(roomName);
		JLabel roomTitle = new JLabel("제목:"+ roomName +"의 방입니다.");
		roomTitle.setFont(new Font("Verdana", Font.PLAIN, 16));
		roomTitle.setHorizontalAlignment(SwingConstants.CENTER);
		roomTitle.setBounds(6, 6, 372, 52);
		chattingRoomPanel.add(roomTitle);
		
		JButton exitRoomButton = new JButton("");
		exitRoomButton.setBounds(373, 6, 90, 52);
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
		messageInput.setBounds(6, 699, 399, 57);
		chattingRoomPanel.add(messageInput);
		messageInput.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 70, 457, 624);
		chattingRoomPanel.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setColumnHeaderView(textArea);
		

		
	}
	
	private void updateRoomList() {
		model = new DefaultListModel<>();
		for (Room room : rooms) {
			model.addElement(room.getName());
		}
		roomList.setModel(model);
	}
}
