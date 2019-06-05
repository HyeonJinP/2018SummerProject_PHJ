import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.sound.sampled.*;
import javax.swing.*;
import DB.DBconnection;

public class GameStart extends JFrame{
	Container c;
	Thread thread;
	int score;
	static int result;
	String id = GameLogin.userId;
	int topscore;
	int size=20;
	int bt_touch = 20;
	
//---------------------------게임 DB 연동 --------------------------------------------
	Connection conn;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
	ResultSetMetaData rsmd;
	int lastrank;
	int originalscore;
//-----------------------------게임 화면 관련---------------------------------------
	ImageIcon bg = new ImageIcon("images/gamebg.png");
	ImageIcon gamebg;
	JPanel bgpanel; //게임 화면
	JPanel infopanel; // 정보 화면
	JPanel mepanel, scopanel;
	
//-----------------------------게임 캐릭터 관련--------------------------------------
	ImageIcon image1 = new ImageIcon("images/friend1.png");
	ImageIcon image2 = new ImageIcon("images/friend2.png");
	ImageIcon image3 = new ImageIcon("images/friend3.png");
	ImageIcon icon[] = new ImageIcon[3];
	JLabel label[] = new JLabel[2000];
	
//-----------------------------게임 폭탄 관련--------------------------------------
	ImageIcon bb1 = new ImageIcon("images/friendbomb1.png");
	ImageIcon bb2 = new ImageIcon("images/friendbomb2.png");
	ImageIcon bb3 = new ImageIcon("images/friendbomb3.png");
	ImageIcon bomb[] = new ImageIcon[3];
	JLabel bombla[] = new JLabel[500];
	ImageIcon bm = new ImageIcon("images/boom.png");
	ImageIcon boom;
	JLabel boomlas[] = new JLabel[500];
	JLabel boomla;
	
//----------------------------------게임 점수 관련--------------------------------------
	ImageIcon score5 = new ImageIcon("images/10score.png");
	ImageIcon score_5;
	JLabel point5;
	boolean scorebl = false;
	
//---------------------------------게임 바구니 관련-------------------------------------
	ImageIcon bt = new ImageIcon("images/basket.png");
	ImageIcon basket ,basketup;
	JLabel btlabel;

	int btx ,bty;
	
//---------------------------------랜덤 박스 관련-------------------------------------
	ImageIcon randomps = new ImageIcon("images/random_present.png");
	ImageIcon showps = new ImageIcon("images/show_present.png");
	ImageIcon rdpresent ,shpresent;
	JLabel random_presentlabel, show_presentlabel;

	int psx ,psy;
	
//---------------------------------게임 스레드 관련-------------------------------------
	Thread thread1, thread2;
	boolean thStatus = true;
	int labelcnt = 0 , labelcnt1 = 0;
	int even = 0, odd = 1, even1 = 2, odd1 = 3;
	boolean evenstatus = false, even1status = false,  oddstatus = false, odd1status = false;
	int time, count = 30, cnt = 3, timecnt = 1;
	int bombtime;
	
	int bombstart, bombcnt = 0;
	boolean bombstatus = false, boomstatus = false;
	int boomcnt = 0, boomnum = 0;
	
	int presentstart, presentscore = 0;
	boolean presentstatus = false, shpresentstatus = false;
	int shownum = 0;
	
//---------------------------------내 정보/게임 목숨 관련--------------------------------
	JLabel lifelabel[] = new JLabel[3];
	JLabel deadlabel[] = new JLabel[3];
	JLabel lslabel, scoslabel, userslabel, velocityslabel, topscoreslabel;
	// 게임 생명, 점수, 사용자 확인, 속도, 최고점수 라벨
	JLabel scorelabel, userlabel, velocitylabel, topscorelabel;
	// 점수,현재 속도, 사용자, 속도, 최고점수 창 라벨
	ImageIcon lifepng = new ImageIcon("images/life.png");
	ImageIcon deadpng = new ImageIcon("images/dead.png");
	ImageIcon lifesh = new ImageIcon("images/lifeshow.png");
	ImageIcon gamesco = new ImageIcon("images/gamescore.png");
	ImageIcon userpng = new ImageIcon("images/user.png");
	ImageIcon velocitypng = new ImageIcon("images/velocity.png");
	ImageIcon topscorepng = new ImageIcon("images/topscore.png");
	ImageIcon life, dead, lifetitle, gamescore, usericon, velocityicon, topscoreicon;
	int chance = 0;
	String nickname;
//-------------------------------------오디오-------------------------------------------
	Clip clip1, clip2;
	String boomsound = "audio/폭탄터지는 소리.wav";
	String skysound = "audio/밤하늘wav.wav";
	
//------------------------------------------------------------------------------------
	
