import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JFrame{
	Container c;
	String id = GameLogin.userId;
//--------------------------------------------------------------------------------
	JButton methodbtn, startbtn, rankbtn, mebtn, logoutbtn, messagebtn;
	//���۹����� ��ư, ���ӽ��� ��ư, ������ư, ������ ����, �α׾ƿ�, �޼�����
	ImageIcon m = new ImageIcon("images/method.png");
	ImageIcon s = new ImageIcon("images/start.png");
	ImageIcon r = new ImageIcon("images/rank.png");
	ImageIcon me = new ImageIcon("images/me.png");
	ImageIcon logout = new ImageIcon("images/logout.png");
	ImageIcon main = new ImageIcon("images/gameMain.png");
	ImageIcon ms = new ImageIcon("images/Message.png");
	ImageIcon mpng, spng, rpng, mepng, logoutpng, mainbg, messagepng;
	JLabel mainlabel, phjlabel;
	
	public GameMain() {
		setTitle("Ż���ϴ� īī�� ����� ���϶�");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.white);
		
		Image originImg = main.getImage();
		Image changedImg = originImg.getScaledInstance(1850,780,Image.SCALE_SMOOTH);     
		mainbg = new ImageIcon(changedImg);
		
		mainlabel = new JLabel(mainbg); //��� ��
		mainlabel.setSize(1850,780);
		mainlabel.setLocation(0,0);
		
		
		Image originImg1 = m.getImage();
		Image changedImg1 = originImg1.getScaledInstance(350,125,Image.SCALE_SMOOTH);     
		mpng = new ImageIcon(changedImg1);
		
		methodbtn = new JButton(mpng); //���ӹ�� ��ư
		methodbtn.setSize(350,125);
		methodbtn.setLocation(350,780);
		methodbtn.setBackground(Color.white);
		methodbtn.setBorderPainted(false); //��ư�� �ܰ���(Border)�� �����ش�.
		methodbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameMethod();
			}
			
		});
		
		Image originImg2 = s.getImage();
		Image changedImg2 = originImg2.getScaledInstance(350,125,Image.SCALE_SMOOTH);     
		spng = new ImageIcon(changedImg2);
		
		startbtn = new JButton(spng); // ���� ���� ��ư
		startbtn.setSize(350,125);
		startbtn.setLocation(750,780);
	    startbtn.setBackground(Color.white);
		startbtn.setBorderPainted(false);
		startbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameStart();
				dispose();
			}
			
		});
		
		Image originImg3 = r.getImage();
		Image changedImg3 = originImg3.getScaledInstance(350,125,Image.SCALE_SMOOTH);     
		rpng = new ImageIcon(changedImg3);
		
		rankbtn = new JButton(rpng); // ���� ���� ��ư
		rankbtn.setSize(350,125);
		rankbtn.setLocation(1150,780);
		rankbtn.setBackground(Color.white);
		rankbtn.setBorderPainted(false);
		rankbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameRank();
			}
			
		});
		
		Image originImg4 = me.getImage();
		Image changedImg4 = originImg4.getScaledInstance(250,80,Image.SCALE_SMOOTH);     
		mepng = new ImageIcon(changedImg4);
		
		mebtn = new JButton(mepng); // �� ���� ���� ��ư
		mebtn.setSize(250,80);
		mebtn.setLocation(1590,875);
		mebtn.setBackground(Color.white);
		mebtn.setBorderPainted(false);
		mebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MyInformation();
			}
			
		});
		
		Image originImg5 = logout.getImage();
		Image changedImg5 = originImg5.getScaledInstance(250,80,Image.SCALE_SMOOTH);     
		logoutpng = new ImageIcon(changedImg5);
		
		logoutbtn = new JButton(logoutpng); // �α׾ƿ� ��ư
		logoutbtn.setSize(250,80);
		logoutbtn.setLocation(10,875);
		logoutbtn.setBackground(Color.white);
		logoutbtn.setBorderPainted(false);
		logoutbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameLogin();
				dispose();
			}
			
		});
		
		Image originms = ms.getImage();
		Image changedms = originms.getScaledInstance(200,80,Image.SCALE_SMOOTH);     
		messagepng = new ImageIcon(changedms);
		
		messagebtn = new JButton(messagepng);
		messagebtn.setSize(200,80);
		messagebtn.setLocation(1650,785);
		messagebtn.setBackground(Color.WHITE);
		messagebtn.setBorderPainted(false);
		messagebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameMessage();
			}
			
		});
		
		
	//---------------------------�ۼ��ڰ� ������ ��д�---------------------------------------
		phjlabel = new JLabel("made by ������ in Korea");
		phjlabel.setSize(250,25);
		phjlabel.setLocation(285,720);
		phjlabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 20));
	//---------------------------------------------------------------------------------
		
		
		c.add(mainlabel);
		c.add(methodbtn);
		c.add(startbtn);
		c.add(rankbtn);
		c.add(mebtn);
		c.add(logoutbtn);
		c.add(phjlabel);
		c.add(messagebtn);
		
		setSize(1850,1000);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
