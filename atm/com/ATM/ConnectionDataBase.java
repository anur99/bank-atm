package ATM;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnectionDataBase {
	public static Connection getConnection() {
		Connection con=null;
		try {
			Class.forName(DataBaseDetail.DATABASE_DRIVER);
			con=DriverManager.getConnection(DataBaseDetail.DATABASE_URL,DataBaseDetail.DATABASE_USERNAME,DataBaseDetail.DATABASE_PASSWORD);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Some Exceptions Occur....","Error Message",JOptionPane.ERROR_MESSAGE);
		}
		return con;
	}
}