	public GameStart() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
//------------------------- 게임 화면 --------------------------------------------
		Image bgimg = bg.getImage();
		Image changedbg = bgimg.getScaledInstance(1650,1000,Image.SCALE_SMOOTH);     
		gamebg = new ImageIcon(changedbg);
		
		bgpanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(gamebg.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		bgpanel.setLayout(null);
		bgpanel.setSize(1650,1000);
		bgpanel.setLocation(0,0);
		
		bgpanel.addMouseMotionListener(new MyMouseMotionListener());
		bgpanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				btx = e.getX();
			}
			
		});
//----------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
		
				infopanel = new JPanel();
				infopanel.setLayout(null);
				infopanel.setSize(200,1000);
				infopanel.setLocation(1650,0);
				infopanel.setBackground(Color.white);
				
				Image lfs = lifesh.getImage();
				Image chlfs = lfs.getScaledInstance(200,50,Image.SCALE_SMOOTH);     
				lifetitle = new ImageIcon(chlfs);
//----------------------------------------이미지들---------------------------------------------
				Image dd = deadpng.getImage();
				Image chdd = dd.getScaledInstance(60,60,Image.SCALE_SMOOTH);     
				dead = new ImageIcon(chdd);
				
				Image sco = gamesco.getImage();
				Image chsco = sco.getScaledInstance(200,50,Image.SCALE_SMOOTH);     
				gamescore = new ImageIcon(chsco);
				
				Image lf = lifepng.getImage();
				Image chlf = lf.getScaledInstance(60,60,Image.SCALE_SMOOTH);     
				life = new ImageIcon(chlf);
				
				Image userimg = userpng.getImage();
				Image chuserimg = userimg.getScaledInstance(200,50,Image.SCALE_SMOOTH);     
				usericon = new ImageIcon(chuserimg);
				
				Image velocityimg = velocitypng.getImage();
				Image chvelocityimg = velocityimg.getScaledInstance(200,50,Image.SCALE_SMOOTH);     
				velocityicon = new ImageIcon(chvelocityimg);
				
				Image topscoreimg = topscorepng.getImage();
				Image chtopscoreimg = topscoreimg.getScaledInstance(200,50,Image.SCALE_SMOOTH);     
				topscoreicon = new ImageIcon(chtopscoreimg);
