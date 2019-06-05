import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import DB.DBconnection;

public class MemberJoin extends JFrame{
	Container c;
	String nickname, userid, userpw;
	int score = 0;
	int rank;
//---------------------------게임 DB 연동 ---------------------------------
	Connection conn;
	Statement stmt = null; // sql문 실행하는 클래스
	PreparedStatement pstmt = null;
	ResultSet rs; // sql문 실행 결과 얻어오는 클래스
	ResultSetMetaData rsmd;
	int lastrank;
	int success = 0;
	boolean okjoin = false;
//----------------------게임 로그인 화면 인터페이스 -----------------------------
	CenterPanel cpanel;
	logoPanel lpanel;
	JLabel idlabel, pwlabel, nicklabel;
	JTextField idtext, nicktext;
	JPasswordField pwtext;
	JButton checkbtn, rewritebtn, okbtn, cancelbtn;
	ImageIcon f1 = new ImageIcon("images/friend1.png");
	ImageIcon f2 = new ImageIcon("images/friend2.png");
	ImageIcon f1logo, f2logo;
	boolean check = false;
	
	public MemberJoin() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c=getContentPane();
		c.setLayout(new BorderLayout());
		
		
		lpanel = new logoPanel();
		c.add(lpanel, BorderLayout.NORTH);
		cpanel = new CenterPanel();
		c.add(cpanel, BorderLayout.CENTER);
		
