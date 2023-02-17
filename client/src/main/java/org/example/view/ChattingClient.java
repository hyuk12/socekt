package org.example.view;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ChattingClient extends JFrame {
	private static final long serialVersionUID = 1L;

	private static ChattingClient instance;
	
	public static ChattingClient getInstance() {
		if(instance == null) {
			instance = new ChattingClient();
		}
		return instance;
	}

	private JPanel mainPanel;
	 
	private JPanel joinPanel ;
	private JButton joinButton;
	private JTextField joinNickname;
	private ImageIcon kakaoImage;
	private JTextField messageInput;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChattingClient frame = new ChattingClient();
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
		


		
		joinButton = new JButton("");
		joinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		
		joinNickname = new JTextField();
		joinNickname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		joinNickname.setBounds(62, 473, 328, 49);
		joinPanel.add(joinNickname);
		joinNickname.setColumns(10);
		
		ImageIcon plusButton = new ImageIcon(ChattingClient.class.getResource("../image/플러스버튼이미지.png"));
		
		JPanel chattingRoomPanel = new JPanel();
		mainPanel.add(chattingRoomPanel, "name_53896203369291");
		chattingRoomPanel.setLayout(null);
		chattingRoomPanel.setBackground(new Color(255, 217, 0));
		
		JLabel roomTitle = new JLabel("제목: 000의 방입니다.");
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
		
		JPanel chattingListPanel = new JPanel();
		mainPanel.add(chattingListPanel, "name_55790787035083");
		chattingListPanel.setLayout(null);
		chattingListPanel.setBackground(new Color(255, 217, 0));
		
		JButton roomPlusButton = new JButton("");
		roomPlusButton.setBounds(0, 102, 101, 98);
		chattingListPanel.add(roomPlusButton);
		roomPlusButton.setIcon(plusButton);
		roomPlusButton.setBorderPainted(false);
		roomPlusButton.setFocusPainted(false);
		roomPlusButton.setContentAreaFilled(false);
		
		JLabel kakaoImg = new JLabel(kakaoImage);
		kakaoImg.setBounds(0, 17, 101, 81);
		chattingListPanel.add(kakaoImg);
		
		
		JList<String> roomList = new JList<String>();
		roomList.setBounds(0, 212, 324, -81);
		chattingListPanel.add(roomList);
		
		JScrollPane scrollpane = new JScrollPane(roomList);
		scrollpane.setBounds(113, 0, 351, 762);
		chattingListPanel.add(scrollpane);
		
	}
}