//----------------------------------------------------------------------------------------
//-----------------------------------오른쪽 페널(정보)-----------------------------------------
				topscoreslabel = new JLabel(topscoreicon);
				topscoreslabel.setSize(200,50);
				topscoreslabel.setLocation(1650,75);
				
				try {
					String quary = "SELECT MAX(score) FROM member";
					conn = DBconnection.getConnection();
					stmt = conn.createStatement();
					rs = stmt.executeQuery(quary);
					
					
					while(rs.next()) {
						topscore = rs.getInt("MAX(score)");
					}
					
					rs.close();
					
				}catch(SQLException esql) {
					esql.printStackTrace();
				}
				
				topscorelabel = new JLabel(topscore+"점");
				topscorelabel.setSize(200,50);
				topscorelabel.setLocation(1650,135);
				topscorelabel.setFont(new Font("a시월구일2", Font.PLAIN, 35));
				topscorelabel.setVerticalAlignment(SwingConstants.CENTER);
				topscorelabel.setHorizontalAlignment(SwingConstants.CENTER);
				
				lslabel = new JLabel(lifetitle);
				lslabel.setSize(200,50);
				lslabel.setLocation(1650,195);
				
				scoslabel = new JLabel(gamescore);
				scoslabel.setSize(200,50);
				scoslabel.setLocation(1650,325);
				
				scorelabel = new JLabel(score+"점");
				scorelabel.setSize(200,50);
				scorelabel.setLocation(1650,385);
				scorelabel.setForeground(Color.blue);
				scorelabel.setFont(new Font("a시월구일2", Font.PLAIN, 35));
				scorelabel.setVerticalAlignment(SwingConstants.CENTER);
				scorelabel.setHorizontalAlignment(SwingConstants.CENTER);
				
				velocityslabel = new JLabel(velocityicon);
				velocityslabel.setSize(200,50);
				velocityslabel.setLocation(1650,445);
				
				velocitylabel = new JLabel();
				velocitylabel.setSize(200,50);
				velocitylabel.setLocation(1650,505);
				velocitylabel.setForeground(Color.MAGENTA);
				velocitylabel.setFont(new Font("a시월구일2", Font.PLAIN, 35));
				velocitylabel.setVerticalAlignment(SwingConstants.CENTER);
				velocitylabel.setHorizontalAlignment(SwingConstants.CENTER);
				
				userslabel = new JLabel(usericon);
				userslabel.setSize(200,50);
				userslabel.setLocation(1650,565);
				
				try {
					String quary = "SELECT * FROM member WHERE userid='"+id+"'";
					conn = DBconnection.getConnection();
					stmt = conn.createStatement();
					rs = stmt.executeQuery(quary);
					
					while(rs.next()) {
						nickname = rs.getString("nickname");
					}
					
					rs.close();
					
				}catch(SQLException esql) {
					esql.printStackTrace();
				}
				
				userlabel = new JLabel(nickname);
				userlabel.setSize(200,50);
				userlabel.setLocation(1650,615);
				userlabel.setForeground(Color.black);
				userlabel.setFont(new Font("a시월구일2", Font.PLAIN, 22));
				userlabel.setVerticalAlignment(SwingConstants.CENTER);
				userlabel.setHorizontalAlignment(SwingConstants.CENTER);
				
				
				
				for(int i=0;i<lifelabel.length;i++) {
					lifelabel[i] = new JLabel(life);
					lifelabel[i].setSize(60,60);
					lifelabel[i].setBackground(Color.white);
					lifelabel[i].setLocation(60*i+1655,255);
					lifelabel[i].setVisible(true);
					infopanel.add(lifelabel[i]);
				}
				
				
				for(int i=0;i<lifelabel.length;i++) {
					deadlabel[i] = new JLabel(dead);
					deadlabel[i].setSize(60,60);
					deadlabel[i].setBackground(Color.white);
					deadlabel[i].setLocation(60*i+1655,255);
					deadlabel[i].setVisible(false);
					infopanel.add(deadlabel[i]);
				}
				
				infopanel.add(lslabel);
				infopanel.add(scoslabel);
				infopanel.add(scorelabel);
				infopanel.add(velocityslabel);
				infopanel.add(velocitylabel);
				infopanel.add(userslabel);
				infopanel.add(userlabel);
				infopanel.add(topscoreslabel);
				infopanel.add(topscorelabel);
				
				
//-----------------------------------------------------------------------------
//------------------------- 캐릭터 ----------------------------------------------
		
		Image chimg1 = image1.getImage();
		Image changedch1 = chimg1.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		icon[0] = new ImageIcon(changedch1);
		
		Image chimg2 = image2.getImage();
		Image changedch2 = chimg2.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		icon[1] = new ImageIcon(changedch2);
		
		Image chimg3 = image3.getImage();
		Image changedch3 = chimg3.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		icon[2] = new ImageIcon(changedch3);
		
		int chy[] = new int[label.length];
		int chx[] = new int[label.length];
		
		for(int i = 0; i<chy.length; i++) {
			chy[i] = 200;
			chx[i] = (int)(Math.random()*1575)+1;
		}
		
		for(int i=0;i<label.length;i++) {
			if(i%3==0) {
				label[i] = new JLabel(icon[0]);
			}else if(i%3==1) {
				label[i] = new JLabel(icon[1]);
			}else if(i%3==2) {
				label[i] = new JLabel(icon[2]);
			}
			
			label[i].setSize(80,80);
			label[i].setLocation(chx[i],chy[i]);
			if(i!=0) {
				label[i].setVisible(false);
			}
			bgpanel.add(label[i]);
		}

