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
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Component;

public class ChattingView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

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
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(255, 255, 51));
		contentPane.add(loginPanel, "name_4791122600800");
		loginPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(42, 455, 368, 59);
		loginPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("카카오로 시작하기");
		btnNewButton.setIcon(new ImageIcon(ChattingView.class.getResource("/org/example/image/kakao_login_large_wide.png")));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(0, 51, 255));
		btnNewButton.setBounds(42, 535, 368, 49);
		loginPanel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIconTextGap(2);
		lblNewLabel_1.setIgnoreRepaint(true);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(ChattingView.class.getResource("/org/example/image/kakao-talk.png")));
		lblNewLabel_1.setBounds(118, 143, 215, 170);
		loginPanel.add(lblNewLabel_1);
		
		JPanel userListPanel = new JPanel();
		userListPanel.setBackground(new Color(255, 255, 51));
		contentPane.add(userListPanel, "name_5238560318400");
		userListPanel.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(89, 0, 367, 753);
		userListPanel.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(91, 0, 365, 753);
		userListPanel.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JPanel chattingPanel = new JPanel();
		chattingPanel.setBackground(new Color(255, 255, 51));
		contentPane.add(chattingPanel, "name_5298663423800");
		chattingPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 79, 456, 600);
		chattingPanel.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 676, 392, 77);
		chattingPanel.add(scrollPane_2);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setIcon(new ImageIcon(ChattingView.class.getResource("/org/example/image/log-out.png")));
		btnNewButton_1.setBounds(392, 676, 64, 77);
		chattingPanel.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("제목: 000의 방");
		lblNewLabel.setFont(new Font("LG Smart UI SemiBold", Font.PLAIN, 25));
		lblNewLabel.setBounds(96, 0, 265, 77);
		chattingPanel.add(lblNewLabel);
	}
}
