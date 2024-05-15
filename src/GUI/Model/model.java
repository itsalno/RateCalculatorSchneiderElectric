package GUI.Model;


import BE.Employee;
import BE.Multiplier;
import BLL.EmployeeLogic;
import BLL.GroupLogic;
import BLL.MultiplierLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import BE.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class model {

    private double EURtoUSDRate = 1.0775286;

    private double USDtoEURRate= 0;
    private int curentCurency;
    private final static model instance = new model();
    private GroupLogic groupLogic;

    private MultiplierLogic multiplierLogic = new MultiplierLogic();

    private EmployeeLogic eLogic= new EmployeeLogic();

    public model() {
        this.groupLogic = new GroupLogic();
    }

    public static model getInstance(){
        return instance;
    }




    //TEAM OPERATIONS

    public void createTeam(Group group){
        groupLogic.createGroup(group);
    }

    public ObservableList<Group> getAllTeams(){
        return groupLogic.getAllGroups();
    }
    public Group updateGroupTable(int id){
        return groupLogic.updateGroupTable(id);
    }
    public void deleteTeam(Group group){
        groupLogic.deleteGroup(group);
    }
    public void updateTeam(Group group){
        groupLogic.editGroup(group);
    }

    public void applyMultiplierToGroup(int multiplier, int id){
        groupLogic.applyMultiplierToGroup(multiplier, id);
    }
    public void updateTeamTable(TableView<Group> groupTable){

    }



    //EMPLOYEE OPERATIONS
    public void createEmployee(Employee employee){
        eLogic.create(employee);
    }
    public ObservableList<Employee> getAllEmployees() {
        return eLogic.getAllEmployees();
    }

    public void deleteEmployee(Employee employee) {
        eLogic.delete(employee);
    }

    public void editEmployee(Employee employee) {
        eLogic.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText){
        return eLogic.searchEmployees(searchText);
    }

    public void removeTeamFromEmployee(int id){
        eLogic.removeTeamFromEmployee(id);
    }
    public Employee getEmployeeById(int id){
        return eLogic.getEmployeeById(id);
    }

    //MULTIPLIER OPERATIONS

    public void createMulti(Multiplier multiplier){multiplierLogic.createMulti(multiplier);}

    public ArrayList<Multiplier> getAllMultis(){
        return multiplierLogic.getAllMultis();
    }

    public void deleteMulti(int id){multiplierLogic.deleteMulti(id);}

    public void editMulti(Multiplier multiplier){multiplierLogic.editMultiplier(multiplier);}

////////////////////////////////////////////////////////////

    public void updateTable(TableView<Multiplier> multiTable){
        ObservableList<Multiplier> multis = FXCollections.observableArrayList();
        multis.setAll(getAllMultis());
        multiTable.setItems(multis);
    }

    //Main controller/////////////////////////////////////

    //Give it our own exception
    public void populateEmpTable(TableView<Employee> profileTable) {

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        List<Group> group = new LinkedList<Group>(getAllTeams());
        group.addAll(groupLogic.getAllGroups());

        employees.addAll(getAllEmployees());
        for (Employee employee:employees) {
            System.out.println(employee.getTeamId()+"AAAAAAA");
        }
        for (Group group1:group) {
            System.out.println(group1.getId()+"BBBBBBB");
        }

        for (Employee employee:employees) {
            for (Group group1:group) {
                if(employee.getTeamId()==group1.getId()){
                    double hourlyRate = Double.parseDouble(employee.getHourlyRate());
                    employee.setHourlyRate(String.valueOf(hourlyRate * ((double) group1.getMultiplier() / 100)) + hourlyRate);
                    System.out.println(employee.getTeamId());
                    System.out.println(group1.getId());
                }
            }
        }

        profileTable.setItems(employees);
    }

    public void populateGrpTable(TableView<Group> groupTable ) {
        ObservableList<Group> teams = FXCollections.observableArrayList();
        teams.addAll(getAllTeams());
        groupTable.setItems(teams);
    }

    public void searchInfo(TextField searchBar, TableView<Employee> profileTable, String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            searchText = searchBar.getText().trim().toLowerCase();
        } else {
            searchText = searchText.trim().toLowerCase();
        }
        List<Employee> matchingEmployees = searchEmployees(searchText);
        profileTable.setItems(FXCollections.observableArrayList(matchingEmployees));
    }

    public int swichCurency(int curentCurency, TableView<Employee> profileTable, ToggleButton curencyBTN, TableColumn<Employee, String> hourlyRateCollumn, TableColumn<Employee, String> dailyRateCollumn){
        // this method swches curentCurency int between 1 and 0.
        // Each number represent a curency and everything is set acordingly to that curency.
        curentCurency = (curentCurency + 1) % 2;

        setCurencyLabel(curentCurency, curencyBTN);

        calculateExchange(curentCurency,hourlyRateCollumn, dailyRateCollumn);

        profileTable.refresh();
        this.curentCurency = curentCurency;
        return curentCurency;
    }

    private void setCurencyLabel(int curentCurency, ToggleButton curencyBTN) {
        if (curentCurency == 0) {
            curencyBTN.setText("EUR");
        }
        if (curentCurency == 1) {
            curencyBTN.setText("USD");
        }
    }

    private void calculateExchange(int curentCurency, TableColumn<Employee, String> hourlyRateCollumn, TableColumn<Employee, String> dailyRateCollumn) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (curentCurency == 0) {
            hourlyRateCollumn.setCellValueFactory(cellData -> {
                String originalValueString = cellData.getValue().getCalculatedHourlyRate();
                // Replace commas with periods if present because math would work with commas
                String modifiedValueString = originalValueString.replace(',', '.');
                // Parse the modified string to a double
                Double originalValue = Double.valueOf(modifiedValueString);

                df.setRoundingMode(RoundingMode.HALF_UP); // Round to nearest cent
                // Convert the original value to a string
                String modifiedValueAsString = df.format(originalValue);
                //we are not multiplying because this is the original value and the defolt is EUR
                //so when we come back from dollar we want the value that was already there
                return new SimpleStringProperty(modifiedValueAsString + "€");
            });
            dailyRateCollumn.setCellValueFactory(cellData -> {
                //has to be changed to accomodate different hours in work days
                String originalValueString = cellData.getValue().getCalculatedDailyRate(8);
                // Replace commas with periods if present because math would work with commas
                String modifiedValueString = originalValueString.replace(',', '.');
                // Parse the modified string to a double
                Double originalValue = Double.valueOf(modifiedValueString);
                df.setRoundingMode(RoundingMode.HALF_UP); // Round to nearest cent
                // Convert the original value to a string
                String modifiedValueAsString = df.format(originalValue);
                //we are not multiplying because this is the original value and the defolt is EUR
                //so when we come back from dollar we want the value that was already there
                return new SimpleStringProperty(modifiedValueAsString + "€");
            });
        }
        if (curentCurency == 1) {
            hourlyRateCollumn.setCellValueFactory(cellData -> {
                String originalValueString = cellData.getValue().getCalculatedHourlyRate();
                // Replace commas with periods if present
                String modifiedValueString = originalValueString.replace(',', '.');
                // Parse the modified string to a double
                Double originalValue = Double.valueOf(modifiedValueString);
                // Multiply the original value by conversion rate
                Double modifiedValue = originalValue * EURtoUSDRate;
                df.setRoundingMode(RoundingMode.HALF_UP); // Round to nearest cent
                // Convert the modified value to a string
                String modifiedValueAsString = df.format(modifiedValue);
                // Return the modified value as an ObservableValue<String>
                return new SimpleStringProperty( modifiedValueAsString + "$");
            });
            dailyRateCollumn.setCellValueFactory(cellData -> {
                //has to be changed to accomodate different hours in work days
                String originalValueString = cellData.getValue().getCalculatedDailyRate(8);
                // Replace commas with periods if present
                String modifiedValueString = originalValueString.replace(',', '.');
                // Parse the modified string to a double
                Double originalValue = Double.valueOf(modifiedValueString);
                // Multiply the original value by conversion rate
                Double modifiedValue = originalValue * EURtoUSDRate;
                df.setRoundingMode(RoundingMode.HALF_UP); // Round to nearest cent
                // Convert the modified value to a string
                String modifiedValueAsString = df.format(modifiedValue);
                // Return the modified value as an ObservableValue<String>
                return new SimpleStringProperty(modifiedValueAsString + "$");
            });
        }
    }

    public void setUSDtoEURRate(double EURtoUSDRate){
        //this method sets USDtoEUR exchange its done as a method because if we change EURtoUSD the USDtoEUR changes
        //this setting is accured when the program starts in initialize method
        //this is the formula so we dont have to change it twice if the rate of exchane changes
        double USDtoEURRate1 = (1 - EURtoUSDRate) + 1;

        // Set the locale to use a period as the decimal separator ad not a comma because it gives a error
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);

        // Format the double value with the specified decimal format
        String formattedValue = df.format(USDtoEURRate1);
        USDtoEURRate = Double.parseDouble(formattedValue);
    }


    //ViewProfController//////////////////////

    public void updateUIInfo(Employee selectedEmployee, Label workingHoursLbl, Label countryLbl, Label continentLbl, Label annualSalaryLbl, Label empTypeLbl,
                             Label utilPercentLbl, Label ompLbl, Label cfaaLbl, Label hourlyRateLbl, Label dailyRateLbl, Label teamLbl,Label namaField) {
        if (selectedEmployee != null) {

            workingHoursLbl.setText(String.valueOf(selectedEmployee.getWorkingHours()));
            countryLbl.setText(selectedEmployee.getCountry());
            continentLbl.setText(selectedEmployee.getContinent());
            empTypeLbl.setText(selectedEmployee.getEmployeeType());
            utilPercentLbl.setText(String.valueOf(selectedEmployee.getUtilizationPercent()));
            ompLbl.setText(String.valueOf(selectedEmployee.getOverheadMultiPercent()));
            teamLbl.setText(selectedEmployee.getTeam());

            String hourlyRate = String.format("%.2f", Double.parseDouble(selectedEmployee.getHourlyRate()));
            String dailyRate = String.format("%.2f", Double.parseDouble(selectedEmployee.getDailyRate()));
            hourlyRateLbl.setText(hourlyRate);
            dailyRateLbl.setText(dailyRate);
            namaField.setText(selectedEmployee.getFullName());

            if (curentCurency == 0) {
                annualSalaryLbl.setText(String.valueOf(selectedEmployee.getAnnualSalary()));
                cfaaLbl.setText(String.valueOf(selectedEmployee.getConfFixedAnnualAmount()));
            }

            else{
                utilPercentLbl.setText(String.valueOf(selectedEmployee.getUtilizationPercent()));
                cfaaLbl.setText(String.valueOf(eLogic.getConFixAnnAmount(selectedEmployee)));
                annualSalaryLbl.setText(String.valueOf(eLogic.getAnnualSllaryUSD(selectedEmployee)));;
            }
        }
    }

    //NewProfileController///////////////////////////////////

    public void createP(TextField annualSalaryField, TextField overheadMultiField, TextField configFixAnnAmountField, TextField countryField, TextField continentField,
                        ChoiceBox<Group> teamChoiceBox, TextField workingHoursField, TextField utilPercentField, TextField employeeTypeField , TextField  nameField) {
        setUSDtoEURRate(EURtoUSDRate);
        int annualSalary = Integer.parseInt(annualSalaryField.getText());
        int overheadMultiPercent = Integer.parseInt(overheadMultiField.getText());
        int confFixedAnnualAmount = Integer.parseInt(configFixAnnAmountField.getText());
        String country = countryField.getText();
        String continent = continentField.getText();
        String team = String.valueOf(teamChoiceBox.getSelectionModel().getSelectedItem());
        int workingHours = Integer.parseInt(workingHoursField.getText());
        int utilizationPercent = Integer.parseInt(utilPercentField.getText());
        String employeeType = employeeTypeField.getText();
        String fullName=nameField.getText();

        double anualSalaryUSD;
        double confFixedAnnualAmountUSD;

        if (curentCurency == 0) {
            // EUR
            anualSalaryUSD = annualSalary * EURtoUSDRate;
            confFixedAnnualAmountUSD = confFixedAnnualAmount * EURtoUSDRate;
        } else {
            // USD
            anualSalaryUSD = annualSalary;
            confFixedAnnualAmountUSD = confFixedAnnualAmount;
            annualSalary = (int) (annualSalary * USDtoEURRate);
            confFixedAnnualAmount = (int) (confFixedAnnualAmount * USDtoEURRate);
        }

        Employee newEmployee = new Employee(fullName,annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                country, continent, team, workingHours, utilizationPercent, employeeType,
                anualSalaryUSD, confFixedAnnualAmountUSD);

        createEmployee(newEmployee);
    }

    public void updateP(Employee emplyeeToUpdate, TextField annualSalaryField, TextField overheadMultiField, TextField configFixAnnAmountField, TextField countryField, TextField continentField,
                        ChoiceBox<Group> teamChoiceBox, TextField workingHoursField, TextField utilPercentField, TextField employeeTypeField, TextField nameField) {
        emplyeeToUpdate.setUtilizationPercent(Integer.parseInt(utilPercentField.getText()));
        emplyeeToUpdate.setTeam(String.valueOf(teamChoiceBox.getSelectionModel().getSelectedItem()));
        emplyeeToUpdate.setWorkingHours(Integer.parseInt(workingHoursField.getText()));
        emplyeeToUpdate.setOverheadMultiPercent(Integer.parseInt(overheadMultiField.getText()));
        emplyeeToUpdate.setContinent(continentField.getText());
        emplyeeToUpdate.setCountry(countryField.getText());
        emplyeeToUpdate.setEmployeeType(employeeTypeField.getText());
        emplyeeToUpdate.setFullName(nameField.getText());

        if (curentCurency == 0) {
            double annualSalary = Double.parseDouble(annualSalaryField.getText());
            double confFixedAnnualAmount = Double.parseDouble(configFixAnnAmountField.getText());

            emplyeeToUpdate.setAnnualSalary(annualSalary);
            emplyeeToUpdate.setConfFixedAnnualAmount(confFixedAnnualAmount);

            emplyeeToUpdate.setAnnualSalaryUSD((int) (annualSalary * EURtoUSDRate));
            emplyeeToUpdate.setConfFixedAnnualAmountUSD((int) (confFixedAnnualAmount * EURtoUSDRate));
        } else{
            double conFixedAnnualAmount = Double.parseDouble(configFixAnnAmountField.getText()) * USDtoEURRate;
            double annualSalary = Double.parseDouble(annualSalaryField.getText()) * USDtoEURRate;

            emplyeeToUpdate.setAnnualSalary(annualSalary);
            emplyeeToUpdate.setConfFixedAnnualAmount(conFixedAnnualAmount);

            emplyeeToUpdate.setAnnualSalaryUSD(Double.parseDouble(annualSalaryField.getText()));
            emplyeeToUpdate.setConfFixedAnnualAmountUSD(Double.parseDouble(configFixAnnAmountField.getText()));
        }

        editEmployee(emplyeeToUpdate);
    }

    public void setEmployeeToUpdateM(Employee employee, TextField annualSalaryField, TextField overheadMultiField, TextField configFixAnnAmountField, TextField countryField, TextField continentField,
                                    ChoiceBox<Group> teamChoiceBox, TextField workingHoursField, TextField utilPercentField, TextField employeeTypeField,TextField nameField) {
        overheadMultiField.setText(String.valueOf(employee.getOverheadMultiPercent()));
        countryField.setText(employee.getCountry());
        continentField.setText(employee.getContinent());
        workingHoursField.setText(String.valueOf(employee.getWorkingHours()));
        utilPercentField.setText(String.valueOf(employee.getUtilizationPercent()));
        employeeTypeField.setText(employee.getEmployeeType());
        nameField.setText(employee.getFullName());
        if (curentCurency == 0) {
            annualSalaryField.setText(String.valueOf(employee.getAnnualSalary()));
            configFixAnnAmountField.setText(String.valueOf(employee.getConfFixedAnnualAmount()));

        }
        if(curentCurency==1) {
            configFixAnnAmountField.setText(String.valueOf(eLogic.getConFixAnnAmount(employee)));
            annualSalaryField.setText(String.valueOf(eLogic.getAnnualSllaryUSD(employee)));;
        }
    }
public void updateGroupTable(Group group, TableView<Group> groupTable){
    // Get the selected group
    Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
    if (selectedGroup != null) {
        // Get the index of the selected group
        int selectedIndex = groupTable.getSelectionModel().getSelectedIndex();

        // Remove the selected group
        groupTable.getItems().remove(selectedGroup);

        // Add the updated group at the same index
        groupTable.getItems().add(selectedIndex, group);
    }
}


    public void setImage(ImageView imageview){
        Image img =new Image("file:src/SchneiderLogo.png");
        imageview.setImage(img);
    }


}
