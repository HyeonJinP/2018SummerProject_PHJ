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
//---------------------------���� DB ���� ---------------------------------
	Connection conn; // ���� db
	Statement stmt = null; //SQL���� �����ϱ� ���ؼ��� Statement Ŭ������ �̿�
	PreparedStatement pstmt = null;
	ResultSet rs;
	
//-------------------------------------�����-------------------------------------------
	Clip clip;
	String skysound = "audio/���ϴ�wav.wav";
//----------------------���� �α��� ȭ�� �������̽� -----------------------------
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
		setTitle("Ż���ϴ� īī�� ����� ���϶�"); //������ �̸� ����
		setDefaultCloseOperation(EXIT_ON_CLOSE); //������ ����
		c = getContentPane();
		c.setLayout(null);
		
		
		Image originImg = flogo.getImage();
		Image changedImg = originImg.getScaledInstance(650,250,Image.SCALE_SMOOTH);      
		gamelogo = new ImageIcon(changedImg); // �ΰ� ũ�� ����
		
		Image originImg2 = bg.getImage();
		Image changedImg2 = originImg2.getScaledInstance(1850,1000,Image.SCALE_SMOOTH); 
		background = new ImageIcon(changedImg2); // �ΰ� ũ�� ����
		
		LoginPanel panel = new LoginPanel();
		panel.setSize(620,300);
		panel.setLocation(625,620);
		
		//---------------------------���� �ΰ�---------------------------------
        logolabel = new JLabel(gamelogo);
		logolabel.setSize(650,250);
		logolabel.setLocation(625,450);
		//------------------------------------------------------------------
		
		//---------------------------���� ȭ��---------------------------------
		
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
		setSize(1850,1000); //������ ũ��
		setVisible(true); //������ ���̱�
		setResizable(false); //������ ũ�� ����
		setLocationRelativeTo(null); //������ ȭ�� �߾ӿ��� ����
	}
	
//---------------------------------�����----------------------------------------
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
		
 //------------------------�� �� �� �Է� â & ȸ������, �α��� ��ư----------------------------
	class LoginPanel extends JPanel {
		public LoginPanel() {
			
			setBackground(new Color(245,245,245)); //whitesmoke��(rgb ����ǥ ����)
			setLayout(null);
			
			idlabel = new JLabel("���̵�"); //���̵� ��
			idlabel.setSize(100,50);
			idlabel.setLocation(172,90);
			idlabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
			
			idtext = new JTextField(20); //���̵� �Է�â
			idtext.setSize(150,30);
			idtext.setLocation(265,102);
			idtext.setFont(new Font("a��M",Font.PLAIN, 25));
			
			pwlabel = new JLabel("��й�ȣ");  //��й�ȣ ��
			pwlabel.setSize(100,50);
			pwlabel.setLocation(150,140);
			pwlabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
			
			pwtext = new JPasswordField(20); //��й�ȣ �Է� â
			pwtext.setSize(150,30);
			pwtext.setLocation(265,150);
			pwtext.setFont(new Font("a��M",Font.PLAIN, 25));
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
								JOptionPane.showMessageDialog(null,"���̵�/��й�ȣ �Է����ּ���.","ERROR",JOptionPane.ERROR_MESSAGE);
							}
							else if(rs.next() == false) {
								JOptionPane.showMessageDialog(null,"���̵�/��й�ȣ�� ��ġ���� �ʽ��ϴ�.","ERROR",JOptionPane.ERROR_MESSAGE);
								idtext.setText("");
								pwtext.setText("");
							}
							else {
								JOptionPane.showMessageDialog(null,rs.getString("nickname")+"�� ȯ���մϴ�!","Welcome",JOptionPane.PLAIN_MESSAGE);
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
			
			
			join = new JButton("ȸ������"); //ȸ������ ��ư
			join.setBackground(Color.LIGHT_GRAY);
			join.setSize(140,35);
			join.setLocation(95,225);
			join.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
			join.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new MemberJoin();
				}
				
			});
			
			
			login = new JButton("�α���"); //�α��� ��ư
			login.setBackground(Color.LIGHT_GRAY);
			login.setSize(120,35);
			login.setLocation(250,225);
			login.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
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
							
							JOptionPane.showMessageDialog(null,"���̵�/��й�ȣ �Է����ּ���.","ERROR",JOptionPane.ERROR_MESSAGE);
						}
						else if(rs.next() == false) {
							JOptionPane.showMessageDialog(null,"���̵�/��й�ȣ�� ��ġ���� �ʽ��ϴ�.","ERROR",JOptionPane.ERROR_MESSAGE);
							idtext.setText("");
							pwtext.setText("");
						}
						else {
							JOptionPane.showMessageDialog(null,rs.getString("nickname")+"�� ȯ���մϴ�!","Welcome",JOptionPane.PLAIN_MESSAGE);
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
			
			finish = new JButton("������"); //������ ��ư
			finish.setBackground(Color.LIGHT_GRAY);
			finish.setSize(120,35);
			finish.setLocation(385,225);
			finish.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
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
