package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EditProfileController {

    @FXML
    private TextField overheadMultiField;
    @FXML
    private TextField configFixAnnAmountField;
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
    @FXML
    private ImageView newProfileImage;
    private MSController msController;

   private Employee emplyeeToUpdate;

    public void setMsController(MSController msController){
        this.msController=msController;
    }
    public void edit(ActionEvent actionEvent) {

        int annualSalary = Integer.parseInt(annualSalaryField.getText());
        int overheadMultiPercent = Integer.parseInt(overheadMultiField.getText());
        int confFixedAnnualAmount = Integer.parseInt(configFixAnnAmountField.getText());
        String country = countryField.getText();
        String continent = continentField.getText();
        String team = teamField.getText();
        int workingHours = Integer.parseInt(workingHoursField.getText());
        int utilizationPercent = Integer.parseInt(utilPercentField.getText());
        String employeeType = employeeTypeField.getText();



       emplyeeToUpdate.updateEmployee(annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                country, continent, team, workingHours, utilizationPercent, employeeType);
        model.getInstance().editEmployee(emplyeeToUpdate);
        msController.populateTable();



        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setEmplyeeToUpdate(Employee emplyee){
        this.emplyeeToUpdate=emplyee;

        annualSalaryField.setText(String.valueOf(emplyee.getAnnualSalary()));
        overheadMultiField.setText(String.valueOf(emplyee.getOverheadMultiPercent()));
        configFixAnnAmountField.setText(String.valueOf(emplyee.getConfFixedAnnualAmount()));
        countryField.setText(emplyee.getCountry());
        continentField.setText(emplyee.getContinent());
        teamField.setText(emplyee.getTeam());
        workingHoursField.setText(String.valueOf(emplyee.getWorkingHours()));
        utilPercentField.setText(String.valueOf(emplyee.getUtilizationPercent()));
        employeeTypeField.setText(emplyee.getEmployeeType());


    }



}
