package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NPController {

    @FXML
    private TextField configFixAnnAmountField;
    @FXML
    private TextField overheadMultiField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField teamField;
    @FXML
    private TextField annualSalaryField;
    @FXML
    private TextField utilPercentField;
    @FXML
    private TextField workingHoursField;
    @FXML
    private TextField continentField;
    @FXML
    private TextField employeeTypeField;
    model model=new model();
    MSController msc=new MSController();





    public void create(ActionEvent actionEvent) {

        int annualSalary = Integer.parseInt(annualSalaryField.getText());
        int overheadMultiPercent = Integer.parseInt(overheadMultiField.getText());
        int confFixedAnnualAmount = Integer.parseInt(configFixAnnAmountField.getText());
        String country = countryField.getText();
        String continent = continentField.getText();
        String team = teamField.getText();
        int workingHours = Integer.parseInt(workingHoursField.getText());
        int utilizationPercent = Integer.parseInt(utilPercentField.getText());
        String employeeType = employeeTypeField.getText();

        Employee newEmployee = new Employee(annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                country, continent, team, workingHours, utilizationPercent, employeeType);

        model.createEmployee(newEmployee);



        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
