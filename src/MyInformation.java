import java.awt.*;
import javax.swing.*;
import java.sql.*;
import DB.DBconnection;

public class MyInformation extends JFrame{
	Container c;
	ImageIcon logo = new ImageIcon("images/kakaofriends_talk.png");
	ImageIcon infologo;
	JLabel logolabel, nicklabel, idlabel, pwlabel, scorelabel;
	JLabel nickdata, iddata, pwdata, scoredata;
	// 별명, 아이디, 비밀번호(나중에 변경창도 만들어 보자), 현재 최고 점수  들어감
	String pw, nickname;
	int score;
//---------------------------게임 DB 연동 --------------------------------------------
	Connection conn; // 연결 db
	Statement stmt = null; //SQL문을 실행하기 위해서는 Statement 클래스를 이용
	PreparedStatement pstmt = null;
	ResultSet rs;
//--------------------------------------------------------------------------------
	String id = GameLogin.userId;
	
	public MyInformation() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,215,0));
		
		Image originImg = logo.getImage();
		Image changedImg = originImg.getScaledInstance(550,150,Image.SCALE_SMOOTH);     
		infologo = new ImageIcon(changedImg); // 로고 크기 조정
		
		try {
			String quary = "SELECT * FROM member WHERE userid='"+id+"'";
			conn = DBconnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(quary);
			
			while(rs.next()) {
				nickname = rs.getString("nickname");
				pw = rs.getString("userpw");
				score = rs.getInt("score");
			}
			
			rs.close();
			
		}catch(SQLException esql) {
			esql.printStackTrace();
		}
		
		
		logolabel = new JLabel(infologo);
		logolabel.setSize(550,150);
		logolabel.setLocation(0,0);
		c.add(logolabel);
		
		nicklabel = new JLabel("별명");
		nicklabel.setSize(90,25);
		nicklabel.setLocation(100,185);
		nicklabel.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		nicklabel.setVerticalAlignment(SwingConstants.CENTER);
		nicklabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		nickdata = new JLabel(nickname);
		nickdata.setSize(200,25);
		nickdata.setLocation(210,185);
		nickdata.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		nickdata.setBackground(Color.white);
		nickdata.setOpaque(true);
		nickdata.setVerticalAlignment(SwingConstants.CENTER);
		nickdata.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(nickdata);
		
		idlabel = new JLabel("ID");
		idlabel.setSize(90,25);
		idlabel.setLocation(100,240);
		idlabel.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		idlabel.setVerticalAlignment(SwingConstants.CENTER);
		idlabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		iddata = new JLabel(id);
		iddata.setSize(200,25);
		iddata.setLocation(210,240);
		iddata.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		iddata.setBackground(Color.white);
		iddata.setOpaque(true);
		iddata.setVerticalAlignment(SwingConstants.CENTER);
		iddata.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(iddata);
		
		pwlabel = new JLabel("비밀번호");
		pwlabel.setSize(90,25);
		pwlabel.setLocation(100,295);
		pwlabel.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		pwlabel.setVerticalAlignment(SwingConstants.CENTER);
		pwlabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		pwdata = new JLabel(pw);
		pwdata.setSize(200,25);
		pwdata.setLocation(210,295);
		pwdata.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		pwdata.setBackground(Color.white);
		pwdata.setOpaque(true);
		pwdata.setVerticalAlignment(SwingConstants.CENTER);
		pwdata.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(pwdata);
		
		scorelabel = new JLabel("최고점수");
		scorelabel.setSize(90,25);
		scorelabel.setLocation(100,350);
		scorelabel.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		scorelabel.setVerticalAlignment(SwingConstants.CENTER);
		scorelabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		scoredata = new JLabel(Integer.toString(score)+"점");
		scoredata.setSize(200,25);
		scoredata.setLocation(210,350);
		scoredata.setFont(new Font("a시월구일2", Font.PLAIN, 22));
		scoredata.setBackground(Color.white);
		scoredata.setOpaque(true);
		scoredata.setVerticalAlignment(SwingConstants.CENTER);
		scoredata.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(scoredata);
		
		
		c.add(nicklabel);
		c.add(idlabel);
		c.add(pwlabel);
		c.add(scorelabel);
		
		setSize(550,460);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
