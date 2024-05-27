package GUI.Controllers;

import BE.Employee;
import Exceptions.RateCalcException;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProfController implements Initializable {


    @FXML
    private Label nameField;
    @FXML
    private ImageView profileInfoImage;
    @FXML
    private Label workingHoursLbl,countryLbl,continentLbl,annualSalaryLbl,empTypeLbl,
            utilPercentLbl, ompLbl,cfaaLbl,hourlyRateLbl,dailyRateLbl,teamLbl;
    private Employee selectedEmployee;

    private MSController msController;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.getInstance().setImage(profileInfoImage);

        try {
            model.getInstance().updateUIInfo(selectedEmployee, workingHoursLbl, countryLbl, continentLbl, annualSalaryLbl, empTypeLbl, utilPercentLbl, ompLbl, cfaaLbl, hourlyRateLbl, dailyRateLbl, teamLbl,nameField);
        } catch (RateCalcException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }
    public void setMsController(MSController msController){
        this.msController=msController;
    }


    public void setEmployee(Employee employee) throws RateCalcException {
        this.selectedEmployee = employee;
            model.getInstance().updateUIInfo(selectedEmployee, workingHoursLbl, countryLbl, continentLbl, annualSalaryLbl, empTypeLbl, utilPercentLbl, ompLbl, cfaaLbl, hourlyRateLbl, dailyRateLbl, teamLbl,nameField);
    }

    public void close(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