//-----------------------------------------------------------------------------
//---------------------------------폭탄------------------------------------------
		Image bb1img = bb1.getImage();
		Image changedbb1 = bb1img.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		bomb[0] = new ImageIcon(changedbb1);
		
		Image bb2img = bb2.getImage();
		Image changedbb2 = bb2img.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		bomb[1] = new ImageIcon(changedbb2);
		
		Image bb3img = bb3.getImage();
		Image changedbb3 = bb3img.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		bomb[2] = new ImageIcon(changedbb3);
		
		int bombx[] = new int[bombla.length];
		int bomby[] = new int[bombla.length];
		
		
		for(int i = 0; i<bombx.length; i++) {
			bomby[i] = 200;
			bombx[i] = (int)(Math.random()*1575)+1;
		}
		
		
		for(int i=0;i<bombla.length;i++) {
			if(i%3==0) {
				bombla[i] = new JLabel(bomb[0]);
			}else if(i%3==1) {
				bombla[i] = new JLabel(bomb[1]);
			}else if(i%3==2) {
				bombla[i] = new JLabel(bomb[2]);
			}
			bombla[i].setSize(80,80);
			bombla[i].setLocation(bombx[i],bomby[i]);
			bombla[i].setVisible(false);
			bgpanel.add(bombla[i]);
		}
		
		Image bm1img = bm.getImage();
		Image changedbm1 = bm1img.getScaledInstance(150,150,Image.SCALE_SMOOTH);     
		boom = new ImageIcon(changedbm1);
		
		boomla = new JLabel(boom);
		boomla.setSize(150,150);
		boomla.setLocation(bombx[0],bomby[0]);
		boomla.setVisible(false);
		bgpanel.add(boomla);
		
		
//-----------------------------------------------------------------------------
//---------------------------------바구니----------------------------------------
		Image btimg = bt.getImage();
		Image changedbt = btimg.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		basket = new ImageIcon(changedbt);
		
		
		btlabel = new JLabel(basket);
		btlabel.setSize(80,80);
		btlabel.setLocation(btx, bty);
		bgpanel.add(btlabel);
		
//-----------------------------------------------------------------------------
//---------------------------------랜덤 박스----------------------------------------
		Image rpsimg = randomps.getImage();
		Image changedrps = rpsimg.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		rdpresent = new ImageIcon(changedrps);
		
		Image spsimg = showps.getImage();
		Image changedsps = spsimg.getScaledInstance(80,80,Image.SCALE_SMOOTH);     
		shpresent = new ImageIcon(changedsps);
		
		
		random_presentlabel = new JLabel(rdpresent);
		random_presentlabel.setSize(80,80);
		random_presentlabel.setLocation(psx, psy);
		random_presentlabel.setVisible(false);
		bgpanel.add(random_presentlabel);
		
		
		show_presentlabel = new JLabel(shpresent);
		show_presentlabel.setSize(80,80);
		show_presentlabel.setLocation(random_presentlabel.getX(), random_presentlabel.getY());
		show_presentlabel.setVisible(false);
		bgpanel.add(show_presentlabel);
				
//-----------------------------------------------------------------------------
//-----------------------------스레드 이용 기능--------------------------------------
		
		thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
