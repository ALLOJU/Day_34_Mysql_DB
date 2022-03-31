import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.EmployeePayrollData;

public class Employee_PayrollDBService {
	private static Employee_PayrollDBService employeePayrollDBService;
	private PreparedStatement employeePayrollDataStatement;

	

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
	 * getEmployeePayrollData method Function to get salary by name
	 * 
	 * @param name
	 * @return employeePayrollDataList
	 *//*
		 * public List<EmployeePayrollData> getEmployeePayrollData(String name) {
		 * List<EmployeePayrollData> employeePayrollDataList = null; if
		 * (this.employeePayrollDataStatement == null)
		 * this.prepareStatementForEmployeeData(); try {
		 * employeePayrollDataStatement.setString(1, name); ResultSet resultSet;
		 * resultSet = employeePayrollDataStatement.executeQuery();
		 * employeePayrollDataList = this.getEmployeePayrollData(resultSet); } catch
		 * (SQLException e) { e.printStackTrace(); } return employeePayrollDataList; }
		 */
	/**
	 * get employee data using result set
	 * 
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
	/*
	 * public void prepareStatementForEmployeeData() { try { Connection connection =
	 * this.getConnection(); String sql =
	 * "SELECT * FROM employee_payroll WHERE name = ?"; employeePayrollDataStatement
	 * = connection.prepareStatement(sql); } catch (SQLException e) {
	 * e.printStackTrace(); } }
	 */
	public int updateEmployeeData(String name, double basic_pay) {
		return this.updateEmployeeDataUsingStatement(name, basic_pay);
	}

