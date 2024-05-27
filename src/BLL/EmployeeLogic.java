package BLL;

import BE.Employee;
import BE.Group;
import DAL.EmployeeDAO;
import Exceptions.RateCalcException;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class EmployeeLogic {

    EmployeeDAO eDAO=new EmployeeDAO();

    public void create(Employee employee) throws RateCalcException {
        eDAO.create(employee);
    }

    public List<Employee> getAllEmployees() throws RateCalcException {
        return eDAO.getAllEmployees();
    }

    public void delete(Employee employee) throws RateCalcException {
        eDAO.delete(employee);
    }
    public void edit(Employee employee) throws RateCalcException {
        eDAO.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText) throws RateCalcException {
        return eDAO.searchEmployees(searchText);
    }

    public int getAnnualSllaryUSD(Employee employee) throws RateCalcException {
       return eDAO.getAnuallSalaryUSD(employee);
    }
    public int getConFixAnnAmount(Employee employee) throws RateCalcException {
        return eDAO.getConFixAmountUSD(employee);
    }
    public void removeTeamFromEmployee(int id, int tId) throws RateCalcException {
        eDAO.removeTeamFromEmployee(id, tId);
    }
    public Employee getEmployeeById(int id) throws RateCalcException {
        return eDAO.getEmployeeById(id);
    }

    public float calculateHourlyRate(double annualSalary, int workingHours, int utilizationPercent, Employee employee) {
        float hourlyRate = (float) (annualSalary / (workingHours * (utilizationPercent / 100.0)));
        hourlyRate = roundToTwoDecimalPlaces(hourlyRate);
        employee.setHourlyRate(hourlyRate);
        return hourlyRate;
    }

    public float calculateDailyRate(int hoursInWorkDay, Employee employee) {
        float dailyRate = calculateHourlyRate(employee.getAnnualSalary(), employee.getWorkingHours(), employee.getUtilizationPercent(), employee) * hoursInWorkDay;
        dailyRate = roundToTwoDecimalPlaces(dailyRate);
        employee.setDailyRate(dailyRate);
        return dailyRate;
    }

    public String getCalculatedHourlyRate(Employee employee) {
        return String.format("%.2f", calculateHourlyRate(employee.getAnnualSalary(), employee.getWorkingHours(), employee.getUtilizationPercent(), employee));
    }

    public String getCalculatedDailyRate(int hoursInWorkDay, Employee employee) {
        return String.format("%.2f", calculateDailyRate(hoursInWorkDay, employee));
    }

    private float roundToTwoDecimalPlaces(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public float calculateDailyMulti(Employee employee) {

        float dailyRate = employee.getDailyRate();
        for (Group group : employee.getTeams()) {
            dailyRate = (dailyRate * ((float) group.getMultiplier() / 100)) + dailyRate;
        }
        return dailyRate;
    }

    public float calculateHourlyMulti(Employee employee) {

        float hourlyRate = employee.getHourlyRate();
        for (Group group : employee.getTeams()) {
            hourlyRate = (hourlyRate * ((float) group.getMultiplier() / 100)) + hourlyRate;
        }
        return hourlyRate;
    }


}
