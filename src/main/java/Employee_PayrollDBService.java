import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.EmployeePayrollData;

public class Employee_PayrollDBService {
	private static Employee_PayrollDBService employeePayrollDBService;
	private PreparedStatement employeePayrollDataStatement;

	public static Employee_PayrollDBService getInstant() {
		if (employeePayrollDBService == null)
			employeePayrollDBService = new Employee_PayrollDBService();
		return employeePayrollDBService;
	}

	/**
	 * Attempts to establish a connection to the given database URL. The
	 * DriverManager attempts to select an appropriate driver from the set of
	 * registered JDBC drivers.
	 * 
	 * @return connection
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String username = "root";
		String password = "Admin@123";
		System.out.println("connecting to database");
		Connection connection = DriverManager.getConnection(jdbcURL, username, password);
		System.out.println("Connected Successfully!");
		return connection;
	}

	/**
	 * readData method reads the data and puts it into the ArrayList
	 * 
	 * @return employeePayrollList
	 */

	public ArrayList<EmployeePayrollData> readData() {
		String sql = "SELECT * FROM employee_payroll_service";
		ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basic_pay = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, basic_pay, startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	/**
	 * getEmployeePayrollData method
	 * Function to get salary by name
	 * @param name
	 * @return employeePayrollDataList
	 */
	public List<EmployeePayrollData> getEmployeePayrollData(String name) {
		List<EmployeePayrollData> employeePayrollDataList = null;
		if (this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet;
			resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollDataList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollDataList;
	}

	/**
	 * get employee data using result set
	 * @param resultSet - list of columns for the table
	 * @return
	 */
	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basic_pay = resultSet.getDouble("basic_pay");
				LocalDate startDate = null;
				startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollDataList.add(new EmployeePayrollData(id, name, basic_pay, startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollDataList;
	}
	/**
	 * get the employees data using prepared statement
	 */
	public void prepareStatementForEmployeeData() {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM employee_payroll WHERE name = ?";
			employeePayrollDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
