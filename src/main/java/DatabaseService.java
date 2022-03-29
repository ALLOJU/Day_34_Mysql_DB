import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import com.mysql.cj.jdbc.Driver;

public class DatabaseService {
	public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String username = "root";
		String password = "Admin@123";
		Connection con = null;
		try {
			// calling class for mysql driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find database driver in the Classpath", e);
		}
		listDriver();

		try {
			System.out.println("Connecting to data base :" + jdbcURL);
			con = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connection is Successfull ::" + con);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	/**
	 * to check the list of drivers present
	 */
	public static void listDriver() {

		Enumeration<java.sql.Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
