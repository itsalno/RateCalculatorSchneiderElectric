package BLL;

import BE.Employee;
import DAL.EmployeeDAO;
import Exceptions.RateCalcException;


import java.util.List;

public class EmployeeLogic {

    EmployeeDAO eDAO=new EmployeeDAO();

    public void create(Employee employee) throws RateCalcException {
        eDAO.create(employee);
    }

    public List<Employee> getAllEmployees() throws RateCalcException {
        return eDAO.getAllEmployees();
    }

    public void delete(Employee employee) {
        eDAO.delete(employee);
    }
    public void edit(Employee employee){
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


}
