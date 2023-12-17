package ATM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ATMGUInterface {
	public static JFrame mainFrame;
	public static JButton nextBtn, cancelBtn;
	public static JLabel ATMLabel, accountLabel, confirmAccLabel, noteLabel;
	public static JTextField accountText, confirmText;

	public ATMGUInterface() {
		mainFrame = new JFrame("Automatic Transaction Machine");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize(dim.width,dim.height);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);

		// Add ATM Heading label....
		ATMLabel = new JLabel("AUTOMATIC TRANSACTION MACHINE");
		ATMLabel.setForeground(Color.green);
		ATMLabel.setFont(new Font("Arial", Font.BOLD, 70));
		ATMLabel.setBounds(30, 50, 1500, 60);

		// Add Enter Account Number label....

		accountLabel = new JLabel("Enter Account Number");
		accountLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		accountLabel.setBounds(250, 170, 500, 40);

		accountText = new JTextField();
		accountText.setFont(new Font("Arial", Font.PLAIN, 30));
		accountText.setHorizontalAlignment(JTextField.RIGHT);
		accountText.setBounds(750, 170, 300, 40);

		confirmAccLabel = new JLabel("Confirm Account Number");
		confirmAccLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		confirmAccLabel.setBounds(250, 250, 500, 40);

		confirmText = new JPasswordField();
		confirmText.setFont(new Font("Arial", Font.PLAIN, 30));
		confirmText.setHorizontalAlignment(JPasswordField.RIGHT);
		confirmText.setBounds(750, 250, 300, 40);

		noteLabel = new JLabel();
		noteLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		noteLabel.setBounds(250, 280, 1000, 300);
		noteLabel.setText("<html>Note:" + "<br>1> It is neccessary to enter all the TextFields."
				+ "<br>2> Please make sure your account number and confirm account number are equal."
				+ "<br>3> PLease enter valid account number which is assigned to you by the bank."
				+ "<br>4> TextField should only be a Integers (example: 12345....)" + "</html>");

		cancelBtn = new JButton("CANCEL");
		cancelBtn.setFont(new Font("Arial", Font.BOLD, 30));
		cancelBtn.setBackground(Color.red);
		cancelBtn.setForeground(Color.white);
		cancelBtn.setBounds(300, 600, 300, 60);

		nextBtn = new JButton("NEXT");
		nextBtn.setBackground(new Color(0, 255, 0));
		nextBtn.setForeground(Color.white);
		nextBtn.setFont(new Font("Arial", Font.BOLD, 30));
		nextBtn.setBounds(660, 600, 300, 60);

		mainFrame.add(ATMLabel);
		mainFrame.add(accountLabel);
		mainFrame.add(accountText);
		mainFrame.add(confirmAccLabel);
		mainFrame.add(confirmText);
		mainFrame.add(noteLabel);
		mainFrame.add(nextBtn);
		InfoGUI info = new InfoGUI(this);
		nextBtn.addActionListener(info);
		mainFrame.add(cancelBtn);

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
}