//--------------------------------------짝수1 라벨--------------------------------------------
					if(even == 0 && chy[even]<800 && evenstatus == false) { //처음에는 홀수 라벨이 아무것도 보이지 않음
						chy[even] +=5;
						evenstatus = true;
					}
					else if(even != 0 && chy[odd1]>350 && evenstatus == false) {
						label[even].setVisible(true);
						evenstatus = true;
						//y좌표에 변화를 줘서 떨어지게 함
						//첫 라벨이 아닌 경우
					}
					else if(evenstatus==true && chy[even]<900 && label[even].isVisible() == true) {
						chy[even]+=5;
					}
					else if(evenstatus == true && label[even].isVisible()==false) {
						chy[even]=800;
						score+=5;
						result = score;
						scorelabel.setText(score+"점");
						if(result % 1000 == 0) {
							Image btimg = bt.getImage();
							Image changedbt = btimg.getScaledInstance(80+size,80+size,Image.SCALE_SMOOTH);     
							basketup = new ImageIcon(changedbt);
							
							btlabel.setIcon(basketup);
							btlabel.setSize(80+size,80+size);
							size+=20;
							bt_touch+=10;
						}
						even+=4;
						evenstatus = false;
						//라벨이 보이지 않으면 y좌표는 더이상 움직이지 않고 멈추도록 함
					}
					else if(chy[even]==900){
						chy[even]=900;
						evenstatus = false;
						label[even].setVisible(false);
						lifelabel[chance].setVisible(false);
						deadlabel[chance].setVisible(true);
						even+=4;
						evenstatus = false;
					}
//--------------------------------------짝수2 라벨--------------------------------------------
                   if(chy[odd]>350 && even1status == false) {
						label[even1].setVisible(true);
						even1status = true;
						//y좌표에 변화를 줘서 떨어지게 함
						//첫 라벨이 아닌 경우
					}
					else if(even1status==true && chy[even1]<900 && label[even1].isVisible() == true) {
						chy[even1]+=5;
					}
					else if(even1status == true && label[even1].isVisible()==false) {
						chy[even1]=800;
						score+=5;
						result = score;
						scorelabel.setText(score+"점");
						if(result % 1000 == 0) {
							Image btimg = bt.getImage();
							Image changedbt = btimg.getScaledInstance(80+size,80+size,Image.SCALE_SMOOTH);     
							basketup = new ImageIcon(changedbt);
							
							btlabel.setIcon(basketup);
							btlabel.setSize(80+size,80+size);
							size+=20;
							bt_touch+=10;
						}
						even1+=4;
						even1status = false;
						//라벨이 보이지 않으면 y좌표는 더이상 움직이지 않고 멈추도록 함
					}
					else if(chy[even1]==900){
						chy[even1]=900;
						label[even1].setVisible(false);
						lifelabel[chance].setVisible(false);
						deadlabel[chance].setVisible(true);
						even1+=4;
						even1status = false;
					}
//--------------------------------------홀수1 라벨--------------------------------------------
					if(label[even].getY()>350 && oddstatus == false) {
						label[odd].setVisible(true);
						oddstatus = true;
					}
					else if(oddstatus==true && chy[odd]<900 && label[odd].isVisible() == true) {
						chy[odd]+=5;
					}
					else if(oddstatus == true && label[odd].isVisible()==false) {
						chy[odd]=800;
						score+=5;
						result = score;
						scorelabel.setText(score+"점");
						if(result % 1000 == 0) {
							Image btimg = bt.getImage();
							Image changedbt = btimg.getScaledInstance(80+size,80+size,Image.SCALE_SMOOTH);     
							basketup = new ImageIcon(changedbt);
							
							btlabel.setIcon(basketup);
							btlabel.setSize(80+size,80+size);
							size+=20;
							bt_touch+=10;
						}
						odd+=4;
						oddstatus = false;
					}
					else if(chy[odd]==900){
						chy[odd]=900;
						label[odd].setVisible(false);
						lifelabel[chance].setVisible(false);
						deadlabel[chance].setVisible(true);
						odd+=4;
						oddstatus = false;
					}
