package DAL;

import BE.Employee;
import Exceptions.RateCalcException;
import javafx.collections.ObservableList;

import java.util.List;

public interface IEmployeeDAO {

    void create(Employee employee)throws RateCalcException;

    ObservableList<Employee> getAllEmployees()throws RateCalcException;
    void delete(Employee employee)throws RateCalcException;
    void edit(Employee employee)throws RateCalcException ;
    List<Employee> searchEmployees(String searchText)throws RateCalcException;

    int getAnuallSalaryUSD(Employee e)throws RateCalcException;
    int getConFixAmountUSD(Employee e)throws RateCalcException;
    void removeTeamFromEmployee(int id, int tId)throws RateCalcException;
    Employee getEmployeeById(int id)throws RateCalcException;

}
