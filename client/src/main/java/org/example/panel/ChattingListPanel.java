package org.example.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.example.dto.request.CreateRoomReqDto;
import org.example.dto.request.JoinRoomReqDto;
import org.example.dto.request.RequestDto;
import org.example.view.ChattingClient;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

public class ChattingListPanel extends JPanel{
	private static ChattingListPanel instance;
	public static ChattingListPanel getInstance() {
		if(instance == null) {
			instance = new ChattingListPanel();
		}
		return instance;
	}
	private ChattingClient ccm = ChattingClient.getInstance();
	private JoinPanel jp = JoinPanel.getJoinPanelInstance();
	private Gson gson;
	
	private JPanel chattingListPanel;
	private String title;
	private int index;
	private String roomName;
	private ImageIcon kakaoImage;
	
	@Getter @Setter
	private DefaultListModel<String> model;
	private JList<String> roomList;
	private JScrollPane scrollpane;
	
	public ChattingListPanel() {
		gson = new Gson();
		chattingListPanel = new JPanel();
		
		chattingListPanel.setLayout(null);
		chattingListPanel.setBackground(new Color(255, 217, 0));

		ImageIcon plusButton = new ImageIcon(ChattingClient.class.getResource("../image/플러스버튼이미지.png"));
		JButton roomPlusButton = new JButton("");

		roomPlusButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				title = JOptionPane.showInputDialog(null, "방제목을 입력해주세요.");

				String kingName = JoinPanel.getJoinPanelInstance().getNickname();
				boolean flag = true;
				if (title == null) { //값이 없을 때 취소버튼
					CardLayout mainLayout = (CardLayout) ccm.getLayout();
					mainLayout.show(ccm, "chattingList");
				} else if (title.isBlank()) { //값이 없을때 확인버튼
					while (flag) { //값이 없을때 확인버튼 시 돌아가는 무한루프
						JOptionPane.showMessageDialog(null, "방제목 입력 해주세요!!!", "!!", JOptionPane.ERROR_MESSAGE);
						title = JOptionPane.showInputDialog(null, "방제목을 입력해주세요.");

						if (title == null) { // 값 입력 대기중에 취소 버튼
							break; //반복종료 while 문 밖으로 나감
						} else if (!title.isBlank()) {
							CardLayout mainLayout = (CardLayout) ccm.getLayout();
							mainLayout.show(ccm, "chattingRoom");
							break; // 입력됐을때 반복종료
						}
					}
				} else {
					CreateRoomReqDto createRoomReqDto = new CreateRoomReqDto(title, kingName);
					String createRoomJson = gson.toJson(createRoomReqDto);

					sendRequest("createRoom", createRoomJson);
					
					CardLayout mainLayout = (CardLayout) ccm.getLayout();
					mainLayout.show(ccm, "chattingRoom");
				}
			}
		});
		model = new DefaultListModel<>();
		roomList = new JList<>(model);
		scrollpane = new JScrollPane(roomList);
		
		scrollpane.setViewportView(roomList);
		chattingListPanel.add(scrollpane);


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

					JoinRoomReqDto joinRoomReqDto = new JoinRoomReqDto(roomName, jp.getJoinNickname().getText());
					String joinRoomJson = gson.toJson(joinRoomReqDto);

					sendRequest("joinRoom", joinRoomJson);
					
					CardLayout mainLayout = (CardLayout)ChattingClient.getInstance().getMainPanel().getLayout();
					mainLayout.show(ChattingClient.getInstance().getMainPanel(), "chattingRoom");

				}
			}
		});
		
		roomList.setBounds(0, 212, 324, -81);

		scrollpane.setBounds(113, 0, 351, 762);

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
