package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NPController implements Initializable {


    public Label curency;
    public Label curency1;
    @FXML
    private ImageView newProfileImage;
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

    private MSController msc;
    private Employee emplyeeToUpdate;
   private MSController msController;


   public void setMsController(MSController msController){
       this.msController=msController;
   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/SchneiderLogo.png");
        newProfileImage.setImage(img);







         /*   if (msc.curentCurrency == 0) {
                curency.setText("€");
                curency1.setText("€");
            }
            if (msc.curentCurency == 1) {
                curency.setText("$");
                curency1.setText("$");
            }*/
        }



    public void create(ActionEvent actionEvent) {
        if(emplyeeToUpdate!=null){
            update();
        }else {
            create();
        }

        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

    }


    public void create(){
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
    }

    public void update(){
        emplyeeToUpdate.setUtilizationPercent(Integer.parseInt(utilPercentField.getText()));
        emplyeeToUpdate.setTeam(teamField.getText());
        emplyeeToUpdate.setWorkingHours(Integer.parseInt(workingHoursField.getText()));
        emplyeeToUpdate.setConfFixedAnnualAmount(Integer.parseInt(configFixAnnAmountField.getText()));
        emplyeeToUpdate.setOverheadMultiPercent(Integer.parseInt(overheadMultiField.getText()));
        emplyeeToUpdate.setContinent(continentField.getText());
        emplyeeToUpdate.setCountry(countryField.getText());
        emplyeeToUpdate.setAnnualSalary(Integer.parseInt(annualSalaryField.getText()));
        emplyeeToUpdate.setEmployeeType(employeeTypeField.getText());


        model.getInstance().editEmployee(emplyeeToUpdate);
        msc.populateEmpTable();
    }



    public void setEmployeeToUpdate(Employee employee){
        this.emplyeeToUpdate=employee;

        annualSalaryField.setText(String.valueOf(employee.getAnnualSalary()));
        overheadMultiField.setText(String.valueOf(employee.getOverheadMultiPercent()));
        configFixAnnAmountField.setText(String.valueOf(employee.getConfFixedAnnualAmount()));
        countryField.setText(employee.getCountry());
        continentField.setText(employee.getContinent());
        teamField.setText(employee.getTeam());
        workingHoursField.setText(String.valueOf(employee.getWorkingHours()));
        utilPercentField.setText(String.valueOf(employee.getUtilizationPercent()));
        employeeTypeField.setText(employee.getEmployeeType());


    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setMSController(MSController msController) {
        this.msc=msController;
    }
}
