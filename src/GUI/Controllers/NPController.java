package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NPController implements Initializable {


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



    //idk
    public void create() {
        if (msController.curentCurency == 0) {
            //EUR
            int annualSalary = Integer.parseInt(annualSalaryField.getText());
            int overheadMultiPercent = Integer.parseInt(overheadMultiField.getText());
            int confFixedAnnualAmount = Integer.parseInt(configFixAnnAmountField.getText());
            String country = countryField.getText();
            String continent = continentField.getText();
            String team = teamField.getText();
            int workingHours = Integer.parseInt(workingHoursField.getText());
            int utilizationPercent = Integer.parseInt(utilPercentField.getText());
            String employeeType = employeeTypeField.getText();
             // multiplied whit exchange rate
            double anualSalaryUSD = annualSalary * msController.EURtoUSDRate;
            double confFixedAnnualAmountUSD = confFixedAnnualAmount * msController.EURtoUSDRate;

            Employee newEmployee = new Employee(annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                    country, continent, team, workingHours, utilizationPercent, employeeType, anualSalaryUSD, confFixedAnnualAmountUSD);

            model.getInstance().createEmployee(newEmployee);

        }if(msController.curentCurency==1) {
            //USD
            //multiplyed withe exchange rate
            double annualSalary = Integer.parseInt(annualSalaryField.getText())* msController.USDtoEURRate;

            int overheadMultiPercent = Integer.parseInt(overheadMultiField.getText());

            double confFixedAnnualAmount = Integer.parseInt(configFixAnnAmountField.getText())* msController.USDtoEURRate;

            String country = countryField.getText();
            String continent = continentField.getText();
            String team = teamField.getText();
            int workingHours = Integer.parseInt(workingHoursField.getText());
            int utilizationPercent = Integer.parseInt(utilPercentField.getText());
            String employeeType = employeeTypeField.getText();
            // it swiches the values because if annualSalary= 0.93 anualSallaryUSD has to be 1
            double anualSalaryUSD =  Integer.parseInt(annualSalaryField.getText());
            double confFixedAnnualAmountUSD = Integer.parseInt(configFixAnnAmountField.getText());

            Employee newEmployee = new Employee(annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                    country, continent, team, workingHours, utilizationPercent, employeeType, anualSalaryUSD, confFixedAnnualAmountUSD);

            model.getInstance().createEmployee(newEmployee);
        }
    }


    //idk
    public void update() {
        if (msc.curentCurency == 0) {
            emplyeeToUpdate.setUtilizationPercent(Integer.parseInt(utilPercentField.getText()));
            emplyeeToUpdate.setTeam(teamField.getText());
            emplyeeToUpdate.setWorkingHours(Integer.parseInt(workingHoursField.getText()));
            emplyeeToUpdate.setConfFixedAnnualAmount(Integer.parseInt(configFixAnnAmountField.getText()));
            emplyeeToUpdate.setOverheadMultiPercent(Integer.parseInt(overheadMultiField.getText()));
            emplyeeToUpdate.setContinent(continentField.getText());
            emplyeeToUpdate.setCountry(countryField.getText());
            emplyeeToUpdate.setAnnualSalary(Integer.parseInt(annualSalaryField.getText()));
            emplyeeToUpdate.setEmployeeType(employeeTypeField.getText());

            double annualSalaryUSD = Double.parseDouble(annualSalaryField.getText()) * msc.EURtoUSDRate;
            emplyeeToUpdate.setAnnualSalaryUSD((int) annualSalaryUSD);
            double confFixedAnnualAmountUSD = Double.parseDouble(configFixAnnAmountField.getText()) * msc.EURtoUSDRate;
            emplyeeToUpdate.setConfFixedAnnualAmountUSD((int) confFixedAnnualAmountUSD);

            model.getInstance().editEmployee(emplyeeToUpdate);
            msc.populateEmpTable();
        }
        if (msc.curentCurency == 1) {
            emplyeeToUpdate.setUtilizationPercent(Integer.parseInt(utilPercentField.getText()));
            emplyeeToUpdate.setTeam(teamField.getText());
            emplyeeToUpdate.setWorkingHours(Integer.parseInt(workingHoursField.getText()));

            double conFixedAnnualAmount=Integer.parseInt(configFixAnnAmountField.getText())* msc.USDtoEURRate;
            emplyeeToUpdate.setConfFixedAnnualAmount( conFixedAnnualAmount);

            emplyeeToUpdate.setOverheadMultiPercent(Integer.parseInt(overheadMultiField.getText()));
            emplyeeToUpdate.setContinent(continentField.getText());
            emplyeeToUpdate.setCountry(countryField.getText());

            double annualSalary = Integer.parseInt(annualSalaryField.getText())* msc.USDtoEURRate;
            emplyeeToUpdate.setAnnualSalary(annualSalary);

            emplyeeToUpdate.setEmployeeType(employeeTypeField.getText());

           emplyeeToUpdate.setAnnualSalaryUSD(Integer.parseInt(annualSalaryField.getText()));
           emplyeeToUpdate.setConfFixedAnnualAmountUSD(Integer.parseInt(configFixAnnAmountField.getText()));



            model.getInstance().editEmployee(emplyeeToUpdate);
            msc.populateEmpTable();
        }
    }


    //idk
    public void setEmployeeToUpdate(Employee employee) {
        this.emplyeeToUpdate = employee;
        if (msc.curentCurency == 0) {
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
        if(msc.curentCurency==1){
            double annualSalaryUSD = employee.getAnnualSalary() * msc.EURtoUSDRate;
            annualSalaryField.setText(String.valueOf(annualSalaryUSD));
            overheadMultiField.setText(String.valueOf(employee.getOverheadMultiPercent()));
            double configFixAnnAmountUSD= employee.getConfFixedAnnualAmount() * msc.EURtoUSDRate;
            configFixAnnAmountField.setText(String.valueOf(configFixAnnAmountUSD));
            countryField.setText(employee.getCountry());
            continentField.setText(employee.getContinent());
            teamField.setText(employee.getTeam());
            workingHoursField.setText(String.valueOf(employee.getWorkingHours()));
            utilPercentField.setText(String.valueOf(employee.getUtilizationPercent()));
            employeeTypeField.setText(employee.getEmployeeType());
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setMSController(MSController msController) {
        this.msc=msController;
    }

}
