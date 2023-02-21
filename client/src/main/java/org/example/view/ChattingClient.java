package org.example.view;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.request.*;
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
import java.nio.channels.SocketChannel;


@Getter
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
	private Room room;
	private int index;
	
	private JPanel mainPanel;
	private JList<String> userList;

	private JPanel joinPanel ;
	private JButton joinButton;
	private JTextField joinNickname;
	private ImageIcon kakaoImage;

	private String roomName;
	private String nickname;
	private JPanel chattingRoomPanel;
	private JTextField messageInput;
	private JList<String> roomList;
	private JScrollPane scrollpane;
	@Getter @Setter
	private DefaultListModel<String> model =  new DefaultListModel<>();

	private DefaultListModel<String> userListModel;

	private JPanel chattingListPanel;
	private JLabel roomTitle;
	private JTextArea contentView;

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
		userListModel = new DefaultListModel<>();
		userList = new JList<String>(userListModel);
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
		

		
		joinNickname = new JTextField();
		
		joinNickname.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String ip = "127.0.0.1";
					int port = 8889;

					try {
						socket = new Socket(ip, port);
						ClientRecive clientRecive = new ClientRecive(socket);
						clientRecive.start();

						LoginReqDto loginReqDto = new LoginReqDto(joinNickname.getText());
						nickname = joinNickname.getText();
						userListModel.addElement(nickname);


						String joinJson = gson.toJson(loginReqDto);
						sendRequest("join", joinJson);

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
				int port = 8889;

				try {
					socket = new Socket(ip, port);
					ClientRecive clientRecive = new ClientRecive(socket);
					clientRecive.start();

					LoginReqDto loginReqDto = new LoginReqDto(joinNickname.getText());
					nickname = joinNickname.getText();
					userListModel.addElement(nickname);

					String joinJson = gson.toJson(loginReqDto);
					sendRequest("join", joinJson);

					if (socket != null) {
						CardLayout mainLayout = (CardLayout)mainPanel.getLayout();
						mainLayout.show(mainPanel, "chattingList");
					}

					JOptionPane.showMessageDialog(null, loginReqDto.getNickname() + "님 환영합니다.", "환영메세지", JOptionPane.INFORMATION_MESSAGE);

				} catch (ConnectException ex) {
					JOptionPane.showMessageDialog(null, "접속오류", "접속오류", JOptionPane.ERROR_MESSAGE);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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

		roomList = new JList<>(model);
		scrollpane = new JScrollPane(roomList);

		scrollpane.setViewportView(roomList);
		chattingListPanel.add(scrollpane);
		roomPlusButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				String title = JOptionPane.showInputDialog(null, "방제목을 입력해주세요.");

				String kingName = nickname;


				CreateRoomReqDto createRoomReqDto = new CreateRoomReqDto(title, kingName);
				String createRoomJson = gson.toJson(createRoomReqDto);

				sendRequest("createRoom", createRoomJson);


				if (title != null) {

					CardLayout mainLayout = (CardLayout)mainPanel.getLayout();
					mainLayout.show(mainPanel, "chattingRoom");

				}
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




		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {

					index = roomList.locationToIndex(e.getPoint());
					roomName = roomList.getModel().getElementAt(index);

					JoinRoomReqDto joinRoomReqDto = new JoinRoomReqDto(roomName, joinNickname.getText());
					String joinRoomJson = gson.toJson(joinRoomReqDto);

					sendRequest("joinRoom", joinRoomJson);




				}
			}
		});
		
		roomList.setBounds(0, 212, 324, -81);

		scrollpane.setBounds(113, 0, 351, 762);


		chattingRoomPanel = new JPanel();
		
		mainPanel.add(chattingRoomPanel, "chattingRoom");

		chattingRoomPanel.setLayout(null);
		chattingRoomPanel.setBackground(new Color(255, 217, 0));
//		System.out.println(model.getElementAt(index));
		roomTitle = new JLabel();
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
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 70, 457, 624);
		chattingRoomPanel.add(scrollPane_1);
		
		contentView = new JTextArea();

		scrollPane_1.setColumnHeaderView(contentView);

	}

	private void sendMessage() {
		if(!messageInput.getText().isBlank()) {

			/*from : room 객체에있는 유저들*/
			MessageReqDto messageReqDto = new MessageReqDto(joinNickname.getText(), messageInput.getText());

			sendRequest("message", gson.toJson(messageReqDto));


		}
	}

	private void sendRequest(String resource, String body){
		OutputStream outputStream;
		try {
			outputStream = socket.getOutputStream();
			PrintWriter out = new PrintWriter(outputStream, true);

			RequestDto requestDto = new RequestDto(resource, body);


			out.println(gson.toJson(requestDto));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
