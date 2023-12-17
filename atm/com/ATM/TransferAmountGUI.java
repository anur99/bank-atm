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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TransferAmountGUI implements ActionListener {

	TransactionGUI trans;

	JFrame frame = new JFrame("Automatic Transaction Machine");
	JLabel ATMLabel, payeeAccountNo, payeeConfirmAccount, transferAmount;
	JTextField accountText, confirmText, transferText;
	JButton previousBtn, transactionBtn, sendBtn;

	public TransferAmountGUI(TransactionGUI trans) {
		this.trans = trans;
	}

	public void actionPerformed(ActionEvent e) {
		TransactionGUI.tFrame.setVisible(false);
		frame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dim.width,dim.height);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		ATMLabel = new JLabel("AUTOMATIC TRANSACTION MACHINE");
		ATMLabel.setForeground(Color.green);
		ATMLabel.setFont(new Font("Arial", Font.BOLD, 70));
		ATMLabel.setBounds(30, 50, 1500, 60);

		payeeAccountNo = new JLabel("Enter Account Number");
		payeeAccountNo.setFont(new Font("Arial", Font.PLAIN, 30));
		payeeAccountNo.setBounds(250, 170, 500, 40);

		accountText = new JTextField();
		accountText.setFont(new Font("Arial", Font.PLAIN, 18));
		accountText.setHorizontalAlignment(JTextField.RIGHT);
		accountText.setBounds(750,170,300,40);

		payeeConfirmAccount = new JLabel("Confirm Account Number");
		payeeConfirmAccount.setFont(new Font("Arial", Font.PLAIN, 30));
		payeeConfirmAccount.setBounds(250,250,500,40);

		confirmText = new JPasswordField();
		confirmText.setFont(new Font("Arial", Font.PLAIN, 18));
		confirmText.setHorizontalAlignment(JPasswordField.RIGHT);
		confirmText.setBounds(750,250,300,40);

		transferAmount = new JLabel("Enter Transfer Amount");
		transferAmount.setFont(new Font("Arial", Font.PLAIN, 30));
		transferAmount.setBounds(250, 330, 500, 40);

		transferText = new JTextField();
		transferText.setFont(new Font("Arial", Font.PLAIN, 18));
		transferText.setHorizontalAlignment(JPasswordField.RIGHT);
		transferText.setBounds(750, 330, 300, 40);

		sendBtn = new JButton("SEND");
		sendBtn.setForeground(Color.white);
		sendBtn.setBackground(new Color(0, 255, 0));
		sendBtn.setFont(new Font("Arial", Font.BOLD, 30));
		sendBtn.setBounds(550, 470, 200, 40);

		previousBtn = new JButton("BACK");
		previousBtn.setForeground(Color.white);
		previousBtn.setBackground(new Color(0, 255, 0));
		previousBtn.setFont(new Font("Arial", Font.BOLD, 30));
		previousBtn.setBounds(330,600,300,60);

		transactionBtn = new JButton("CANCEL TRANSACTION");
		transactionBtn.setForeground(Color.white);
		transactionBtn.setBackground(Color.red);
		transactionBtn.setFont(new Font("Arial", Font.BOLD, 30));
		transactionBtn.setBounds(690, 600, 400, 60);

		frame.add(ATMLabel);
		frame.add(payeeAccountNo);
		frame.add(accountText);
		frame.add(payeeConfirmAccount);
		frame.add(confirmText);
		frame.add(transferAmount);
		frame.add(transferText);
		frame.add(sendBtn);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendBtnActionPerformed(e);
			}
		});
		frame.add(previousBtn);
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				TransactionGUI.tFrame.setVisible(true);
			}
		});

		frame.add(transactionBtn);
		transactionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void sendBtnActionPerformed(ActionEvent e) {
		String transfer_account_No = accountText.getText();
		String transfer_C_account_No = confirmText.getText();
		if (transfer_account_No.equals("") || transfer_C_account_No.equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter all the Fields", "Input Error", JOptionPane.OK_OPTION);
			accountText.requestFocus();
		} else {

			// If the account number is same as the current user account number ....
			String curr_account_No = ATMGUInterface.accountText.getText();

			if (curr_account_No.equals(transfer_account_No)) {
				JOptionPane.showMessageDialog(null, "You cannot transfer amount on your own Account", "Error Message",
						JOptionPane.ERROR_MESSAGE);
				accountText.setText("");
				confirmText.setText("");
				accountText.requestFocus();
			} else {
				// To get Connection From database..........
				Connection con = ConnectionDataBase.getConnection();
				// Input Validation...

				if (TransactionGUI.validInput(transferText)) {
					Double transfer_amount = Double.valueOf(transferText.getText()).doubleValue();
					if (transfer_amount > 0 && transfer_amount <= 5000) {
						PreparedStatement ps = null;
						try {
							Double transfer_bal = Double.parseDouble(trans.d_balance);
							Double total_balance = transfer_bal - transfer_amount;
							String curr_balance = String.valueOf(total_balance);
							ps = con.prepareStatement(
									"update users_account set balance=? where accountNo=" + InfoGUI.confirm);
							ps.setString(1, curr_balance);

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
									JOptionPane.ERROR_MESSAGE);
						}
						// Transfer Amount to the given Account number....
						String confirm_account = confirmText.getText();
						String curr_balance = "";
						String curr_account = "";
						try {
							PreparedStatement ps1 = con
									.prepareStatement("select * from users_account where accountNo=?");
							ps1.setString(1, confirm_account);
							ResultSet rs = ps1
									.executeQuery("select * from users_account where accountNo=" + confirm_account);
							while (rs.next()) {
								curr_account = rs.getString(2);
								curr_balance = rs.getString(3);
								break;
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
									JOptionPane.ERROR_MESSAGE);
						}
						if (curr_account.equals(confirm_account)) {
							try {
								Double payee_bal = Double.parseDouble(curr_balance);
								payee_bal = payee_bal + transfer_amount;
								String curr_payee_balance = String.valueOf(payee_bal);
								PreparedStatement ps2 = con.prepareStatement(
										"update users_account set balance=? where accountNo=" + confirm_account);
								ps2.setString(1, curr_payee_balance);
								ps.executeUpdate();
								ps2.executeUpdate();

							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
										JOptionPane.ERROR_MESSAGE);
							}
							// Welcome Message for customer......
							TransactionGUI.thankYouScreen();
							
						} else {
							JOptionPane.showMessageDialog(null, "Account Number doesn't Exist in our database",
									"Account Info Error", JOptionPane.OK_OPTION);
							confirmText.setText("");
							accountText.setText("");
							accountText.requestFocus();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"You cannot transfer amount minimum Rs.1 and maximum Rs.5000", "Error Message",
								JOptionPane.OK_OPTION);
						transferText.setText("");
						transferText.requestFocus();
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Invalid Input\nPlease Enter valid amount",
							"Number Formatting Error", JOptionPane.DEFAULT_OPTION);
					transferText.setText("");
					transferText.requestFocus();
				}
			}
		}
	}
}