		setSize(450,400);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null); //프레임 화면 중앙에서 띄우기
	}
	
	class CenterPanel extends JPanel {
		
		public CenterPanel() {
			setLayout(null);
			setBackground(new Color(240,248,255));
			
			nicklabel = new JLabel("별명"); //별명 라벨
			nicklabel.setSize(50,25);
			nicklabel.setLocation(70,35);
			nicklabel.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			nicklabel.setBackground(new Color(240,248,255));
			nicklabel.setOpaque(true);
			//JLabel은 기본적으로 투명함
			//불투명도를 true로 설정시, 배경색을 넣을 수 있음.
			add(nicklabel);
			
			nicktext = new JTextField(20);
			nicktext.setSize(140,30);
			nicktext.setLocation(140,30);
			nicktext.setFont(new Font("a찐빵M", Font.PLAIN, 22));
			add(nicktext);
			
			idlabel = new JLabel("ID"); //ID 라벨
			idlabel.setSize(30,25);
			idlabel.setLocation(90,85);
			idlabel.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			idlabel.setBackground(new Color(240,248,255));
			idlabel.setOpaque(true);
			add(idlabel);
			
			idtext = new JTextField(20);
			idtext.setSize(140,30);
			idtext.setLocation(140,85);
			idtext.setFont(new Font("a찐빵M", Font.PLAIN, 22));
			add(idtext);
			
			pwlabel = new JLabel("비밀번호"); //비밀번호 라벨
			pwlabel.setSize(100,25);
			pwlabel.setLocation(20,135);
			pwlabel.setFont(new Font("a시월구일2", Font.PLAIN, 25));
			pwlabel.setBackground(new Color(240,248,255));
			pwlabel.setOpaque(true);
			add(pwlabel);
			
			pwtext = new JPasswordField(20);
			pwtext.setSize(140,30);
			pwtext.setLocation(140,135);
			pwtext.setFont(new Font("a찐빵M", Font.PLAIN, 22));
			add(pwtext);
			
			checkbtn = new JButton("중복확인");
			checkbtn.setSize(125,30);
			checkbtn.setLocation(300,85);
			checkbtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
			checkbtn.setBackground(Color.lightGray);
			add(checkbtn);
			checkbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					try {
						
						String quary = "SELECT * FROM member WHERE userid='"+idtext.getText()+"'";
						conn = DBconnection.getConnection();
						stmt = conn.createStatement();
						rs = stmt.executeQuery(quary);
						//executeQuery()메소드는 데이터 검색 / executeUpdate()메소드는 데이터 변경(추가,검색,삭제)
						
						if(rs.next() == true) {
							//존재 여부 확인
							JOptionPane.showMessageDialog(null,"사용 불가능한 아이디입니다.","Check",JOptionPane.WARNING_MESSAGE);
						}
						else if(idtext.getText().isEmpty() == true) {
							JOptionPane.showMessageDialog(null,"아이디를 입력해 주세요.","Check",JOptionPane.WARNING_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null,"사용 가능한 아이디입니다.","Check",JOptionPane.INFORMATION_MESSAGE);
							check = true;
						}
						
						rs.close();
						
					} catch(SQLException esql) {
						esql.printStackTrace();
					}
				}
				
			});
			
			rewritebtn = new JButton("다시쓰기");
			rewritebtn.setSize(125,30);
			rewritebtn.setLocation(40,200);
			rewritebtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
			rewritebtn.setBackground(Color.lightGray);
			add(rewritebtn);
			rewritebtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					nicktext.setText("");
					idtext.setText("");
					pwtext.setText("");
				}
				
			});
			
			okbtn = new JButton("확인");
			okbtn.setSize(100,30);
			okbtn.setLocation(180,200);
			okbtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
			okbtn.setBackground(Color.lightGray);
			add(okbtn);
			okbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					try {
						//가입시 제일 마지막 등수를 부여하기 위해 count(*)함수를 부여한다.
						
						String quary = "SELECT count(*) as cnt FROM member";
						conn = DBconnection.getConnection();
						stmt = conn.createStatement();
						rs = stmt.executeQuery(quary);
						//executeQuery()메소드는 데이터 검색 / executeUpdate()메소드는 데이터 변경(추가,검색,삭제)
						
						rsmd = rs.getMetaData();
						lastrank = rsmd.getColumnCount()+1;

						System.out.println("마지막 등수:"+ lastrank);
						
						rs.close();
						
					} catch(SQLException esql) {
						esql.printStackTrace();
					}
					
					try {
						String quary = "INSERT INTO member(nickname, userid, userpw, score) VALUES(?,?,?,?)";
					    conn = DBconnection.getConnection();
					    pstmt = conn.prepareStatement(quary);
					    
					    pstmt.setString(1, nicktext.getText());
					    pstmt.setString(2, idtext.getText());
					    pstmt.setString(3, pwtext.getText());
					    pstmt.setInt(4, 0);
					    
					    if(nicktext.getText().isEmpty()==true || idtext.getText().isEmpty()==true ||
								pwtext.getText().isEmpty()==true) {
							JOptionPane.showMessageDialog(null,"모두 입력해주세요.","JOIN",JOptionPane.ERROR_MESSAGE);
							success = 0;
							
						} else if(check==false) {
							JOptionPane.showMessageDialog(null,"아이디 중복확인을 해 주십시요","Check",JOptionPane.WARNING_MESSAGE);
							success = 0;
							
						} else {
				            success = pstmt.executeUpdate();
		                }
					    
				           if(success > 0) {
				               okjoin = true;
				           }
				           
					       if(okjoin){
					            
					    	   JOptionPane.showMessageDialog(null,"가입이 완료되었습니다.","JOIN",JOptionPane.INFORMATION_MESSAGE);
								dispose();
					       }
					    
					       rs.close();
					       
					} catch(SQLException esql) {
						esql.printStackTrace();
					}
				}
				
			});
			
			cancelbtn = new JButton("취소");
			cancelbtn.setSize(100,30);
			cancelbtn.setLocation(295,200);
			cancelbtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
			cancelbtn.setBackground(Color.lightGray);
			add(cancelbtn);
			cancelbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
				
			});
			
		}
	}
	
	class logoPanel extends JPanel {
		
		public logoPanel() {
			setLayout(new BorderLayout());
			setBackground(new Color(240,248,255));
			
			JLabel charlabel = new JLabel("회 원 가 입");
			charlabel.setVerticalAlignment(SwingConstants.CENTER);
			charlabel.setHorizontalAlignment(SwingConstants.CENTER);
			//글씨 가운데 정렬
			charlabel.setFont(new Font("a가로수", Font.PLAIN, 45));
			
			Image originImg1 = f1.getImage();
			Image changedImg1 = originImg1.getScaledInstance(90,90,Image.SCALE_SMOOTH);     
			f1logo = new ImageIcon(changedImg1); // 로고 크기 조정
			
			Image originImg2 = f2.getImage();
			Image changedImg2 = originImg2.getScaledInstance(90,90,Image.SCALE_SMOOTH);    
			f2logo = new ImageIcon(changedImg2); // 로고 크기 조정
			
			JLabel imglabel1 = new JLabel(f1logo);
			JLabel imglabel2 = new JLabel(f2logo);
			
			add(imglabel1, BorderLayout.WEST);
			add(imglabel2, BorderLayout.EAST);
			add(charlabel, BorderLayout.CENTER);
			
		}
	}

}
