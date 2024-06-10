package BE.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import BE.Employee;
import BLL.EmployeeLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    private Employee employee;

    private EmployeeLogic eLogic = new EmployeeLogic();

    @BeforeEach
    public void setUp() {
        double annualSalary = 50000.00;
        int overheadMultiPercent = 10;
        double confFixedAnnualAmount = 5000.00;
        double annualSalaryUSD = 52000.00;
        double confFixedAnnualAmountUSD = 5200.00;
        String country = "USA";
        String continent = "North America";
        String team = "Development";
        int workingHours = 2080;
        int utilizationPercent = 100;
        String employeeType = "Full-time";
        String fullName = "Martins Lavely";


        employee = new Employee(fullName,
                annualSalary, overheadMultiPercent, confFixedAnnualAmount, country, continent, null,
                workingHours, utilizationPercent, employeeType, annualSalaryUSD, confFixedAnnualAmountUSD
        );
    }

    @Test
    public void testCalculateHourlyRate() {
        int curentCurency = 0;
        double expectedHourlyRate = 24.04; // Correct expected value rounded to 2 decimal places
        double actualHourlyRate = eLogic.calculateHourlyRate(employee.getAnnualSalary(),employee.getAnnualSalaryUSD(), employee.getWorkingHours(), employee.getUtilizationPercent(), employee, curentCurency);
        assertEquals(expectedHourlyRate, actualHourlyRate, 0.01, "Hourly rate should be correctly calculated.");
    }

    @Test
    public void testCalculateDailyRate() {
        int hoursInWorkDay = 8;
        double expectedHourlyRate = 24.04;
        double expectedDailyRate = 192.32;  // Expected daily rate rounded to 2 decimal places
        int curentCurency = 0;

        double actualDailyRate = eLogic.calculateDailyRate(hoursInWorkDay, employee, curentCurency);
        assertEquals(expectedDailyRate, actualDailyRate, 0.01, "Daily rate should be correctly calculated.");
    }

    @Test
    public void testEmployeeAttributesGreaterThanZero() {
        assertTrue(employee.getAnnualSalary() > 0, "Annual salary should be greater than 0.");
        assertTrue(employee.getOverheadMultiPercent() > 0, "Overhead multiplier percent should be greater than 0.");
        assertTrue(employee.getWorkingHours() > 0, "Working hours should be greater than 0.");
        assertTrue(employee.getUtilizationPercent() > 0, "Utilization percent should be greater than 0.");
    }
}
