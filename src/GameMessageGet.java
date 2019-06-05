import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import DB.DBconnection;

public class GameMessageGet extends JFrame{
	String id = GameLogin.userId;
	String nickname = GameMessage.nickname;
	String msender = GameMessage.msender;
	String mtitle = GameMessage.mtitle;
	
	static String rereceiver;
	
	JPanel cpanel, btnpanel;
	JTextField sender, receiver, title; // 문자 보내는 사람, 받는 사람, 제목
	JTextArea content; // 문자 내용
	JLabel snick, rnick, ctext, ttext;
	JButton sendbtn, returnbtn; // 답장버튼, 목록버튼
	Container c;
//-----------------------------------게임 DB 연동 --------------------------------------------
	Connection conn = DBconnection.getConnection();
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
	
	public GameMessageGet() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		cpanel = new JPanel();
		cpanel.setLayout(null);
		cpanel.setBackground(new Color(255,204,204));
		
		btnpanel = new JPanel();
		btnpanel.setLayout(new FlowLayout());
		btnpanel.setBackground(new Color(255,204,204));
		
//--------------------------------------cpanel 인터페이스&기능&database----------------------------------------------
		
		snick = new JLabel("보내는 사람");
		snick.setSize(115,30);
		snick.setLocation(25,35);
		snick.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		snick.setVerticalAlignment(SwingConstants.CENTER);
		snick.setHorizontalAlignment(SwingConstants.RIGHT);
		
		sender = new JTextField(msender);
		sender.setSize(150,30);
		sender.setLocation(150,35);
		sender.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		sender.setHorizontalAlignment(SwingConstants.CENTER);
		
		rnick = new JLabel("받는 사람");
		rnick.setSize(115,30);
		rnick.setLocation(25,80);
		rnick.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		rnick.setVerticalAlignment(SwingConstants.CENTER);
		rnick.setHorizontalAlignment(SwingConstants.RIGHT);
		
		receiver = new JTextField(nickname);
		receiver.setSize(150,30);
		receiver.setLocation(150,80);
		receiver.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		receiver.setHorizontalAlignment(SwingConstants.CENTER);
		
		ttext = new JLabel("제목");
		ttext.setSize(115,30);
		ttext.setLocation(25,125);
		ttext.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		ttext.setVerticalAlignment(SwingConstants.CENTER);
		ttext.setHorizontalAlignment(SwingConstants.RIGHT);
		
		title = new JTextField(mtitle);
		title.setSize(200,30);
		title.setLocation(150,125);
		title.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		
		ctext = new JLabel("내용");
		ctext.setSize(115,30);
		ctext.setLocation(25,150);
		ctext.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		ctext.setVerticalAlignment(SwingConstants.CENTER);
		ctext.setHorizontalAlignment(SwingConstants.LEFT);
		
		content = new JTextArea(30,30);
		content.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		JScrollPane sc = new JScrollPane(content);
		sc.setBounds(25,190,440,215); //JScrollPane에 포함된 컴포넌트 크기를 지정함!
		sc.setBackground(Color.WHITE);
		c.add(sc);
		
		
		 try {
	          String quary = "SELECT * FROM message WHERE sender='"+msender+"' AND title='"+mtitle+"'"; 
	          conn = DBconnection.getConnection();
	          pstmt = conn.prepareStatement(quary);
	          rs = pstmt.executeQuery();
	          
	          if(rs.next()) {
	        	  content.setText(rs.getString("content"));
	        	}

	      } catch (SQLException sqle) {
	   	  sqle.printStackTrace();
	      }
		 
		 
//--------------------------------------btnpanel 인터페이스&기능&database----------------------------------------------
		 
		 sendbtn = new JButton("답장");
		 sendbtn.setSize(50,25);
		 sendbtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		 sendbtn.setBackground(Color.LIGHT_GRAY);
		 sendbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rereceiver = msender;
				new GameMessageReturn();
				dispose();
			}
			 
		 });
		 
		 returnbtn = new JButton("목록"); // 목록으로 돌아가기 버튼
		 returnbtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		 returnbtn.setBackground(Color.LIGHT_GRAY);
		 returnbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			 
		 });
		
//--------------------------------------------------------------------------------------
		
		cpanel.add(snick);
		cpanel.add(sender);
		cpanel.add(rnick);
		cpanel.add(receiver);
		cpanel.add(ttext);
		cpanel.add(title);
		cpanel.add(ctext);
		
		btnpanel.add(sendbtn);
		btnpanel.add(returnbtn);
		
		c.add(cpanel, BorderLayout.CENTER);
		c.add(btnpanel, BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(500,500);
		setResizable(false);
		setLocationRelativeTo(null);
		
	}

}
