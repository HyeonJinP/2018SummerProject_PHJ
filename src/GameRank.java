import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.sql.*;
import DB.DBconnection;

import javax.swing.table.*;

public class GameRank extends JFrame{
	Container c;
	String id = GameLogin.userId;
	JTable ranktable;
	JPanel monthpanel;
	JLabel monthlabel;
	Calendar cld;
	DefaultTableModel model;
//---------------------------���� DB ���� --------------------------------------------
	Connection conn; // ���� db
	Statement stmt = null; //SQL���� �����ϱ� ���ؼ��� Statement Ŭ������ �̿�
	PreparedStatement pstmt = null;
	ResultSet rs;
	
	public GameRank() {
		setTitle("Ż���ϴ� īī�� ����� ���϶�");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		
		monthpanel = new JPanel();
		monthpanel.setLayout(new FlowLayout());
		monthpanel.setBackground(Color.PINK);
		
		cld = Calendar.getInstance();
		
		monthlabel = new JLabel(cld.get(Calendar.MONTH)+1+"�� ����");
		monthlabel.setBackground(Color.PINK);
		monthlabel.setOpaque(true);
		monthlabel.setFont(new Font("a�ÿ�����2", Font.PLAIN, 25));
		monthpanel.add(monthlabel);
		
		
        String title[] = {"����", "�г���", "����"};
        model = new DefaultTableModel(title,0);
        ranktable = new JTable(model);
        JScrollPane scroll = new JScrollPane(ranktable);
        
        
        
        ranktable.setGridColor(Color.BLACK);
        ranktable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); // ���ϼ���
        String arr[] = {"����", "�г���", "����"};
        model.addRow(arr);
        
        try {
        	String quary = "SELECT cnt, score, nickname, (SELECT COUNT(*) + 1 FROM member WHERE score > b.score) AS rank FROM member AS b ORDER BY rank ASC";
        	conn = DBconnection.getConnection();
        	pstmt = conn.prepareStatement(quary);
        	rs = pstmt.executeQuery();
        	
            DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
            celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
            //JLabel.CENTER�� ��� ����, JLabel.RIGHT�� ������ ����
            
            ranktable.getColumnModel().getColumn(0).setPreferredWidth(80);
            ranktable.getColumnModel().getColumn(0).setCellRenderer(celAlignCenter); //��� ����
            ranktable.getColumnModel().getColumn(1).setPreferredWidth(160);
            ranktable.getColumnModel().getColumn(1).setCellRenderer(celAlignCenter);
            ranktable.getColumnModel().getColumn(2).setPreferredWidth(160);
            ranktable.getColumnModel().getColumn(2).setCellRenderer(celAlignCenter);
            ranktable.setFont(new Font("a�ÿ�����2", Font.PLAIN, 22));
            ranktable.setEnabled(false); // ���� �Ұ�
            ranktable.setRowHeight(35);
            
            while(rs.next()) {
            	arr[0] = rs.getString(4);
            	arr[1] = rs.getString(3);
            	arr[2] = rs.getString(2)+"��";
            	model.addRow(arr);
            }
            
            rs.close();
        	
        }catch(SQLException esql) {
        	esql.printStackTrace();
        }

        setBounds(40,40,600,600);
        add(scroll);
        c.add(ranktable, BorderLayout.CENTER);
        c.add(monthpanel, BorderLayout.NORTH);
        
		setSize(500,600);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
