import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameMethod extends JFrame{
	Container c;
	
	ImageIcon leftway = new ImageIcon("images/leftway.png");
	ImageIcon rightway = new ImageIcon("images/rightway.png");
	ImageIcon m1 = new ImageIcon("images/method1.png");
	ImageIcon m2 = new ImageIcon("images/method2.png");
	ImageIcon m3 = new ImageIcon("images/method3.png");
	ImageIcon m4 = new ImageIcon("images/method4.png");
	ImageIcon lway, rway, method1, method2, method3, method4;
	
	JButton leftbtn, rightbtn;
	JLabel mlabel[] = new JLabel[4];
	
	public GameMethod() {
		setTitle("탈출하는 카카오 프렌즈를 구하라");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.white);
		
		Image img3 = m1.getImage();
		Image changedImg3 = img3.getScaledInstance(1200,750,Image.SCALE_SMOOTH);     
		method1 = new ImageIcon(changedImg3);
		
		Image img4 = m2.getImage();
		Image changedImg4 = img4.getScaledInstance(1200,750,Image.SCALE_SMOOTH);     
		method2 = new ImageIcon(changedImg4);
		
		Image img5 = m3.getImage();
		Image changedImg5 = img5.getScaledInstance(1200,750,Image.SCALE_SMOOTH);     
		method3 = new ImageIcon(changedImg5);
		
		Image img6 = m4.getImage();
		Image changedImg6 = img6.getScaledInstance(1100,750,Image.SCALE_SMOOTH);     
		method4 = new ImageIcon(changedImg6);
		
		mlabel[0] = new JLabel(method1);
		mlabel[0].setSize(1200,750);
		mlabel[0].setLocation(0,0);
		
		mlabel[1] = new JLabel(method2);
		mlabel[1].setSize(1200,750);
		mlabel[1].setLocation(0,0);
		mlabel[1].setVisible(false);
		
		mlabel[2] = new JLabel(method3);
		mlabel[2].setSize(1200,750);
		mlabel[2].setLocation(0,0);
		mlabel[2].setVisible(false);
		
		mlabel[3] = new JLabel(method4);
		mlabel[3].setSize(1100,750);
		mlabel[3].setLocation(50,0);
		mlabel[3].setVisible(false);
		
		c.add(mlabel[0]);
		c.add(mlabel[1]);
		c.add(mlabel[2]);
		c.add(mlabel[3]);
		
		
		Image img1 = leftway.getImage();
		Image changedImg1 = img1.getScaledInstance(75,75,Image.SCALE_SMOOTH);     
		lway = new ImageIcon(changedImg1);
		
		Image img2 = rightway.getImage();
		Image changedImg2 = img2.getScaledInstance(75,75,Image.SCALE_SMOOTH);     
		rway = new ImageIcon(changedImg2);
		
		leftbtn = new JButton(lway);
		leftbtn.setSize(75,75);
		leftbtn.setLocation(515,765);
		leftbtn.setBorderPainted(false);
		leftbtn.setBackground(Color.white);
		c.add(leftbtn);
		leftbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mlabel[0].isVisible()==true) {
					
				}else if(mlabel[1].isVisible()==true) {
					mlabel[0].setVisible(true);
					mlabel[1].setVisible(false);
					
				}else if(mlabel[2].isVisible()==true) {
					mlabel[1].setVisible(true);
					mlabel[2].setVisible(false);
				}
				else if(mlabel[3].isVisible()==true) {
				  mlabel[2].setVisible(true);
				  mlabel[3].setVisible(false);
			}
			}
			
		});
		
		rightbtn = new JButton(rway);
		rightbtn.setSize(75,75);
		rightbtn.setLocation(610,765);
		rightbtn.setBorderPainted(false);
		rightbtn.setBackground(Color.white);
		c.add(rightbtn);
		rightbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mlabel[0].isVisible()==true) {
					mlabel[1].setVisible(true);
					mlabel[0].setVisible(false);
					
				}else if(mlabel[1].isVisible()==true) {
					mlabel[2].setVisible(true);
					mlabel[1].setVisible(false);
					
				}else if(mlabel[2].isVisible()==true) {
					mlabel[3].setVisible(true);
					mlabel[2].setVisible(false);
					
				}else if(mlabel[3].isVisible()==true) {					
				}
			}
			
		});
		
		
		setSize(1200,900);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
