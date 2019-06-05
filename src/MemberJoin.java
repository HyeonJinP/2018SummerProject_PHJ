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
//---------------------------���� DB ���� ---------------------------------
	Connection conn;
	Statement stmt = null; // sql�� �����ϴ� Ŭ����
	PreparedStatement pstmt = null;
	ResultSet rs; // sql�� ���� ��� ������ Ŭ����
	ResultSetMetaData rsmd;
	int lastrank;
	int success = 0;
	boolean okjoin = false;
//----------------------���� �α��� ȭ�� �������̽� -----------------------------
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
		setTitle("Ż���ϴ� īī�� ����� ���϶�");
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
		setLocationRelativeTo(null); //������ ȭ�� �߾ӿ��� ����
	}
	
	class CenterPanel extends JPanel {
		
		public CenterPanel() {
			setLayout(null);
			setBackground(new Color(240,248,255));
			
			nicklabel = new JLabel("����"); //���� ��
			nicklabel.setSize(50,25);
			nicklabel.setLocation(70,35);
			nicklabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
			nicklabel.setBackground(new Color(240,248,255));
			nicklabel.setOpaque(true);
			//JLabel�� �⺻������ ������
			//�������� true�� ������, ������ ���� �� ����.
			add(nicklabel);
			
			nicktext = new JTextField(20);
			nicktext.setSize(140,30);
			nicktext.setLocation(140,30);
			nicktext.setFont(new Font("a��M", Font.PLAIN, 22));
			add(nicktext);
			
			idlabel = new JLabel("ID"); //ID ��
			idlabel.setSize(30,25);
			idlabel.setLocation(90,85);
			idlabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
			idlabel.setBackground(new Color(240,248,255));
			idlabel.setOpaque(true);
			add(idlabel);
			
			idtext = new JTextField(20);
			idtext.setSize(140,30);
			idtext.setLocation(140,85);
			idtext.setFont(new Font("a��M", Font.PLAIN, 22));
			add(idtext);
			
			pwlabel = new JLabel("��й�ȣ"); //��й�ȣ ��
			pwlabel.setSize(100,25);
			pwlabel.setLocation(20,135);
			pwlabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
			pwlabel.setBackground(new Color(240,248,255));
			pwlabel.setOpaque(true);
			add(pwlabel);
			
			pwtext = new JPasswordField(20);
			pwtext.setSize(140,30);
			pwtext.setLocation(140,135);
			pwtext.setFont(new Font("a��M", Font.PLAIN, 22));
			add(pwtext);
			
			checkbtn = new JButton("�ߺ�Ȯ��");
			checkbtn.setSize(125,30);
			checkbtn.setLocation(300,85);
			checkbtn.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
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
						//executeQuery()�޼ҵ�� ������ �˻� / executeUpdate()�޼ҵ�� ������ ����(�߰�,�˻�,����)
						
						if(rs.next() == true) {
							//���� ���� Ȯ��
							JOptionPane.showMessageDialog(null,"��� �Ұ����� ���̵��Դϴ�.","Check",JOptionPane.WARNING_MESSAGE);
						}
						else if(idtext.getText().isEmpty() == true) {
							JOptionPane.showMessageDialog(null,"���̵� �Է��� �ּ���.","Check",JOptionPane.WARNING_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null,"��� ������ ���̵��Դϴ�.","Check",JOptionPane.INFORMATION_MESSAGE);
							check = true;
						}
						
						rs.close();
						
					} catch(SQLException esql) {
						esql.printStackTrace();
					}
				}
				
			});
			
			rewritebtn = new JButton("�ٽþ���");
			rewritebtn.setSize(125,30);
			rewritebtn.setLocation(40,200);
			rewritebtn.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
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
			
			okbtn = new JButton("Ȯ��");
			okbtn.setSize(100,30);
			okbtn.setLocation(180,200);
			okbtn.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
			okbtn.setBackground(Color.lightGray);
			add(okbtn);
			okbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					try {
						//���Խ� ���� ������ ����� �ο��ϱ� ���� count(*)�Լ��� �ο��Ѵ�.
						
						String quary = "SELECT count(*) as cnt FROM member";
						conn = DBconnection.getConnection();
						stmt = conn.createStatement();
						rs = stmt.executeQuery(quary);
						//executeQuery()�޼ҵ�� ������ �˻� / executeUpdate()�޼ҵ�� ������ ����(�߰�,�˻�,����)
						
						rsmd = rs.getMetaData();
						lastrank = rsmd.getColumnCount()+1;

						System.out.println("������ ���:"+ lastrank);
						
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
							JOptionPane.showMessageDialog(null,"��� �Է����ּ���.","JOIN",JOptionPane.ERROR_MESSAGE);
							success = 0;
							
						} else if(check==false) {
							JOptionPane.showMessageDialog(null,"���̵� �ߺ�Ȯ���� �� �ֽʽÿ�","Check",JOptionPane.WARNING_MESSAGE);
							success = 0;
							
						} else {
				            success = pstmt.executeUpdate();
		                }
					    
				           if(success > 0) {
				               okjoin = true;
				           }
				           
					       if(okjoin){
					            
					    	   JOptionPane.showMessageDialog(null,"������ �Ϸ�Ǿ����ϴ�.","JOIN",JOptionPane.INFORMATION_MESSAGE);
								dispose();
					       }
					    
					       rs.close();
					       
					} catch(SQLException esql) {
						esql.printStackTrace();
					}
				}
				
			});
			
			cancelbtn = new JButton("���");
			cancelbtn.setSize(100,30);
			cancelbtn.setLocation(295,200);
			cancelbtn.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
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
			
			JLabel charlabel = new JLabel("ȸ �� �� ��");
			charlabel.setVerticalAlignment(SwingConstants.CENTER);
			charlabel.setHorizontalAlignment(SwingConstants.CENTER);
			//�۾� ��� ����
			charlabel.setFont(new Font("a���μ�", Font.PLAIN, 45));
			
			Image originImg1 = f1.getImage();
			Image changedImg1 = originImg1.getScaledInstance(90,90,Image.SCALE_SMOOTH);     
			f1logo = new ImageIcon(changedImg1); // �ΰ� ũ�� ����
			
			Image originImg2 = f2.getImage();
			Image changedImg2 = originImg2.getScaledInstance(90,90,Image.SCALE_SMOOTH);    
			f2logo = new ImageIcon(changedImg2); // �ΰ� ũ�� ����
			
			JLabel imglabel1 = new JLabel(f1logo);
			JLabel imglabel2 = new JLabel(f2logo);
			
			add(imglabel1, BorderLayout.WEST);
			add(imglabel2, BorderLayout.EAST);
			add(charlabel, BorderLayout.CENTER);
			
		}
	}

}
