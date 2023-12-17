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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TransactionGUI implements ActionListener {
	InfoGUI infoGUI;
	public static JFrame tFrame = new JFrame("Automatic Transaction Machine");
	JLabel ATMLabel, showBalance;
	JButton balanceBtn, withdrawBtn, depositBtn, transferBtn, doneWithBtn, doneDepoBtn, previousBtn, transactionBtn,
			emailBtn, nameBtn, phoneBtn;

	JTextField withdrawtxt, deposittxt;

	String d_balance = "";
	Connection con = null;
	PreparedStatement psm;

	public TransactionGUI(InfoGUI infoGUI) {
		this.infoGUI = infoGUI;
	}

	public void actionPerformed(ActionEvent e) {
		InfoGUI.frame.setVisible(false);
		tFrame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		tFrame.setSize(dim.width,dim.height);
		tFrame.setLocationRelativeTo(null);
		tFrame.setLayout(null);

		ATMLabel = new JLabel("AUTOMATIC TRANSACTION MACHINE");
		ATMLabel.setForeground(Color.green);
		ATMLabel.setFont(new Font("Arial", Font.BOLD, 70));
		ATMLabel.setBounds(30, 50, 1500, 60);

		balanceBtn = new JButton("BALANCE");
		balanceBtn.setForeground(Color.white);
		balanceBtn.setBackground(new Color(221, 111, 0));
		balanceBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		balanceBtn.setBounds(100, 160, 250, 40);

		withdrawBtn = new JButton("WITHDRAW");
		withdrawBtn.setForeground(Color.white);
		withdrawBtn.setBackground(new Color(221, 111, 0));
		withdrawBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		withdrawBtn.setBounds(100, 260, 250, 40);

		withdrawtxt = new JTextField();
		withdrawtxt.setVisible(false);
		withdrawtxt.setHorizontalAlignment(JTextField.RIGHT);
		withdrawtxt.setBounds(400, 260, 220, 40);
		withdrawtxt.setFont(new Font("Arial", Font.BOLD, 30));
		withdrawtxt.setBackground(new Color(186, 219, 255));

		doneWithBtn = new JButton("OK");
		doneWithBtn.setVisible(false);
		doneWithBtn.setForeground(Color.white);
		doneWithBtn.setBackground(new Color(158, 34, 172));
		doneWithBtn.setFont(new Font("Arial", Font.BOLD, 30));
		doneWithBtn.setBounds(640, 260, 80, 40);

		depositBtn = new JButton("DEPOSIT");
		depositBtn.setForeground(Color.white);
		depositBtn.setBackground(new Color(221, 111, 0));
		depositBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		depositBtn.setBounds(100, 360, 250, 40);

		deposittxt = new JTextField();
		deposittxt.setVisible(false);
		deposittxt.setHorizontalAlignment(JTextField.RIGHT);
		deposittxt.setFont(new Font("Arial", Font.BOLD, 30));
		deposittxt.setBounds(400, 360, 220, 40);
		deposittxt.setBackground(new Color(186, 219, 255));
		
		doneDepoBtn = new JButton("OK");
		doneDepoBtn.setVisible(false);
		doneDepoBtn.setForeground(Color.white);
		doneDepoBtn.setBackground(new Color(158, 34, 172));
		doneDepoBtn.setFont(new Font("Arial", Font.BOLD, 30));
		doneDepoBtn.setBounds(640, 360, 80, 40);

		transferBtn = new JButton("TRANSFER");
		transferBtn.setForeground(Color.white);
		transferBtn.setBackground(new Color(221, 111, 0));
		transferBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		transferBtn.setBounds(100, 460, 250, 40);

		emailBtn = new JButton("Change Email ID");
		emailBtn.setForeground(Color.white);
		emailBtn.setBackground(new Color(221, 111, 0));
		emailBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		emailBtn.setBounds(950, 160, 280, 40);

		nameBtn = new JButton("Change Name");
		nameBtn.setForeground(Color.white);
		nameBtn.setBackground(new Color(221, 111, 0));
		nameBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		nameBtn.setBounds(950, 260, 280, 40);

		phoneBtn = new JButton("Change Phone No");
		phoneBtn.setForeground(Color.white);
		phoneBtn.setBackground(new Color(221, 111, 0));
		phoneBtn.setFont(new Font("Arial", Font.PLAIN, 30));
		phoneBtn.setBounds(950, 360, 280, 40);

		previousBtn = new JButton("BACK");
		previousBtn.setForeground(Color.white);
		previousBtn.setBackground(new Color(0, 255, 0));
		previousBtn.setFont(new Font("Arial", Font.BOLD, 30));
		previousBtn.setBounds(290, 600, 300, 60);

		transactionBtn = new JButton("CANCEL TRANSACTION");
		transactionBtn.setForeground(Color.white);
		transactionBtn.setBackground(Color.red);
		transactionBtn.setFont(new Font("Arial", Font.BOLD, 30));
		transactionBtn.setBounds(650, 600, 400, 60);

		// Balance Inquary.....
		try {
			// check the account no. from database and make connection with them...

			con = ConnectionDataBase.getConnection();
			psm = con.prepareStatement("select * from users_account where accountNo=?");
			psm.setString(1, InfoGUI.confirm);
			ResultSet rs = psm.executeQuery("select * from users_account where accountNo=" + InfoGUI.confirm);
			while (rs.next()) {
				d_balance = rs.getString(3);
				break;
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
		showBalance = new JLabel();
		showBalance.setFont(new Font("Arial", Font.ITALIC, 30));
		showBalance.setText("Total Balance  : Rs " + d_balance);
		showBalance.setVisible(false);
		showBalance.setBounds(400, 160, 450, 40);
		tFrame.add(showBalance);

		tFrame.add(ATMLabel);
		tFrame.add(balanceBtn);
		balanceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBalance.setVisible(true);

			}
		});
		tFrame.add(withdrawBtn);
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdrawtxt.setVisible(true);
				withdrawtxt.requestFocus();
				doneWithBtn.setVisible(true);
				deposittxt.setVisible(false);
				doneDepoBtn.setVisible(false);
			}
		});
		tFrame.add(withdrawtxt);
		tFrame.add(depositBtn);
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deposittxt.setVisible(true);
				deposittxt.requestFocus();
				doneDepoBtn.setVisible(true);
				withdrawtxt.setVisible(false);
				doneWithBtn.setVisible(false);
			}
		});
		tFrame.add(deposittxt);
		tFrame.add(previousBtn);
		previousBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deposittxt.setVisible(false);
				withdrawtxt.setVisible(false);
				doneDepoBtn.setVisible(false);
				doneWithBtn.setVisible(false);
				tFrame.setVisible(false);
				InfoGUI.frame.setVisible(true);
			}
		});
		tFrame.add(doneWithBtn);
		doneWithBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doneWithBtnActionPerformed(e);
			}
		});
		tFrame.add(transferBtn);
		TransferAmountGUI transfer = new TransferAmountGUI(this);
		transferBtn.addActionListener(transfer);

		tFrame.add(emailBtn);
		emailBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tFrame.setVisible(false);
				new ChangeEmailGUI();

			}
		});
		tFrame.add(nameBtn);
		nameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tFrame.setVisible(false);
				new ChangeNameGUI();

			}
		});
		tFrame.add(phoneBtn);
		phoneBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tFrame.setVisible(false);
				new ChangePhoneNoGUI();

			}
		});

		tFrame.add(doneDepoBtn);
		doneDepoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doneDepoBtnActionPerformed(e);
			}
		});

		tFrame.add(transactionBtn);
		transactionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		tFrame.setVisible(true);
		tFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void doneWithBtnActionPerformed(ActionEvent e) {
		doneDepoBtn.setVisible(false);
		deposittxt.setVisible(false);

		if (validInput(withdrawtxt)) {
			// Withdraw Balance from account.....
			Double bal = Double.valueOf(withdrawtxt.getText()).doubleValue();
			if (bal % 100 == 0) {
				Double TotalWithBal = Double.parseDouble(infoGUI.d_balance);
				if (bal > TotalWithBal) {
					JOptionPane.showMessageDialog(null, "Insufficient Balance...", "Amount input error",
							JOptionPane.OK_OPTION);
					System.exit(0);
				}
				if (bal < 100 || bal > 25000) {
					JOptionPane.showMessageDialog(null, "Amount should be between $100 and $25000", "Amount input error",
							JOptionPane.OK_OPTION);
					withdrawtxt.setText("");
					withdrawtxt.requestFocus();
				} else {
					TotalWithBal = TotalWithBal - bal;
					String availBal = String.valueOf(TotalWithBal);
					try {
						PreparedStatement ps = con.prepareStatement(
								"update users_account set balance=? where accountNo=" + InfoGUI.confirm);
						ps.setString(1, availBal);
						ps.executeUpdate();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
					// Welcome Message for customer......
					thankYouScreen();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Amount Should be 100, 200, 300, 500.....", "Input Error",
						JOptionPane.OK_OPTION);
				withdrawtxt.setText("");
				withdrawtxt.requestFocus();
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Invalid Input\nPlease Enter valid amount", "Input Error",
					JOptionPane.DEFAULT_OPTION);
			withdrawtxt.setText("");
			withdrawtxt.requestFocus();
		}
	}

	private void doneDepoBtnActionPerformed(ActionEvent e) {
		// Deposit Balance to Account....
		withdrawtxt.setVisible(false);
		doneWithBtn.setVisible(false);
		if (validInput(deposittxt)) {
			Double depoBal = Double.valueOf(deposittxt.getText()).doubleValue();
			if (depoBal % 100 == 0) {
				Double TotalDepoBal = Double.parseDouble(infoGUI.d_balance);
				if (depoBal < 100 || depoBal > 25000) {
					JOptionPane.showMessageDialog(null, "You Cannot be transfer amount minimum 100 and maximum 25000",
							"Amount Input Error", JOptionPane.OK_OPTION);
					deposittxt.setText("");
					deposittxt.requestFocus();
				} else {
					TotalDepoBal = TotalDepoBal + depoBal;
					String availBalis = String.valueOf(TotalDepoBal);
					try {
						PreparedStatement ps = con.prepareStatement(
								"update users_account set balance=? where accountNo=" + InfoGUI.confirm);
						ps.setString(1, availBalis);
						ps.executeUpdate();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Some Exceptions Occur....", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					}

					// Welcome Message for customer......
					thankYouScreen();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Amount should be 100, 200, 300, 500.....", "Input Error",
						JOptionPane.OK_OPTION);
				deposittxt.setText("");
				deposittxt.requestFocus();
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Invalid Input\nPlease Enter valid amount", "Input Error",
					JOptionPane.DEFAULT_OPTION);
			deposittxt.setText("");
			deposittxt.requestFocus();
		}
	}

	// Validation Input ....
	public static boolean validInput(JTextField text) {
		String str = text.getText().trim();
		boolean hasDec = false;
		boolean valid = true;
		if (str.length() == 0) {
			valid = false;
		} else {
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				if (ch >= '0' && ch <= '9') {
					continue;
				} else if (ch == '.' && !hasDec) {
					hasDec = true;
				} else {
					valid = false; // Invalid Character Found
				}
			}
		}
		text.setText(str);
		if (!valid) {
			text.requestFocus();
		}
		return valid;
	}
	public static void thankYouScreen() {
		JFrame wFrame = new JFrame();
		tFrame.setVisible(false);
		wFrame.setSize(500, 300);
		wFrame.setLocationRelativeTo(null);
		wFrame.setLayout(null);
		JLabel welcomeMsg = new JLabel("Thanks for Coming");
		welcomeMsg.setFont(new Font("Arial", Font.BOLD, 35));
		welcomeMsg.setForeground(new Color(186, 219, 255));
		welcomeMsg.setBounds(100, 30, 400, 40);

		JLabel transLabel = new JLabel("Transaction Successful.....");
		transLabel.setBounds(40, 100, 500, 40);
		transLabel.setFont(new Font("Arial", Font.BOLD, 35));

		JButton okBtn = new JButton("OK");
		okBtn.setBackground(new Color(186, 219, 255));
		okBtn.setForeground(Color.black);
		okBtn.setFont(new Font("Arial", Font.BOLD, 18));
		okBtn.setBounds(220, 200, 80, 40);
		wFrame.add(welcomeMsg);
		wFrame.add(transLabel);
		wFrame.add(okBtn);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		wFrame.setVisible(true);
	}
}