//--------------------------------------홀수2 라벨--------------------------------------------
					if(label[even1].getY()>350 && odd1status == false) {
						label[odd1].setVisible(true);
						odd1status = true;
					}
					else if(odd1status==true && chy[odd1]<900 && label[odd1].isVisible() == true) {
						chy[odd1]+=5;
					}
					else if(odd1status == true && label[odd1].isVisible()==false) {
						chy[odd1]=800;
						score+=5;
						result = score;
						scorelabel.setText(score+"점");
						if(result % 1000 == 0) {
							Image btimg = bt.getImage();
							Image changedbt = btimg.getScaledInstance(80+size,80+size,Image.SCALE_SMOOTH);     
							basketup = new ImageIcon(changedbt);
							
							btlabel.setIcon(basketup);
							btlabel.setSize(80+size,80+size);
							size+=20;
							bt_touch+=10;
						}
						odd1+=4;
						odd1status = false;
					}
					else if(chy[odd1]==900){
						chy[odd1]=900;
						label[odd1].setVisible(false);
						lifelabel[chance].setVisible(false);
						deadlabel[chance].setVisible(true);
						odd1+=4;
						odd1status = false;
					}
//-----------------------------------폭탄 캐릭터 라벨----------------------------------------

					if(boomstatus == true && boomnum == 5) {
						boomla.setVisible(false);
						boomstatus = false;
						boomnum = 0;
					}
					else if(boomstatus == true){
						boomnum++;
					}

					if(even == bombstart && bombstatus == false) {
  						if(chy[even]>350) {
  							bombla[bombcnt].setVisible(true);
  							bomby[bombcnt] = 200;
  							bombstatus = true;
  							clip1.setFramePosition(0);
  							clip1.stop();
  						}
					}
					else if(odd == bombstart && bombstatus == false) {
  						if(chy[odd]>350) {
  							bombla[bombcnt].setVisible(true);
  							bomby[bombcnt] = 200;
  							bombstatus = true;
  							clip1.setFramePosition(0);
  							clip1.stop();
  						}
					}
					else if(even1 == bombstart && bombstatus == false) {
  						if(chy[even1]>350) {
  							bombla[bombcnt].setVisible(true);
  							bomby[bombcnt] = 200;
  							bombstatus = true;
  							clip1.setFramePosition(0);
  							clip1.stop();
  							
  						}
						
					}
					else if(odd1 == bombstart && bombstatus == false) {
  						if(chy[odd1]>350) {
  							bombla[bombcnt].setVisible(true);
  							bomby[bombcnt] = 200;
  							bombstatus = true;
  							clip1.setFramePosition(0);
  							clip1.stop();
  						}
					}
					else if(bombla[bombcnt].isVisible() == false && bombstatus == true) {
						bomby[bombcnt]=800;
						score-=10;
						result = score;
						scorelabel.setText(score+"점");
						bombstatus = false;
						
						lifelabel[chance].setVisible(false);
						deadlabel[chance].setVisible(true);
						boomla.setLocation(bombla[bombcnt].getX(), bombla[bombcnt].getY()-45);
						boomla.setVisible(true);
						boomstatus = true;
						bombcnt++;
						clip1.start();
					}
					
					else if(bombla[bombcnt].isVisible() == true && bombstatus == true &&
							bomby[bombcnt]>=900) {
						bomby[bombcnt]=900;
						bombla[bombcnt].setVisible(false);
						bombstatus = false;
						boomla.setLocation(bombla[bombcnt].getX(), bombla[bombcnt].getY()-45);
						boomla.setVisible(true);
						boomstatus = true;
						bombcnt++;
						clip1.start();
					}
  					
					if(bombla[bombcnt].isVisible() == true && bombstatus == true
							&& bomby[bombcnt]<900) {
						bomby[bombcnt]+=5;
					}
					
