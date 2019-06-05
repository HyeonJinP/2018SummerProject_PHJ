import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import DB.DBconnection;

public class GameOver extends JFrame{
	Container c;
	String id = GameLogin.userId;
	int score = GameStart.result;
//---------------------------게임 DB 연동 --------------------------------------------
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs;
//--------------------------------------------------------------------------------
	ImageIcon fake = new ImageIcon("images/gameover.png");
	ImageIcon ss = new ImageIcon("images/scorebg.png");
	ImageIcon gameover, gameresult;
	JLabel gameoverla, scorela;
	JPanel centerpanel;
	JButton replaybtn, mainbtn;
	ImageIcon rr = new ImageIcon("images/replay.png");
	ImageIcon mm = new ImageIcon("images/main.png");
	ImageIcon replay, main;
	
	public GameOver() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		c.setBackground(Color.white);

//----------------------------------------------이미지--------------------------------------------------------
		Image img = fake.getImage();
		Image changedimg = img.getScaledInstance(500,150,Image.SCALE_SMOOTH);     
		gameover = new ImageIcon(changedimg);
		
		Image bgimg = ss.getImage();
		Image changedbg = bgimg.getScaledInstance(500,350,Image.SCALE_SMOOTH);     
		gameresult = new ImageIcon(changedbg);
		
		Image btnimg1 = rr.getImage();
		Image changedbtnimg1 = btnimg1.getScaledInstance(110,50,Image.SCALE_SMOOTH);     
		replay = new ImageIcon(changedbtnimg1);
		
		Image btnimg2 = mm.getImage();
		Image changedbtnimg2 = btnimg2.getScaledInstance(110,50,Image.SCALE_SMOOTH);     
		main = new ImageIcon(changedbtnimg2);
		
//---------------------------------------------------------------------------------------------------------
		
		gameoverla = new JLabel(gameover);
		gameoverla.setSize(500,150);
		
		centerpanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(gameresult.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		centerpanel.setBackground(Color.white);
		centerpanel.setLayout(null);
		
		scorela = new JLabel(score+"점");
		scorela.setSize(140,40);
		scorela.setLocation(165,112);
		scorela.setFont(new Font("a시월구일2", Font.PLAIN, 40));
		scorela.setVerticalAlignment(SwingConstants.CENTER);
		scorela.setHorizontalAlignment(SwingConstants.CENTER);		
		
		replaybtn = new JButton(replay);
		replaybtn.setSize(110,50);
		replaybtn.setLocation(125,240);
		replaybtn.setBorderPainted(false);
		replaybtn.setBackground(Color.white);
		replaybtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameStart();
				dispose();
			}
			
		});
		
		mainbtn = new JButton(main);
		mainbtn.setSize(110,50);
		mainbtn.setLocation(255,240);
		mainbtn.setBorderPainted(false);
		mainbtn.setBackground(Color.white);
		mainbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameMain();
				dispose();
			}
			
		});
		
		centerpanel.add(scorela);
		centerpanel.add(replaybtn);
		centerpanel.add(mainbtn);
		c.add(gameoverla, BorderLayout.NORTH);
		c.add(centerpanel, BorderLayout.CENTER);
		
		setSize(500,500);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
