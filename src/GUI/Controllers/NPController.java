package GUI.Controllers;

import BE.Employee;
import BE.Group;
import GUI.Model.model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class NPController implements Initializable {


    @FXML
    private TextField nameField;
    @FXML
    private ListView<Group> ListViewEx;
    @FXML
    private ChoiceBox<Group> teamChoiceBox;
    @FXML
    private ImageView newProfileImage;
    @FXML
    private TextField configFixAnnAmountField;
    @FXML
    private TextField overheadMultiField;
    @FXML
    private TextField countryField;
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
        model.getInstance().setImage(newProfileImage);
        teamChoiceBox.getItems().addAll((model.getInstance().getAllTeams()));
        ListViewEx.getItems().addAll(model.getInstance().getAllTeams());
        ListViewEx.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }



    public void create(ActionEvent actionEvent) {
        if(emplyeeToUpdate!=null){
            model.getInstance().updateP(emplyeeToUpdate,annualSalaryField, overheadMultiField, configFixAnnAmountField,
                    countryField, continentField, teamChoiceBox, workingHoursField, utilPercentField, employeeTypeField,nameField);
        }else {
            model.getInstance().createP(annualSalaryField, overheadMultiField, configFixAnnAmountField,
                    countryField, continentField, teamChoiceBox, workingHoursField, utilPercentField, employeeTypeField,nameField);
        }
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

    }

    //idk
    public void setEmployeeToUpdate(Employee employee) {
        this.emplyeeToUpdate = employee;
        model.getInstance().setEmployeeToUpdateM(emplyeeToUpdate,annualSalaryField, overheadMultiField,configFixAnnAmountField,
                countryField, continentField, teamChoiceBox, workingHoursField, utilPercentField, employeeTypeField,nameField);
    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setMSController(MSController msController) {
        this.msc=msController;
    }

}
