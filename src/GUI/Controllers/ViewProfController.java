package GUI.Controllers;

import BE.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProfController implements Initializable {


    @FXML
    private Label workingHoursLbl,countryLbl,continentLbl,annualSalaryLbl,empTypeLbl,
            utilPercentLbl, ompLbl,cfaaLbl,hourlyRateLbl,dailyRateLbl;
    private Employee selectedEmployee;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUIInfo();
    }
    public void setEmployee(Employee employee) {
        this.selectedEmployee = employee;
    }

    public void updateUIInfo() {
        if (selectedEmployee != null) {

            workingHoursLbl.setText(String.valueOf(selectedEmployee.getWorkingHours()));
            countryLbl.setText(selectedEmployee.getCountry());
            continentLbl.setText(selectedEmployee.getContinent());
            annualSalaryLbl.setText(String.valueOf(selectedEmployee.getAnnualSalary()));
            empTypeLbl.setText(selectedEmployee.getEmployeeType());
            utilPercentLbl.setText(String.valueOf(selectedEmployee.getUtilizationPercent()));
            ompLbl.setText(String.valueOf(selectedEmployee.getOverheadMultiPercent()));
            cfaaLbl.setText(String.valueOf(selectedEmployee.getConfFixedAnnualAmount()));
            hourlyRateLbl.setText(selectedEmployee.getHourlyRate());
            dailyRateLbl.setText(selectedEmployee.getDailyRate());

        }
    }
    public void close(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
