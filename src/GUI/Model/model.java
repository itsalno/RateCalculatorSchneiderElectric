package GUI.Model;

import BE.Employee;
import BE.Group;
import BE.Multiplier;
import BLL.EmployeeLogic;
import BLL.GroupLogic;
import BLL.MultiplierLogic;
import BLL.UserLogic;
import Exceptions.RateCalcException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

public class model {

    private double EURtoUSDRate = 1.0775286;
    private double USDtoEURRate = 0;
    private int curentCurency;
    private final static model instance = new model();
    private GroupLogic groupLogic = new GroupLogic();
    private MultiplierLogic multiplierLogic = new MultiplierLogic();
    private EmployeeLogic eLogic = new EmployeeLogic();

    private UserLogic userLogic = new UserLogic();

    private model(){}

    public static model getInstance() {
        return instance;
    }



    // TEAM OPERATIONS

    public void createTeam(Group group) throws RateCalcException {
        groupLogic.createGroup(group);
    }

    public ObservableList<Group> getAllTeams() throws RateCalcException {
        ObservableList<Group> groups = FXCollections.observableArrayList();
        groups.setAll(groupLogic.getAllGroups());
        return groups;
    }

    public Group updateGroupTable(int id) throws RateCalcException {
        return groupLogic.updateGroupTable(id);
    }

    public void deleteTeam(Group group) throws RateCalcException {
        groupLogic.deleteGroup(group);
    }

    public void updateTeam(Group group) throws RateCalcException {
        groupLogic.editGroup(group);
    }

    public void applyMultiplierToGroup(int multiplier, int id) throws RateCalcException {
        groupLogic.applyMultiplierToGroup(multiplier, id);
    }

    // EMPLOYEE OPERATIONS

    private void createEmployee(Employee employee) throws RateCalcException {
        if (employee.getTeams().size() > 2) {
            throw new IllegalArgumentException("An employee cannot be part of more than two teams.");
        }
        eLogic.create(employee);
    }

    private ObservableList<Employee> getAllEmployees() throws RateCalcException {
        ObservableList<Employee> obsList = FXCollections.observableArrayList();
        obsList.setAll(eLogic.getAllEmployees());
        return obsList;
    }

    public void deleteEmployee(Employee employee) throws RateCalcException {
        eLogic.delete(employee);
    }

    public void editEmployee(Employee employee) throws RateCalcException {
        eLogic.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText) throws RateCalcException {
        return eLogic.searchEmployees(searchText);
    }

    public void removeTeamFromEmployee(int id, int tId) throws RateCalcException {
        eLogic.removeTeamFromEmployee(id, tId);
    }


    // MULTIPLIER OPERATIONS

    public void createMulti(Multiplier multiplier) throws RateCalcException {
        multiplierLogic.createMulti(multiplier);
    }

    public ArrayList<Multiplier> getAllMultis() throws RateCalcException {
        return multiplierLogic.getAllMultis();
    }

    public void deleteMulti(int id) throws RateCalcException {
        multiplierLogic.deleteMulti(id);
    }

    public void updateTable(TableView<Multiplier> multiTable) throws RateCalcException {

        ObservableList<Multiplier> multis = FXCollections.observableArrayList();
        multis.setAll(getAllMultis());
        multiTable.setItems(multis);
    }

    // Main controller


    public void populateEmpTable(TableView<Employee> profileTable) throws RateCalcException {

        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.addAll(getAllEmployees());
        profileTable.setItems(employees);
    }

    public float calculateHourlyMulti(Employee employee) {
        return eLogic.calculateHourlyMulti(employee);
    }

    public float calculateDailyMulti(Employee employee) {
        return eLogic.calculateDailyMulti(employee);
    }

    public void populateGrpTable(TableView<Group> groupTable) throws RateCalcException {

        ObservableList<Group> teams = FXCollections.observableArrayList();
        teams.addAll(getAllTeams());
        groupTable.setItems(teams);
    }