//---------------------------------------------랜덤 박스 라벨---------------------------------------------------
					
					if(shpresentstatus == true && shownum == 7) {
						show_presentlabel.setVisible(false);
						shpresentstatus = false;
						shownum = 0;
					}
					else if(shpresentstatus == true){
						shownum++;
					}
					
           		    if(even % 60 == 0 && even != 0 && presentstatus == false) {
           		    	if(chy[even]>500) {
           		    		presentscore = (int)(Math.random()*1000)+1;
                		    psx = (int)(Math.random()*1575)+1;
           		    		random_presentlabel.setVisible(true);
  							psy = 200;
  							presentstatus = true;
           		    	}
        		    }
           		    
					else if(random_presentlabel.isVisible() == false && presentstatus == true) {
						
						psy=800;
						
						if((presentscore % 4) == 0) { // presentscore에 따라 점수 다름
							System.out.println(presentscore);
							score+=50;
						}
						else if((presentscore % 4) == 1){
							System.out.println(presentscore);
							score-=25;
						}
						else if((presentscore % 4) == 2) {
							System.out.println(presentscore);
							score+=25;
						}
						else {
							System.out.println(presentscore);
							score-=50;
						}
						result = score;
						scorelabel.setText(score+"점");
						presentstatus = false;
						
						show_presentlabel.setLocation(random_presentlabel.getX(), random_presentlabel.getY());
						show_presentlabel.setVisible(true);
						shpresentstatus = true;
					}
					
					else if(random_presentlabel.isVisible() == true && presentstatus == true &&
							psy>=900) {
						psy=900;
						presentstatus = false;
						random_presentlabel.setVisible(false);
					}
  					
					if(random_presentlabel.isVisible() == true && presentstatus == true
							&& psy<900) {
						psy+=10;
					}
//---------------------------------------------------------------------------------------------------------
					
  					bombla[bombcnt].setLocation(bombx[bombcnt],bomby[bombcnt]);
  					random_presentlabel.setLocation(psx, psy);
					
					label[even].setLocation(chx[even],chy[even]);
					label[odd].setLocation(chx[odd],chy[odd]);
					label[even1].setLocation(chx[even1],chy[even1]);
					label[odd1].setLocation(chx[odd1],chy[odd1]);
					
					if(lifelabel[0].isVisible() == false && deadlabel[0].isVisible() == true) {
						chance = 1;
						if(lifelabel[1].isVisible() == false && deadlabel[1].isVisible() == true) {
							chance = 2;
							if(lifelabel[2].isVisible() == false && deadlabel[2].isVisible() == true) {
								chance = 3;
							}
						}
					}
					if(chance == 3)  {
						thStatus=false;
						//3번의 기회(생명)을 모두 다 쓴경우 y좌표는 멈추고 게임이 끝나도록 설정
					}
					
					
					if(thStatus == false) {
					  try {
						  String quary = "SELECT * FROM member WHERE userid='"+id+"'";
						  conn = DBconnection.getConnection();
						  stmt = conn.createStatement();
						  rs = stmt.executeQuery(quary);
						
						  while(rs.next()) {
							originalscore = rs.getInt("score");
							if(originalscore >= result) {
								break;
							}
							else {
								  try {
									  String quary1 = "UPDATE member SET score="+result+" WHERE userid='"+id+"'";
									  conn = DBconnection.getConnection();
									  stmt = conn.createStatement();
									  stmt.executeUpdate(quary1);
									  
									  rs.close();
									  
								  }catch(SQLException esql) {
								  	  esql.printStackTrace();
								  }
							}
						  }
						  
						  rs.close();
						  
					  }catch(SQLException esql) {
					  	  esql.printStackTrace();
					  }
					  
						new GameOver();
						dispose();
						clip2.stop();
						thread1.stop();
					}
					
	                  try
	                  {
	                	  //속도 주기
	                	 for(int i=0;i<=1000;i++) {
	                		 if(even1<=10 && timecnt == 1) {
	                			 time = 50;
	                			 velocitylabel.setText(Integer.toString(100-time)+"km/s");
	                			 timecnt++;
	                			 if(even == 0) {
	                				 bombstart = (int)(Math.random()*10)+1;
	                			 }
	                		 }
	                		 else if(even == 20 && timecnt == 2) {
	                			 time = 30;
	                			 velocitylabel.setText(Integer.toString(100-time)+"km/s");
	                			 timecnt++;
	                			 bombstart = (int)(Math.random()*10)+10;
	                		 }
	                		 else if(even == 10*cnt || even == 10*cnt+2) {
	                			 time = (int)(Math.random()*count)+10;
	                			 velocitylabel.setText(Integer.toString(100-time)+"km/s");
	                			 bombstart = (int)(Math.random()*10)+10*cnt;
	                			 cnt++;
	                		 }
 	                	 }
	                	 
	                     Thread.sleep(time);
	                     repaint();
	                  }
	                  catch(Exception e) {}
				}
			}
			
		});
		thread1.start();
		
		c.add(bgpanel);
		c.add(infopanel);
		loadAudio1(boomsound); // 폭탄 터지는 소리
		loadAudio2(skysound); // 밤하늘(배경)
		setSize(1850,1000);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
