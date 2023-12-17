

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

public class WelcomeScreen {
	Timer timer;

	public WelcomeScreen() {
		displayWelcomeScreen();
	}

	private void displayWelcomeScreen() {
		final JWindow window = new JWindow();
		window.setSize(700, 400);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		String file = "D:\\java program\\atm\\com\\ATM\\college.jpg";
		JLabel label = new JLabel(new ImageIcon(file));
		panel.add(label);
		window.add(panel);

		JProgressBar progress = new JProgressBar(0, 100);
		progress.setForeground(Color.green);
		window.add(BorderLayout.PAGE_END, progress);
		window.revalidate();
		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = progress.getValue();
				if (x == 100) {
					window.dispose();
					new ATMGUInterface();
					timer.stop();
				} else {
					progress.setValue(x + 8);
				}
			}
		});
		timer.start();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

	public static void main(String... args) {
		new WelcomeScreen();
	}
}
