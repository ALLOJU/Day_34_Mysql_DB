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
		Employee_PayrollDBService employeePayRollDBService=new Employee_PayrollDBService();
		List<EmployeePayrollData> employeePayRollList=employeePayRollDBService.readData();
		Assert.assertEquals(3,employeePayRollList.size());
	}
	
	
	
}
