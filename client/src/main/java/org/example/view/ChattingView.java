package org.example.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChattingView extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField massageInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChattingView frame = new ChattingView();
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
	public ChattingView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 51));
		contentPane.add(mainPanel, "name_4791122600800");
		mainPanel.setLayout(null);
		
		usernameField = new JTextField();
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBackground(new Color(255, 255, 255));
		usernameField.setBounds(42, 465, 368, 49);
		mainPanel.add(usernameField);
		usernameField.setColumns(10);
		
		JButton loginButton = new JButton("카카오로 시작하기");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		loginButton.setIcon(new ImageIcon(ChattingView.class.getResource("/org/example/image/kakao_login_large_wide.png")));
		loginButton.setForeground(new Color(0, 0, 0));
		loginButton.setBackground(new Color(0, 51, 255));
		loginButton.setBounds(42, 535, 368, 49);
		mainPanel.add(loginButton);
		
		JLabel mainIcon = new JLabel("New label");
		mainIcon.setIconTextGap(2);
		mainIcon.setIgnoreRepaint(true);
		mainIcon.setHorizontalAlignment(SwingConstants.CENTER);
		mainIcon.setIcon(new ImageIcon(ChattingView.class.getResource("/org/example/image/kakao-talk.png")));
		mainIcon.setBounds(130, 144, 190, 179);
	
		
		try {
			BufferedImage originalImage = ImageIO.read(new File("/org/example/image/kakao-talk.png"));
			Image scaledImage = originalImage.getScaledInstance(5, 5, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(scaledImage);
			JLabel label = new JLabel(icon);
			getContentPane().add(label);
		} catch (IOException e) {
			System.out.println("이미지를 불러올 수 없습니다.");
			e.printStackTrace();
		}
		
		setVisible(true);
		mainPanel.add(mainIcon);
		
		JPanel userListPanel = new JPanel();
		userListPanel.setBackground(new Color(255, 255, 51));
		contentPane.add(userListPanel, "name_5238560318400");
		userListPanel.setLayout(null);
		
		JTextArea userArea = new JTextArea();
		userArea.setBounds(89, 0, 367, 753);
		userListPanel.add(userArea);
		
		JScrollPane userListScroll = new JScrollPane();
		userListScroll.setBounds(91, 0, 365, 753);
		userListPanel.add(userListScroll);
		
		JList userList = new JList();
		userListScroll.setViewportView(userList);
		
		JLabel iconLabel = new JLabel("New label");
		iconLabel.setBounds(12, 27, 65, 53);
		userListPanel.add(iconLabel);
		
		JButton addRoomButton = new JButton("");
		addRoomButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		addRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addRoomButton.setBounds(12, 100, 65, 42);
		userListPanel.add(addRoomButton);
		
		JPanel chattingPanel = new JPanel();
		chattingPanel.setBackground(new Color(255, 255, 51));
		contentPane.add(chattingPanel, "name_5298663423800");
		chattingPanel.setLayout(null);
		
		JScrollPane contentScroll = new JScrollPane();
		contentScroll.setBounds(0, 79, 456, 600);
		chattingPanel.add(contentScroll);
		
		JTextArea contentView = new JTextArea();
		contentScroll.setViewportView(contentView);
		
		JScrollPane messageScroll = new JScrollPane();
		messageScroll.setBounds(0, 676, 392, 77);
		chattingPanel.add(messageScroll);
		
		massageInput = new JTextField();
		massageInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		massageInput.addMouseListener(new MouseAdapter() {
			
		});
		messageScroll.setViewportView(massageInput);
		massageInput.setColumns(10);
		
		JButton sendButton = new JButton("전송");
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sendButton.setIcon(new ImageIcon(ChattingView.class.getResource("/org/example/image/log-out.png")));
		sendButton.setBounds(392, 676, 64, 77);
		chattingPanel.add(sendButton);
		
		JLabel commnicativeTitle = new JLabel("제목: " + "+ 변수명 + " + "방");
		commnicativeTitle.setFont(new Font("LG Smart UI SemiBold", Font.PLAIN, 25));
		commnicativeTitle.setBounds(108, 0, 236, 77);
		chattingPanel.add(commnicativeTitle);
		
		JLabel iconLalbel_2 = new JLabel("");
		iconLalbel_2.setHorizontalAlignment(SwingConstants.CENTER);
		iconLalbel_2.setBounds(32, 17, 52, 52);
		chattingPanel.add(iconLalbel_2);
		
		JButton exitButton = new JButton("나가기");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		exitButton.setBounds(378, 17, 52, 52);
		chattingPanel.add(exitButton);
	}
}