//---------------------------------오디오(폭발)----------------------------------------
	private void loadAudio1(String pathName1) {
		try {
			clip1 = AudioSystem.getClip();
			File audioFile1 = new File(pathName1);
			AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile1);
			clip1.open(audioStream1);
			FloatControl gainControl1 = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl1.setValue(5.0f);
			
		}catch (LineUnavailableException e) { e.printStackTrace(); }
		 catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
		 catch (IOException e) { e.printStackTrace(); }
	}
	
	
//---------------------------------오디오(밤하늘)----------------------------------------
	void loadAudio2(String pathName2) {
		try {
			clip2 = AudioSystem.getClip();
			File audioFile2 = new File(pathName2);
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
			clip2.open(audioStream2);
			FloatControl gainControl2 = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl2.setValue(-10.0f);
			clip2.start();
			
		}catch (LineUnavailableException e) { e.printStackTrace(); }
		 catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
		 catch (IOException e) { e.printStackTrace(); }
	}
	
//---------------------------------바구니 움직이기----------------------------------
	class MyMouseMotionListener extends MouseMotionAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			btx = e.getX();
			bty = e.getY();
			if(bty<550) {
				bty=550;
			}else if(bty>800) {
				bty=800;
			}
			
			if(btx>1575) {
				btx=1575;
			}
			
			btlabel.setLocation(btx, bty);
			
			for(int i=0;i<label.length && odd<label.length && even<label.length &&
					odd1<label.length && even1<label.length && bombcnt < bombla.length;i++) {
				
			    if((label[odd].getX()-25-bt_touch)<=btlabel.getX() && (label[odd].getX()+25+bt_touch)>=btlabel.getX() &&
						(label[odd].getY()-25-bt_touch)<=btlabel.getY() && (label[odd].getY()+25+bt_touch)>=btlabel.getY()) {
							label[odd].setVisible(false);
				}
					    
			    else if((label[even].getX()-25-bt_touch)<=btlabel.getX() && (label[even].getX()+25+bt_touch)>=btlabel.getX() &&
						(label[even].getY()-25-bt_touch)<=btlabel.getY() && (label[even].getY()+25+bt_touch)>=btlabel.getY()) {
							label[even].setVisible(false);
				 }
			    else if((label[odd1].getX()-25-bt_touch)<=btlabel.getX() && (label[odd1].getX()+25+bt_touch)>=btlabel.getX() &&
						(label[odd1].getY()-25-bt_touch)<=btlabel.getY() && (label[odd1].getY()+25+bt_touch)>=btlabel.getY()) {
							label[odd1].setVisible(false);
				}
					    
			    else if((label[even1].getX()-25-bt_touch)<=btlabel.getX() && (label[even1].getX()+25+bt_touch)>=btlabel.getX() &&
						(label[even1].getY()-25-bt_touch)<=btlabel.getY() && (label[even1].getY()+25+bt_touch)>=btlabel.getY()) {
							label[even1].setVisible(false);
				 }
			    else if((bombla[bombcnt].getX()-25-bt_touch)<=btlabel.getX() && (bombla[bombcnt].getX()+25+bt_touch)>=btlabel.getX() &&
						(bombla[bombcnt].getY()-25-bt_touch)<=btlabel.getY() && (bombla[bombcnt].getY()+25+bt_touch)>=btlabel.getY()) {
			    	bombla[bombcnt].setVisible(false);
			    }
			    else if((random_presentlabel.getX()-45)<=btlabel.getX() && (random_presentlabel.getX()+45)>=btlabel.getX() &&
						(random_presentlabel.getY()-45)<=btlabel.getY() && (random_presentlabel.getY()+45)>=btlabel.getY()) {
			    	random_presentlabel.setVisible(false);
			    }
			}
			
		}
		
	}

}
