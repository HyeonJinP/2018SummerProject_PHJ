import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import DB.DBconnection;

public class GameMessageSend extends JFrame{
	String id = GameLogin.userId;
	String nickname = GameMessage.nickname;
	
	JTextField sender, receiver, title;
	JTextArea content;
	JLabel snick, rnick, ctext, ttext;
	JButton sendbtn;
	int success;
	boolean okjoin = false;
	Container c;
//-----------------------------------���� DB ���� --------------------------------------------
	Connection conn = DBconnection.getConnection();
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
//-----------------------------------------------------------------------------------------
	
	public GameMessageSend() {
		setTitle("Ż���ϴ� īī�� ����� ���϶�");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,204,204));
		
//--------------------------------------��----------------------------------------------
		snick = new JLabel("������ ���");
		snick.setSize(115,30);
		snick.setLocation(25,35);
		snick.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		snick.setVerticalAlignment(SwingConstants.CENTER);
		snick.setHorizontalAlignment(SwingConstants.RIGHT);
		
		sender = new JTextField(nickname);
		sender.setSize(150,30);
		sender.setLocation(150,35);
		sender.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		sender.setHorizontalAlignment(SwingConstants.CENTER);
		
		rnick = new JLabel("�޴� ���");
		rnick.setSize(115,30);
		rnick.setLocation(25,80);
		rnick.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		rnick.setVerticalAlignment(SwingConstants.CENTER);
		rnick.setHorizontalAlignment(SwingConstants.RIGHT);
		
		receiver = new JTextField();
		receiver.setSize(150,30);
		receiver.setLocation(150,80);
		receiver.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		receiver.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		ttext = new JLabel("����");
		ttext.setSize(115,30);
		ttext.setLocation(25,125);
		ttext.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		ttext.setVerticalAlignment(SwingConstants.CENTER);
		ttext.setHorizontalAlignment(SwingConstants.RIGHT);
		
		title = new JTextField();
		title.setSize(200,30);
		title.setLocation(150,125);
		title.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		
		ctext = new JLabel("����");
		ctext.setSize(115,30);
		ctext.setLocation(25,150);
		ctext.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		ctext.setVerticalAlignment(SwingConstants.CENTER);
		ctext.setHorizontalAlignment(SwingConstants.LEFT);
		
		content = new JTextArea();
		content.setSize(440,225);
		content.setLocation(25,190);
		content.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		JScrollPane sc = new JScrollPane(content);
		
		sendbtn = new JButton("������");
		sendbtn.setSize(100,30);
		sendbtn.setLocation(365,125);
		sendbtn.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
		sendbtn.setBackground(Color.LIGHT_GRAY);
		sendbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String quary = "INSERT INTO message(sender, receiver, title, content) VALUES(?,?,?,?)";
					pstmt = conn.prepareStatement(quary);
					
					pstmt.setString(1, nickname);
					pstmt.setString(2, receiver.getText());
					pstmt.setString(3, title.getText());
					pstmt.setString(4, content.getText());
					
				    
				    if(receiver.getText().isEmpty()==true || title.getText().isEmpty()==true ||
							content.getText().isEmpty()==true) {
						JOptionPane.showMessageDialog(null,"��� �Է����ּ���.","MESSAGE",JOptionPane.ERROR_MESSAGE);
						success = 0;
					} else {
						success = pstmt.executeUpdate();
	                }
				    
			           if(success > 0) {
			               okjoin = true;
			           }
			           
				       if(okjoin){
				            
				    	   JOptionPane.showMessageDialog(null,"�޽����� ���۵Ǿ����ϴ�.","MESSAGE",JOptionPane.INFORMATION_MESSAGE);
							dispose();
				       }
					
					
				}catch(SQLException esql) {
				     esql.printStackTrace();
			    }
			}
			
		});
//--------------------------------------------------------------------------------------
		
		c.add(snick);
		c.add(sender);
		c.add(rnick);
		c.add(receiver);
		c.add(ttext);
		c.add(title);
		c.add(ctext);
		c.add(content);
		c.add(sendbtn);
		
		setVisible(true);
		setSize(500,500);
		setResizable(false);
		setLocationRelativeTo(null);
		
	}

}
