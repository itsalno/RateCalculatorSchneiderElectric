package GUI.Controllers;

import BE.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProfController implements Initializable {


    @FXML
    private ImageView profileInfoImage;
    @FXML
    private Label workingHoursLbl,countryLbl,continentLbl,annualSalaryLbl,empTypeLbl,
            utilPercentLbl, ompLbl,cfaaLbl,hourlyRateLbl,dailyRateLbl,teamLbl;
    private Employee selectedEmployee;

    private MSController msController;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img =new Image("file:src/SchneiderLogo.png");
        //model
        profileInfoImage.setImage(img);
        updateUIInfo();
    }
    public void setMsController(MSController msController){
        this.msController=msController;
    }


    public void setEmployee(Employee employee) {
        this.selectedEmployee = employee;
        updateUIInfo();
    }



    //idk
    public void updateUIInfo() {
        if (selectedEmployee != null) {


            if (msController.curentCurency == 0) {

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
                teamLbl.setText(selectedEmployee.getTeam());

            }
            if (msController.curentCurency == 1) {
                workingHoursLbl.setText(String.valueOf(selectedEmployee.getWorkingHours()));
                countryLbl.setText(selectedEmployee.getCountry());
                continentLbl.setText(selectedEmployee.getContinent());
                double annualSalaryUSD = selectedEmployee.getAnnualSalary() * msController.EURtoUSDRate;
                annualSalaryLbl.setText(String.valueOf(annualSalaryUSD));
                empTypeLbl.setText(selectedEmployee.getEmployeeType());
                utilPercentLbl.setText(String.valueOf(selectedEmployee.getUtilizationPercent()));
                ompLbl.setText(String.valueOf(selectedEmployee.getOverheadMultiPercent()));
                double configFixedAnnualAmaount= selectedEmployee.getConfFixedAnnualAmount()* msController.EURtoUSDRate;
                cfaaLbl.setText(String.valueOf(configFixedAnnualAmaount));
                hourlyRateLbl.setText(selectedEmployee.getHourlyRate());
                dailyRateLbl.setText(selectedEmployee.getDailyRate());
                teamLbl.setText(selectedEmployee.getTeam());
            }
        }
    }


    public void close(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
