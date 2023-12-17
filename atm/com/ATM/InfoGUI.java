package ATM;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class InfoGUI implements ActionListener {
	ATMGUInterface atmInterface;

	public static JFrame frame = new JFrame("Automatic Transaction Machine");
	public String d_name = "";
	public String d_accountNo = "";
	public String d_balance = "";
	public String d_adhaarNo = "";
	public String d_phoneNo = "";
	public String d_ifsc_code = "";
	public String d_branch = "";
	public String d_email = "";

	public static String account;
	public static String confirm;

	public InfoGUI(ATMGUInterface atmInterface) {
		this.atmInterface = atmInterface;
	}

	public void actionPerformed(ActionEvent e) {
		// Disable main frame and show a new Frame...
		account = ATMGUInterface.accountText.getText();
		confirm = ATMGUInterface.confirmText.getText();

		if (account.equals("") || confirm.equals("")) {
			JOptionPane.showMessageDialog(null, "Please fillup all the fields");
			ATMGUInterface.accountText.requestFocus();
		} else if (account.equals(confirm)) {

			try {
				// check the account no. from database and make connection with them...

				Connection con = ConnectionDataBase.getConnection();
				PreparedStatement psm = con.prepareStatement("select * from users_account where accountNo=?");
				psm.setString(1, confirm);
				// Fetching a data from database....
				ResultSet rs = psm.executeQuery("select * from users_account where accountNo=" + confirm);
				while (rs.next()) {
					d_name = rs.getString(1);
					d_accountNo = rs.getString(2);
					d_balance = rs.getString(3);
					d_adhaarNo = rs.getString(4);
					d_phoneNo = rs.getString(5);
					d_email = rs.getString(6);
					d_ifsc_code = rs.getString(7);
					d_branch = rs.getString(8);
					break;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
			if (!confirm.equals(d_accountNo)) {
				JOptionPane.showMessageDialog(null, "Invalid Account Number", "Account Info",
						JOptionPane.INFORMATION_MESSAGE);
				ATMGUInterface.accountText.setText("");
				ATMGUInterface.accountText.requestFocus();
				ATMGUInterface.confirmText.setText("");
			} else {
				ATMGUInterface.mainFrame.setVisible(false);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setSize(dim.width,dim.height);
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setLayout(null);
				// Add ATM Heading label....
				JLabel ATMLabel = new JLabel("AUTOMATIC TRANSACTION MACHINE");
				ATMLabel.setForeground(Color.green);
				ATMLabel.setFont(new Font("Arial", Font.BOLD, 70));
				ATMLabel.setBounds(30, 50, 1500, 60);

				JLabel nameLabel = new JLabel("NAME                             " + d_name.toUpperCase());
				nameLabel.setForeground(Color.BLACK);
				nameLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				nameLabel.setBounds(380, 140, 1000, 50);

				JLabel accLabel = new JLabel("ACCOUNT NO.               " + d_accountNo);
				accLabel.setForeground(Color.BLACK);
				accLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				accLabel.setBounds(380, 200, 1000, 50);

				JLabel ifscLabel = new JLabel("IFSC CODE                    " + d_ifsc_code);
				ifscLabel.setForeground(Color.BLACK);
				ifscLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				ifscLabel.setBounds(380, 260, 1000, 50);

				JLabel branchLabel = new JLabel("BRANCH                        " + d_branch);
				branchLabel.setForeground(Color.BLACK);
				branchLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				branchLabel.setBounds(380, 320, 1000, 50);

				JLabel adhaarLabel = new JLabel("ADHAAR NO.                 " + d_adhaarNo);
				adhaarLabel.setForeground(Color.BLACK);
				adhaarLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				adhaarLabel.setBounds(380, 380, 1000, 50);

				JLabel phoneLabel = new JLabel("PHONE NO.                   " + d_phoneNo);
				phoneLabel.setForeground(Color.BLACK);
				phoneLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				phoneLabel.setBounds(380, 440, 1000, 50);

				JLabel emailLabel = new JLabel("EMAIL                             " + d_email);
				emailLabel.setForeground(Color.BLACK);
				emailLabel.setFont(new Font("Arial", Font.PLAIN, 35));
				emailLabel.setBounds(380, 500, 1000, 50);

				JButton previousBtn = new JButton("BACK");
				previousBtn.setForeground(Color.white);
				previousBtn.setBackground(new Color(0, 255, 0));
				previousBtn.setFont(new Font("Arial", Font.BOLD, 30));
				previousBtn.setBounds(330,600,300,60);

				JButton nextBtn = new JButton("NEXT");
				nextBtn.setForeground(Color.white);
				nextBtn.setBackground(new Color(0, 255, 0));
				nextBtn.setFont(new Font("Arial", Font.BOLD, 30));
				nextBtn.setBounds(690, 600, 300, 60);

				frame.add(nameLabel);
				frame.add(accLabel);
				frame.add(ifscLabel);
				frame.add(branchLabel);
				frame.add(adhaarLabel);
				frame.add(phoneLabel);
				frame.add(emailLabel);
				frame.add(ATMLabel);
				frame.add(previousBtn);
				previousBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ATMGUInterface.mainFrame.setVisible(true);
						frame.setVisible(false);
					}
				});
				frame.add(nextBtn);

				TransactionGUI tran = new TransactionGUI(this);
				nextBtn.addActionListener(tran);

				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Account Number", "Error Message", JOptionPane.OK_OPTION);
			ATMGUInterface.accountText.setText("");
			ATMGUInterface.confirmText.setText("");
			ATMGUInterface.accountText.requestFocus();
		}
	}
}
