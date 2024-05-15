package BE.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import BE.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    public void setUp() {
        double annualSalary = 50000.00; // Using double for more precise calculations
        int overheadMultiPercent = 10;
        double confFixedAnnualAmount = 5000.00;
        double annualSalaryUSD = 52000.00; // Assuming conversion to USD for international employees
        double confFixedAnnualAmountUSD = 5200.00;
        String country = "USA";
        String continent = "North America";
        String team = "Development";
        int workingHours = 2080; // 40 hours/week * 52 weeks
        int utilizationPercent = 100; // Fully utilized
        String employeeType = "Full-time";
        String fullname="Martins Lavely";
        int teamId=1;

        employee = new Employee(teamId,fullname,
                annualSalary, overheadMultiPercent, confFixedAnnualAmount, country, continent, team,
                workingHours, utilizationPercent, employeeType, annualSalaryUSD, confFixedAnnualAmountUSD
        );
    }

    @Test
    public void testCalculateHourlyRate() {
        // Updated expected value based on correct calculations
        double expectedHourlyRate = 2.64423;  // Rounded to 5 decimal places for matching precision in assertion
        double actualHourlyRate = employee.calculateHourlyRate();
        assertEquals(expectedHourlyRate, actualHourlyRate, 0.01, "Hourly rate should be correctly calculated.");
    }

    @Test
    public void testCalculateDailyRate() {
        // Assuming 8 working hours per day
        int hoursInWorkDay = 8;
        // Calculate the expected daily rate based on the corrected hourly rate calculation
        double expectedHourlyRate = 2.64423;  // Corrected and moved inside this test method
        double expectedDailyRate = expectedHourlyRate * hoursInWorkDay;  // This should be 2.64423 * 8

        double actualDailyRate = employee.calculateDailyRate(hoursInWorkDay);
        assertEquals(expectedDailyRate, actualDailyRate, 0.01, "Daily rate should be correctly calculated.");
    }
}
