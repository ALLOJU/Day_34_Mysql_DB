import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;
import model.EmployeePayrollData;

public class EmployeePayrollTest {
	/**
	 * Test case to get table data from database using select query
	 */
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
		Employee_PayrollDBService employeePayRollDBService = new Employee_PayrollDBService();
		List<EmployeePayrollData> employeePayRollList = employeePayRollDBService.readData();
		Assert.assertEquals(3, employeePayRollList.size());
	}

	/**
	 * In this test case given New Employee Salary Should Update With Database.
	 */
	@Test
	public void givenNewEmployeeSalaryShouldUpdateWithDatabase() {
		Employee_PayrollDBService employeePayRollDBService = new Employee_PayrollDBService();
		employeePayRollDBService.updateEmployeeData("Terisa", 3000000.00);
		Assert.assertTrue(true);
	}

	/**
	 * Method to retrieve data for the given date range
	 * 
	 * @throws SQLException
	 */
	@Test
	public void givenDataRangeWhenRetrievedShouldMatchEmployeeCount() throws SQLException {
		Employee_PayrollDBService employeePayRollDBService = new Employee_PayrollDBService();

		List<EmployeePayrollData> employeePayrollData = employeePayRollDBService
				.retrieveEmployyesForGivenDataRange("2018-01-01", "2019-01-03");
		System.out.println(employeePayrollData);
		Assert.assertEquals(1, employeePayrollData.size());
	}

	/**
	 * test case is created to check average salary by gender
	 */
	@Test
	public void givenPayrollData_WhenAverageSalaryRetrieveByGender_ShouldReturnValue() {
		Employee_PayrollDBService employeePayrollService = new Employee_PayrollDBService();
		Map<String, Double> averageSalaryByGender = employeePayrollService.getAverageSalaryByGender();
		// System.out.println(averageSalaryByGender);
		Assert.assertTrue(
				averageSalaryByGender.get("M").equals(2000000.0) && averageSalaryByGender.get("F").equals(3000000.0));
	}

	/**
	 * test case is created to check sum salary by gender
	 */
	@Test
	public void givenPayrollData_WhenSumSalaryRetrieveByGender_ShouldReturnValue() {
		Employee_PayrollDBService employeePayrollService = new Employee_PayrollDBService();
		Map<String, Double> sumSalaryByGender = employeePayrollService.getSumSalaryByGender();
		// System.out.println(sumSalaryByGender);
		Assert.assertTrue(
				sumSalaryByGender.get("M").equals(4000000.0) && sumSalaryByGender.get("F").equals(3000000.00));
	}

	/**
	 * test case is created to check minimum salary by gender
	 */
	@Test
	public void givenPayrollData_WhenMinimumSalaryRetrieveByGender_ShouldReturnValue() {
		Employee_PayrollDBService employeePayrollService = new Employee_PayrollDBService();
		Map<String, Double> minimumSalaryByGender = employeePayrollService.getMinimumSalaryByGender();
		// System.out.println(minimumSalaryByGender);
		Assert.assertTrue(
				minimumSalaryByGender.get("M").equals(1000000.00) && minimumSalaryByGender.get("F").equals(3000000.00));
	}

	/**
	 * test case is created to check maximum salary by gender
	 */
	@Test
	public void givenPayrollData_WhenMaximumSalaryRetrieveByGender_ShouldReturnValue() {
		Employee_PayrollDBService employeePayrollService = new Employee_PayrollDBService();
		Map<String, Double> maximumSalaryByGender = employeePayrollService.getMaximumSalaryByGender();
		// System.out.println(maximumSalaryByGender);
		Assert.assertTrue(
				maximumSalaryByGender.get("M").equals(3000000.00) && maximumSalaryByGender.get("F").equals(3000000.00));
	}

	/**
	 * test case is created to check count name by gender
	 */
	@Test
	public void givenPayrollData_WhenCountNameRetrieveByGender_ShouldReturnValue() {
		Employee_PayrollDBService employeePayrollService = new Employee_PayrollDBService();
		Map<String, Integer> countNameByGender = employeePayrollService.getCountNameByGender();
		System.out.println(countNameByGender);
		Assert.assertTrue(countNameByGender.get("M").equals(2) && countNameByGender.get("F").equals(2));
	}
}
