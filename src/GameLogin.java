import java.awt.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import DB.DBconnection;
import java.sql.*;

public class GameLogin extends JFrame{
	Container c;
	static String userId;
//---------------------------게임 DB 연동 ---------------------------------
	Connection conn; // 연결 db
	Statement stmt = null; //SQL문을 실행하기 위해서는 Statement 클래스를 이용
	PreparedStatement pstmt = null;
	ResultSet rs;
	
//-------------------------------------오디오-------------------------------------------
	Clip clip;
	String skysound = "audio/밤하늘wav.wav";
//----------------------게임 로그인 화면 인터페이스 -----------------------------
	ImageIcon flogo = new ImageIcon("images/loginLogo.png");
	ImageIcon bg = new ImageIcon("images/background.png");
	ImageIcon gamelogo, background;
	JPanel bgpanel;
	JLabel logolabel, bglabel;
	JButton join, login ,finish;
	JLabel idlabel, pwlabel;
	JTextField idtext;
	JPasswordField pwtext;
	JDialog dialog;


	public GameLogin() {
		setTitle("탈출하는 카카오 프렌즈를 구하라"); //프레임 이름 지정
		setDefaultCloseOperation(EXIT_ON_CLOSE); //닫으면 끝남
		c = getContentPane();
		c.setLayout(null);
		
		
		Image originImg = flogo.getImage();
		Image changedImg = originImg.getScaledInstance(650,250,Image.SCALE_SMOOTH);      
		gamelogo = new ImageIcon(changedImg); // 로고 크기 조정
		
		Image originImg2 = bg.getImage();
		Image changedImg2 = originImg2.getScaledInstance(1850,1000,Image.SCALE_SMOOTH); 
		background = new ImageIcon(changedImg2); // 로고 크기 조정
		
		LoginPanel panel = new LoginPanel();
		panel.setSize(620,300);
		panel.setLocation(625,620);
		
		//---------------------------게임 로고---------------------------------
        logolabel = new JLabel(gamelogo);
		logolabel.setSize(650,250);
		logolabel.setLocation(625,450);
		//------------------------------------------------------------------
		
		//---------------------------메인 화면---------------------------------
		
		bgpanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		bgpanel.setLayout(null);
		bgpanel.setSize(1850,1000);
		bgpanel.setLocation(0,0);
		
		//------------------------------------------------------------------
		
		bgpanel.add(logolabel);
		bgpanel.add(panel);
		c.add(bgpanel);
		
		loadAudio(skysound);
		clip.start();
		setSize(1850,1000); //프레임 크기
		setVisible(true); //프레임 보이기
		setResizable(false); //프레임 크기 고정
		setLocationRelativeTo(null); //프레임 화면 중앙에서 띄우기
	}
	
//---------------------------------오디오----------------------------------------
	 void loadAudio(String pathName) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
				
		}catch (LineUnavailableException e) { e.printStackTrace(); }
		 catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
		 catch (IOException e) { e.printStackTrace(); }
	}
		
 //------------------------각 라벨 및 입력 창 & 회원가입, 로그인 버튼----------------------------
	class LoginPanel extends JPanel {
		public LoginPanel() {
			
			setBackground(new Color(245,245,245)); //whitesmoke색(rgb 색상표 참고)
			setLayout(null);
			
			idlabel = new JLabel("아이디"); //아이디 라벨
			idlabel.setSize(100,50);
			idlabel.setLocation(172,90);
			idlabel.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			
			idtext = new JTextField(20); //아이디 입력창
			idtext.setSize(150,30);
			idtext.setLocation(265,102);
			idtext.setFont(new Font("a찐빵M",Font.PLAIN, 25));
			
			pwlabel = new JLabel("비밀번호");  //비밀번호 라벨
			pwlabel.setSize(100,50);
			pwlabel.setLocation(150,140);
			pwlabel.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			
			pwtext = new JPasswordField(20); //비밀번호 입력 창
			pwtext.setSize(150,30);
			pwtext.setLocation(265,150);
			pwtext.setFont(new Font("a찐빵M",Font.PLAIN, 25));
			pwtext.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						
						try {
							String quary = "SELECT * FROM member WHERE userid='"+idtext.getText()+"' AND userpw='"+pwtext.getText()+"'";
							conn = DBconnection.getConnection();
							pstmt = conn.prepareStatement(quary);
							rs = pstmt.executeQuery();
							
							if(idtext.getText().isEmpty() == true || pwtext.getText().isEmpty() == true) {
								JOptionPane.showMessageDialog(null,"아이디/비밀번호 입력해주세요.","ERROR",JOptionPane.ERROR_MESSAGE);
							}
							else if(rs.next() == false) {
								JOptionPane.showMessageDialog(null,"아이디/비밀번호가 일치하지 않습니다.","ERROR",JOptionPane.ERROR_MESSAGE);
								idtext.setText("");
								pwtext.setText("");
							}
							else {
								JOptionPane.showMessageDialog(null,rs.getString("nickname")+"님 환영합니다!","Welcome",JOptionPane.PLAIN_MESSAGE);
								userId = idtext.getText();
								new GameMain();
								clip.setFramePosition(0);
								clip.stop();
								dispose();
							}
							
							rs.close();
						} catch(SQLException esql) {
							esql.printStackTrace();
						}
						
					}
				}
				
			});
			
			
			join = new JButton("회원가입"); //회원가입 버튼
			join.setBackground(Color.LIGHT_GRAY);
			join.setSize(140,35);
			join.setLocation(95,225);
			join.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			join.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new MemberJoin();
				}
				
			});
			
			
			login = new JButton("로그인"); //로그인 버튼
			login.setBackground(Color.LIGHT_GRAY);
			login.setSize(120,35);
			login.setLocation(250,225);
			login.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			login.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					try {
						String quary = "SELECT * FROM member WHERE userid='"+idtext.getText()+"' AND userpw='"+pwtext.getText()+"'";
						conn = DBconnection.getConnection();
						pstmt = conn.prepareStatement(quary);
						rs = pstmt.executeQuery();
						
						
						if(idtext.getText().isEmpty() == true || pwtext.getText().isEmpty() == true) {
							
							JOptionPane.showMessageDialog(null,"아이디/비밀번호 입력해주세요.","ERROR",JOptionPane.ERROR_MESSAGE);
						}
						else if(rs.next() == false) {
							JOptionPane.showMessageDialog(null,"아이디/비밀번호가 일치하지 않습니다.","ERROR",JOptionPane.ERROR_MESSAGE);
							idtext.setText("");
							pwtext.setText("");
						}
						else {
							JOptionPane.showMessageDialog(null,rs.getString("nickname")+"님 환영합니다!","Welcome",JOptionPane.PLAIN_MESSAGE);
							userId = idtext.getText();
							new GameMain();
							dispose();
						}
						
						rs.close();
					} catch(SQLException esql) {
						esql.printStackTrace();
					}
				}
				
			});
			
			finish = new JButton("나가기"); //나가기 버튼
			finish.setBackground(Color.LIGHT_GRAY);
			finish.setSize(120,35);
			finish.setLocation(385,225);
			finish.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			finish.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
				
			});
			
			
			add(idlabel);
			add(idtext);
			add(pwlabel);
			add(pwtext);
			add(join);
			add(login);
			add(finish);
		}
	}
	//------------------------------------------------------------------
	

}
