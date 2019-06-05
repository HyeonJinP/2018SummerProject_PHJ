import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DB.DBconnection;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameMessage extends JFrame{
		Container c;
		String id = GameLogin.userId;
		static String nickname;
		static String mtitle, msender;
		
		JTable messagetable;
		DefaultTableModel model;
		JPanel btnpanel;
		JButton sendbtn;
//-----------------------------------게임 DB 연동 --------------------------------------------
		Connection conn;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		
	public GameMessage() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
			
//-------------------------------------메세지함------------------------------------------
	    String title[] = {"보낸이", "받는이", "제목"};
	    model = new DefaultTableModel(title,0);
	    messagetable = new JTable(model);
	    JScrollPane scroll = new JScrollPane(messagetable);
	        
	        
	        
	    messagetable.setGridColor(Color.BLACK);
	    messagetable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); // 단일선택
	    String arr[] = {"보낸이", "받는이", "제목"};
	    model.addRow(arr);
	    
	    try {
	    	String quary = "SELECT * FROM member where userid='"+id+"'";
	        conn = DBconnection.getConnection();
	        pstmt = conn.prepareStatement(quary);
	        rs = pstmt.executeQuery();
	        
	        if(rs.next() == true) {
	        	nickname = rs.getString(1);
	        }
	        rs.close();
	        
	    }catch(SQLException esql) {
		     esql.printStackTrace();
		    }
	        
	    try {
	        String quary = "SELECT * FROM message where receiver='"+nickname+"'";
	        conn = DBconnection.getConnection();
	        pstmt = conn.prepareStatement(quary);
	        rs = pstmt.executeQuery();
	        	
	        DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	        celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
	        //JLabel.CENTER은 가운데 정렬, JLabel.RIGHT은 오른쪽 정렬
	            
            messagetable.getColumnModel().getColumn(0).setPreferredWidth(80);
            messagetable.getColumnModel().getColumn(0).setCellRenderer(celAlignCenter); //가운데 정렬
            messagetable.getColumnModel().getColumn(1).setPreferredWidth(80);
            messagetable.getColumnModel().getColumn(1).setCellRenderer(celAlignCenter);
            messagetable.getColumnModel().getColumn(2).setPreferredWidth(240);
            messagetable.getColumnModel().getColumn(2).setCellRenderer(celAlignCenter);
            messagetable.setFont(new Font("a시월구일2", Font.PLAIN, 22));
            messagetable.setRowHeight(35);
	            
	        while(rs.next()) {
	            arr[0] = rs.getString(2);
	            arr[1] = rs.getString(3);
	            arr[2] = rs.getString(4);
	            model.addRow(arr);
	        }
	            
	        rs.close();
	        	
	    }catch(SQLException esql) {
	     esql.printStackTrace();
	    }
	    messagetable.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
		        int row = messagetable.getSelectedRow();
		        if(row==-1) {
		           return;
		        }
		        else {
		           try {
		                String quary = "SELECT * FROM message WHERE sender='"+messagetable.getValueAt(row, 0)+"' AND title='"+messagetable.getValueAt(row, 2)+"'";
				           conn = DBconnection.getConnection();
				           pstmt = conn.prepareStatement(quary);
			               rs = pstmt.executeQuery();	
			               
			               
			                if (rs.next() == true) {
			                	
					            mtitle = rs.getString("title");
					            msender = rs.getString("sender");
					            
			                	new GameMessageGet();
			                }

		           }
		           catch(Exception ee){
		               ee.printStackTrace();
		                       }
		      	}
			}
	    	
	    });
	        

	    setBounds(40,40,600,600);
	    add(scroll);
//------------------------------------버튼 기능-----------------------------------
	    btnpanel = new JPanel();
	    btnpanel.setLayout(new FlowLayout());
	    btnpanel.setBackground(Color.WHITE);
	    
	    sendbtn = new JButton("보내기");
	    sendbtn.setFont(new Font("a시월구일2", Font.PLAIN, 22));
	    sendbtn.setBackground(Color.lightGray);
	    sendbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameMessageSend();
			}
	    	
	    });
	    
	    btnpanel.add(sendbtn);
	    
	    
	    
	    
	    c.add(messagetable, BorderLayout.CENTER);
	    c.add(btnpanel, BorderLayout.NORTH);
	        
		setSize(500,600);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
