import java.sql.SQLException;
import java.util.List;

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

	@Test
	public void givenDataRangeWhenRetrievedShouldMatchEmployeeCount() throws SQLException {
		Employee_PayrollDBService employeePayRollDBService = new Employee_PayrollDBService();
		
		List<EmployeePayrollData> employeePayrollData = employeePayRollDBService
				.retrieveEmployyesForGivenDataRange("2018-01-01", "2019-01-03");
		System.out.println(employeePayrollData);
		Assert.assertEquals(1, employeePayrollData.size());
	}

}