	/**
	 * method to update employee salary by using name of the employee
	 * 
	 * @param name      - name of the employee to update
	 * @param basic_pay - salary of the employee which we need to change for
	 *                  particular employee
	 * @return
	 */
	private int updateEmployeeDataUsingStatement(String name, double basic_pay) {
		String sql = String.format("update employee_payroll_service set basic_pay = %.2f where name = '%s';", basic_pay,
				name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param startDate - start date for the given input
	 * @param endDate   - end date for the given input
	 * @return
	 */
	public List<EmployeePayrollData> retrieveEmployyesForGivenDataRange(String startDate, String endDate)
			throws SQLException {
		List<EmployeePayrollData> employeePayrollDataList = null;
		try {
			if (this.employeePayrollDataStatement == null)
				this.prepareStatementForRetrieveEmployeePayrollDateRange();
			employeePayrollDataStatement.setString(1, startDate);
			employeePayrollDataStatement.setString(2, endDate);
			ResultSet resultSet;
			resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollDataList = this.retrieveEmployeePayrollDataRange(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollDataList;
	}

	public List<EmployeePayrollData> retrieveEmployeePayrollDataRange(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basic_pay = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollDataList.add(new EmployeePayrollData(id, name, basic_pay, startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollDataList;
	}

	public void prepareStatementForRetrieveEmployeePayrollDateRange() {
		Connection connection = null;
		try {
			connection = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = "SELECT * FROM employee_payroll_service WHERE start BETWEEN ? AND ?";
		try {
			assert connection != null;
			employeePayrollDataStatement = connection.prepareStatement(sql);
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

	}

	/**
	 * created getAverageSalaryByGender method to get average salary group by gender
	 * data from database by using mysql query in the method and mapped gender and
	 * averageSalary
	 * 
	 * @return genderToAverageSalaryMap
	 */
	public Map<String, Double> getAverageSalaryByGender() {
		String sql = "SELECT gender, AVG(basic_pay) as average_salary FROM employee_payroll_service GROUP BY GENDER;";
		Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				Double averageSalary = resultSet.getDouble("average_salary");
				genderToAverageSalaryMap.put(gender, averageSalary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderToAverageSalaryMap;
	}

	/**
	 * created getAverageSalaryByGender method to get sum salary group by gender
	 * data from database by using mysql query in the method and mapped gender and
	 * sumSalary
	 * 
	 * @return genderToSumSalaryMap
	 */
	public Map<String, Double> getSumSalaryByGender() {
		String sql = "SELECT gender, SUM(basic_pay) as sum_salary FROM employee_payroll_service GROUP BY GENDER;";
		Map<String, Double> genderToSumSalaryMap = new HashMap<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				Double sumSalary = resultSet.getDouble("sum_salary");
				genderToSumSalaryMap.put(gender, sumSalary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderToSumSalaryMap;
	}

	/**
	 * created getMinimumSalaryByGender method to get minimum salary group by gender
	 * data from database by using mysql query in the method and mapped gender and
	 * minimumSalary
	 * 
	 * @return genderToMinimumSalaryMap
	 */
	public Map<String, Double> getMinimumSalaryByGender() {
		String sql = "SELECT gender, Min(basic_pay) as minimum_salary FROM employee_payroll GROUP BY GENDER;";
		Map<String, Double> genderToMinimumSalaryMap = new HashMap<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				Double minimumSalary = resultSet.getDouble("minimum_salary");
				genderToMinimumSalaryMap.put(gender, minimumSalary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderToMinimumSalaryMap;
	}

	/**
	 * created getMaximumSalaryByGender method to get maximum salary group by gender
	 * data from database by using mysql query in the method and mapped gender and
	 * maximumSalary
	 * 
	 * @return genderToMaximumSalaryMap
	 */
	public Map<String, Double> getMaximumSalaryByGender() {
		String sql = "SELECT gender, Max(basic_pay) as maximum_salary FROM employee_payroll GROUP BY GENDER;";
		Map<String, Double> genderToMaximumSalaryMap = new HashMap<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				Double maximumSalary = resultSet.getDouble("maximum_salary");
				genderToMaximumSalaryMap.put(gender, maximumSalary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderToMaximumSalaryMap;
	}

	/**
	 * created getCountNameByGender method to get count name group by gender data
	 * from database by using mysql query in the method and mapped gender and
	 * countName
	 * 
	 * @return genderToCountNameMap
	 */
	public Map<String, Integer> getCountNameByGender() {
		String sql = "SELECT gender, COUNT(name) as count_name FROM employee_payroll GROUP BY GENDER;";
		Map<String, Integer> genderToCountNameMap = new HashMap<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				Integer countName = resultSet.getInt("count_name");
				genderToCountNameMap.put(gender, countName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genderToCountNameMap;
	}
	/**
	 * Add new employee to database
	 * @param empName - name of the employee
	 * @param gender - gender of the employee
	 * @param salary  - salary of the employee
	 * @param now - time from the LocalDate
	 */
	public EmployeePayrollData addEmployeeToPayroll(String empName, String gender, double salary, LocalDate now) {
		int employeeId = -1;
        EmployeePayrollData employeePayrollData = null;
        String sql = String.format("INSERT INTO employee_payroll_service (name, gender, basic_pay, start) " +
                                   "VALUES('%s', '%s', '%s', '%s');", empName, gender, salary, now);
        try(Connection connection = this.getConnection()) {
        	
        	Statement statement=connection.createStatement();
        	int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    employeeId = resultSet.getInt(1);
            }
            employeePayrollData = new EmployeePayrollData(employeeId, empName, salary, now);
        }
        catch (SQLException e) {
			e.printStackTrace();
		}
        return employeePayrollData;
        }
	 public boolean checkEmployeePayrollSyncWithDB(String name) {
	        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
	        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	    }
	 /**
	     * created getEmployeePayrollData method to get data from database
	     * added try and catch block to throw sql exception
	     * @param name name
	     * @return employeePayrollDataList
	     */
	    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
	        List<EmployeePayrollData> employeePayrollDataList = null;
	        if (this.employeePayrollDataStatement == null)
	            this.prepareStatementForEmployeeData();
	        try {
	            employeePayrollDataStatement.setString(1, name);
	            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
	            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return employeePayrollDataList;
	    }
	    /**
	     * prepareStatementForEmployeeData method for single query to get the data from database
	     * added try and catch block to throw sql exception
	     */
	    private void prepareStatementForEmployeeData() {
	        try {
	            Connection connection = this.getConnection();
	            String sql = "SELECT * FROM employee_payroll Where name = ?";
	            employeePayrollDataStatement = connection.prepareStatement(sql);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
}
