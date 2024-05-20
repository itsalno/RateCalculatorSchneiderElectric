package BLL;

import BE.Employee;
import DAL.EmployeeDAO;
import javafx.collections.ObservableList;

import java.util.List;

public class EmployeeLogic {

    EmployeeDAO eDAO=new EmployeeDAO();

    public void create(Employee employee){
        eDAO.create(employee);
    }

    public List<Employee> getAllEmployees() {
        return eDAO.getAllEmployees();
    }

    public void delete(Employee employee) {
        eDAO.delete(employee);
    }
    public void edit(Employee employee){
        eDAO.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText){
        return eDAO.searchEmployees(searchText);
    }

    public int getAnnualSllaryUSD(Employee employee){
       return eDAO.getAnuallSalaryUSD(employee);
    }
    public int getConFixAnnAmount(Employee employee){
        return eDAO.getConFixAmountUSD(employee);
    }
    public void removeTeamFromEmployee(int id, int tId){
        eDAO.removeTeamFromEmployee(id, tId);
    }
    public Employee getEmployeeById(int id){
        return eDAO.getEmployeeById(id);
    }


}
