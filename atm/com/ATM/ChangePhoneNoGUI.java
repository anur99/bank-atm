package ATM;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;

public class ChangePhoneNoGUI {
	JFrame frame;
	JLabel ATMLabel,oldLabel,newLabel;
	JTextField oldText,newText;
	JButton submitBtn;
	
	public ChangePhoneNoGUI() {
		frame = new JFrame("AUTOMATIC TRANSACTION MACHINE");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dim.width,dim.height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		
		ATMLabel = new JLabel("AUTOMATIC TRANSACTION MACHINE");
		ATMLabel.setForeground(Color.green);
		ATMLabel.setFont(new Font("Arial", Font.BOLD, 70));
		ATMLabel.setBounds(30, 50, 1500, 60);
		
		oldLabel = new JLabel("Enter Current Phone Number");
		oldLabel.setFont(new Font("Arial",Font.BOLD,30));
		oldLabel.setBounds(250, 170, 500, 40);
		oldLabel.setForeground(Color.BLUE);
		
		oldText = new JTextField();
		oldText.setFont(new Font("Arial",Font.PLAIN,18));
		oldText.setBounds(670, 170, 400, 40);
		oldText.setHorizontalAlignment(JTextField.RIGHT);
		
		newLabel = new JLabel("Enter New Phone Number");
		newLabel.setFont(new Font("Arial",Font.BOLD,30));
		newLabel.setBounds(250, 270, 500, 40);
		newLabel.setForeground(Color.BLUE);
		
		newText = new JTextField();
		newText.setFont(new Font("Arial",Font.PLAIN,18));
		newText.setBounds(670, 270, 400, 40);
		newText.setHorizontalAlignment(JTextField.RIGHT);
		
		submitBtn = new JButton("Submit");
		submitBtn.setFont(new Font("Arial",Font.BOLD,30));
		submitBtn.setBounds(550, 470, 200, 40);
		submitBtn.setForeground(Color.white);
		submitBtn.setBackground(new Color(0,255,0));
		
		frame.add(ATMLabel);
		frame.add(oldLabel);
		frame.add(oldText);
		frame.add(newLabel);
		frame.add(newText);
		frame.add(submitBtn);
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitBtnActionPerformed(e);
			}
		});
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void submitBtnActionPerformed(ActionEvent e) {
		String newPhone = newText.getText().toString();
		String oldPhone = oldText.getText().toString();
		String checkOldPhone="";
		try {
			Connection con =ConnectionDataBase.getConnection();
			PreparedStatement psm = con.prepareStatement("select * from users_account where accountNo="+InfoGUI.confirm);
			ResultSet rs = psm.executeQuery();
			while(rs.next()) {
				checkOldPhone = rs.getString(5);
				break;
			}
			if(checkOldPhone.equals(oldPhone)) {
				PreparedStatement psm2 = con.prepareStatement("update users_account set phoneNo=? where accountNo="+InfoGUI.confirm);
				psm2.setString(1, newPhone);
				psm2.executeUpdate();
				
				JFrame wFrame = new JFrame();
				frame.setVisible(false);
				wFrame.setSize(600, 300);
				wFrame.setLocationRelativeTo(null);
				wFrame.setLayout(null);
				JLabel welcomeMsg = new JLabel("Thanks for Coming");
				welcomeMsg.setFont(new Font("Arial", Font.BOLD, 35));
				welcomeMsg.setForeground(new Color(186, 219, 255));
				welcomeMsg.setBounds(150, 30, 400, 40);

				JLabel phoneLabel = new JLabel("Phone No. Successfully Change.....");
				phoneLabel.setBounds(20, 100, 600, 40);
				phoneLabel.setFont(new Font("Arial", Font.BOLD, 35));

				JButton okBtn = new JButton("OK");
				okBtn.setBackground(new Color(0, 255, 0));
				okBtn.setForeground(Color.white);
				okBtn.setFont(new Font("Arial", Font.BOLD, 18));
				okBtn.setBounds(250, 200, 80, 40);
				wFrame.add(welcomeMsg);
				wFrame.add(phoneLabel);
				wFrame.add(okBtn);
				okBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				wFrame.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Phone Number doesn't match\nplease type again");
				oldText.setText("");
				oldText.requestFocus();
			}
		} 
		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Some Exceptions Occur....","Error Message",JOptionPane.ERROR_MESSAGE);
		}
	}
}