    public void searchInfo(TextField searchBar, TableView<Employee> profileTable, String searchText) throws RateCalcException {

        if (searchText == null || searchText.isEmpty()) {
            searchText = searchBar.getText().trim().toLowerCase();
        } else {
            searchText = searchText.trim().toLowerCase();
        }
        List<Employee> matchingEmployees = searchEmployees(searchText);
        profileTable.setItems(FXCollections.observableArrayList(matchingEmployees));
    }

    public void filterEmployeesByTeam(TableView<Employee> profileTable,String searchText) throws RateCalcException {

        if (searchText == null || searchText.isEmpty()) {
            profileTable.setItems(model.getInstance().getAllEmployees());
        } else {
            ObservableList<Employee> filteredList = FXCollections.observableArrayList(
                    model.getInstance().getAllEmployees().stream()
                            .filter(employee -> employee.getTeams().stream()
                                            .map(Group::getName)
                                            .anyMatch(name -> name.contains(searchText)))
                            .collect(Collectors.toList())
            );
            profileTable.setItems(filteredList);
        }
    }

    public int swichCurency(int curentCurency, TableView<Employee> profileTable, ToggleButton curencyBTN, TableColumn<Employee, String> hourlyRateCollumn, TableColumn<Employee, String> dailyRateCollumn) throws RateCalcException {

        curentCurency = (curentCurency + 1) % 2;
        setCurencyLabel(curentCurency, curencyBTN);
        calculateExchange(curentCurency, hourlyRateCollumn, dailyRateCollumn);
        populateEmpTable(profileTable);
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
                String modifiedValueString = String.valueOf(model.getInstance().calculateHourlyMulti(cellData.getValue())).replace(",", ".");

                Double originalValue = Double.valueOf(modifiedValueString);
                df.setRoundingMode(RoundingMode.HALF_UP);
                String modifiedValueAsString = df.format(originalValue);
                return new SimpleStringProperty(modifiedValueAsString + "€");
            });
            dailyRateCollumn.setCellValueFactory(cellData -> {
                String modifiedValueString = String.valueOf(model.getInstance().calculateDailyMulti(cellData.getValue())).replace(',', '.');

                Double originalValue = Double.valueOf(modifiedValueString);
                df.setRoundingMode(RoundingMode.HALF_UP);
                String modifiedValueAsString = df.format(originalValue);
                return new SimpleStringProperty(modifiedValueAsString + "€");
            });
        }
        if (curentCurency == 1) {

            hourlyRateCollumn.setCellValueFactory(cellData -> {
                String modifiedValueString = String.valueOf(model.getInstance().calculateHourlyMulti(cellData.getValue())).replace(",", ".");

                Double originalValue = Double.valueOf(modifiedValueString);
                Double modifiedValue = originalValue * EURtoUSDRate;
                df.setRoundingMode(RoundingMode.HALF_UP);
                String modifiedValueAsString = df.format(modifiedValue);
                return new SimpleStringProperty(modifiedValueAsString + "$");
            });
            dailyRateCollumn.setCellValueFactory(cellData -> {
                String modifiedValueString = String.valueOf(model.getInstance().calculateDailyMulti(cellData.getValue())).replace(',', '.');

                Double originalValue = Double.valueOf(modifiedValueString);
                Double modifiedValue = originalValue * EURtoUSDRate;
                df.setRoundingMode(RoundingMode.HALF_UP);
                String modifiedValueAsString = df.format(modifiedValue);
                return new SimpleStringProperty(modifiedValueAsString + "$");
            });
        }
    }

    public void setUSDtoEURRate(double EURtoUSDRate) {

        double USDtoEURRate1 = (1 - EURtoUSDRate) + 1;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        String formattedValue = df.format(USDtoEURRate1);
        USDtoEURRate = Double.parseDouble(formattedValue);
    }

    // ViewProfController

    public void updateUIInfo(Employee selectedEmployee, Label workingHoursLbl, Label countryLbl, Label continentLbl, Label annualSalaryLbl, Label empTypeLbl,
                             Label utilPercentLbl, Label ompLbl, Label cfaaLbl, Label hourlyRateLbl, Label dailyRateLbl, Label teamLbl, Label namaField) throws RateCalcException {

        if (selectedEmployee != null) {

            workingHoursLbl.setText(String.valueOf(selectedEmployee.getWorkingHours()));
            countryLbl.setText(selectedEmployee.getCountry());
            continentLbl.setText(selectedEmployee.getContinent());
            empTypeLbl.setText(selectedEmployee.getEmployeeType());
            utilPercentLbl.setText(String.valueOf(selectedEmployee.getUtilizationPercent()));
            ompLbl.setText(String.valueOf(selectedEmployee.getOverheadMultiPercent()));
            teamLbl.setText(selectedEmployee.getTeams().stream().map(Group::getName).collect(Collectors.joining(", ")));

            String hourlyRate = String.format("%.2f", selectedEmployee.getHourlyRate());
            String dailyRate = String.format("%.2f", selectedEmployee.getDailyRate());
            hourlyRateLbl.setText(hourlyRate);
            dailyRateLbl.setText(dailyRate);
            namaField.setText(selectedEmployee.getFullName());

                if (curentCurency == 0) {
                    annualSalaryLbl.setText(String.valueOf(selectedEmployee.getAnnualSalary()));
                    cfaaLbl.setText(String.valueOf(selectedEmployee.getConfFixedAnnualAmount()));
                } else {
                    utilPercentLbl.setText(String.valueOf(selectedEmployee.getUtilizationPercent()));
                    cfaaLbl.setText(String.valueOf(eLogic.getConFixAnnAmount(selectedEmployee)));
                    annualSalaryLbl.setText(String.valueOf(eLogic.getAnnualSllaryUSD(selectedEmployee)));
                }
        }
    }

    // NewProfileController

    public void createP(TextField annualSalaryField, TextField overheadMultiField, TextField configFixAnnAmountField, TextField countryField, TextField continentField,
                         ListView<Group> listViewEx, TextField workingHoursField, TextField utilPercentField, TextField employeeTypeField, TextField nameField) throws RateCalcException {

        List<Group> selectedTeams = new ArrayList<>();

        selectedTeams.addAll(listViewEx.getSelectionModel().getSelectedItems());

        // Validate that no more than 2 teams are selected
        if (selectedTeams.size() > 2) {
            throw new IllegalArgumentException("An employee cannot be part of more than two teams.");
        }

        setUSDtoEURRate(EURtoUSDRate);
        int annualSalary = Integer.parseInt(annualSalaryField.getText());
        int overheadMultiPercent = Integer.parseInt(overheadMultiField.getText());
        int confFixedAnnualAmount = Integer.parseInt(configFixAnnAmountField.getText());
        String country = countryField.getText();
        String continent = continentField.getText();
        int workingHours = Integer.parseInt(workingHoursField.getText());
        int utilizationPercent = Integer.parseInt(utilPercentField.getText());
        String employeeType = employeeTypeField.getText();
        String fullName = nameField.getText();

        float annualSalaryUSD;
        float confFixedAnnualAmountUSD;

        if (curentCurency == 0) {
            annualSalaryUSD = (float) (annualSalary * EURtoUSDRate);
            confFixedAnnualAmountUSD = (float) (confFixedAnnualAmount * EURtoUSDRate);
        } else {
            annualSalaryUSD = annualSalary;
            confFixedAnnualAmountUSD = confFixedAnnualAmount;
            annualSalary = (int) (annualSalary * USDtoEURRate);
            confFixedAnnualAmount = (int) (confFixedAnnualAmount * USDtoEURRate);
        }

        Employee newEmployee = new Employee(0, fullName, annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                country, continent, selectedTeams, workingHours, utilizationPercent, employeeType,
                annualSalaryUSD, confFixedAnnualAmountUSD);

        createEmployee(newEmployee);
    }

    public void updateP(Employee employeeToUpdate, TextField annualSalaryField, TextField overheadMultiField, TextField configFixAnnAmountField, TextField countryField, TextField continentField,
                        ListView<Group> listViewEx, TextField workingHoursField, TextField utilPercentField, TextField employeeTypeField, TextField nameField) throws RateCalcException {

        // Collect selected teams from ListView
        List<Group> selectedTeams = new ArrayList<>();

        selectedTeams.addAll(listViewEx.getSelectionModel().getSelectedItems());

        // Validate that no more than 2 teams are selected
        if (selectedTeams.size() > 2) {
            throw new IllegalArgumentException("An employee cannot be part of more than two teams.");
        }

        // Update employee with new values
        employeeToUpdate.setUtilizationPercent(Integer.parseInt(utilPercentField.getText()));
        employeeToUpdate.setTeams(selectedTeams);
        employeeToUpdate.setWorkingHours(Integer.parseInt(workingHoursField.getText()));
        employeeToUpdate.setOverheadMultiPercent(Integer.parseInt(overheadMultiField.getText()));
        employeeToUpdate.setContinent(continentField.getText());
        employeeToUpdate.setCountry(countryField.getText());
        employeeToUpdate.setEmployeeType(employeeTypeField.getText());
        employeeToUpdate.setFullName(nameField.getText());

        if (curentCurency == 0) {
            double annualSalary = Double.parseDouble(annualSalaryField.getText());
            double confFixedAnnualAmount = Double.parseDouble(configFixAnnAmountField.getText());

            employeeToUpdate.setAnnualSalary(annualSalary);
            employeeToUpdate.setConfFixedAnnualAmount(confFixedAnnualAmount);

            employeeToUpdate.setAnnualSalaryUSD(annualSalary * EURtoUSDRate);
            employeeToUpdate.setConfFixedAnnualAmountUSD(confFixedAnnualAmount * EURtoUSDRate);
        } else {
            double conFixedAnnualAmount = Double.parseDouble(configFixAnnAmountField.getText()) * USDtoEURRate;
            double annualSalary = Double.parseDouble(annualSalaryField.getText()) * USDtoEURRate;

            employeeToUpdate.setAnnualSalary(annualSalary);
            employeeToUpdate.setConfFixedAnnualAmount(conFixedAnnualAmount);

            employeeToUpdate.setAnnualSalaryUSD(Double.parseDouble(annualSalaryField.getText()));
            employeeToUpdate.setConfFixedAnnualAmountUSD(Double.parseDouble(configFixAnnAmountField.getText()));
        }

        editEmployee(employeeToUpdate);
    }

    public void setEmployeeToUpdateM(Employee employee, TextField annualSalaryField, TextField overheadMultiField, TextField configFixAnnAmountField, TextField countryField, TextField continentField,
                                     ListView<Group> listViewEx, TextField workingHoursField, TextField utilPercentField, TextField employeeTypeField, TextField nameField) throws RateCalcException {

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
        if (curentCurency == 1) {
            configFixAnnAmountField.setText(String.valueOf(eLogic.getConFixAnnAmount(employee)));
            annualSalaryField.setText(String.valueOf(eLogic.getAnnualSllaryUSD(employee)));
        }
    }

    public void updateGroupTable(Group group, TableView<Group> groupTable) {

        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            int selectedIndex = groupTable.getSelectionModel().getSelectedIndex();
            groupTable.getItems().remove(selectedGroup);
            groupTable.getItems().add(selectedIndex, group);
        }
    }

    public void setImage(ImageView imageview) {

        Image img = new Image("file:src/SchneiderLogo.png");
        imageview.setImage(img);
    }

    //Log in operation

    public boolean checkUser(String username, String password) throws RateCalcException {
        return userLogic.checkUser(username, password);
    }

}