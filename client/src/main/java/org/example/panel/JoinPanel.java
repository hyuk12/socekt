package org.example.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.example.dto.request.LoginReqDto;
import org.example.dto.request.RequestDto;
import org.example.view.ChattingClient;
import org.example.viewcontroller.ClientRecive;

import com.google.gson.Gson;

import lombok.Getter;

public class JoinPanel extends JPanel{
	
	private static JoinPanel instance ;
	
	public static JoinPanel getJoinPanelInstance() {
		if (instance == null) {
			instance = new JoinPanel();
		}
		return instance;
	}
	private ChattingClient ccm = ChattingClient.getInstance();
	@Getter
	private Socket socket;
	private Gson gson;
	
	private JPanel joinPanel ;
	private ImageIcon kakaoImage;
	@Getter
	private JTextField joinNickname;
	@Getter
	private String nickname;
	private JButton joinButton;
	
	private JList<String> userList;
	private DefaultListModel<String> userListModel;
	
	private JoinPanel() {
		gson = new Gson();
		joinPanel = new JPanel();
		joinPanel.setBackground(new Color(255, 217, 0));
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
					if (joinNickname.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "please input nickname", "입장불가", JOptionPane.ERROR_MESSAGE);
					} else {
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
								CardLayout mainLayout = (CardLayout) ccm.getMainPanel().getLayout();
								mainLayout.show(ccm.getMainPanel(), "chattingList");
							}

							//						JOptionPane.showMessageDialog(null, loginReqDto.getNickname() + "님 환영합니다.", "환영메세지", JOptionPane.INFORMATION_MESSAGE);

						} catch (ConnectException ex) {
							JOptionPane.showMessageDialog(null, "접속오류", "접속오류", JOptionPane.ERROR_MESSAGE);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		
		joinButton = new JButton("");
		joinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (joinNickname.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "please input nickname", "입장불가", JOptionPane.ERROR_MESSAGE);


				} else{
					joinButton.setEnabled(true);
					joinButton.setFocusable(true);
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


						if (socket != null && !joinNickname.getText().isBlank()) {
							joinButton.setEnabled(true);
							CardLayout mainLayout = (CardLayout) ChattingClient.getInstance().getMainPanel().getLayout();
							mainLayout.show(ChattingClient.getInstance().getMainPanel(), "chattingList");
						}

						//					JOptionPane.showMessageDialog(null, loginReqDto.getNickname() + "님 환영합니다.", "환영메세지", JOptionPane.INFORMATION_MESSAGE);

					} catch (ConnectException ex) {
						JOptionPane.showMessageDialog(null, "접속오류", "접속오류", JOptionPane.ERROR_MESSAGE);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
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
