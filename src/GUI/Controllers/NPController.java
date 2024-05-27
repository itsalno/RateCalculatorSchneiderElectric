package GUI.Controllers;

import BE.Employee;
import BE.Group;
import GUI.Notifications;
import Exceptions.RateCalcException;
import GUI.Model.model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
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
    private Notifications nt=new Notifications();





   public void setMsController(MSController msController){
       this.msController=msController;
   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.getInstance().setImage(newProfileImage);
        ObservableList<Group> allTeams = null;
        try {
            allTeams = model.getInstance().getAllTeams();
        } catch (RateCalcException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }
        //teamChoiceBox.setItems(allTeams);
        ListViewEx.setItems(allTeams);
        ListViewEx.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void create(ActionEvent actionEvent) {
       try {
           if (!fieldCheck()) {
               nt.showError("You have left an empty field!");
               return;
           }

           if (emplyeeToUpdate != null) {
               model.getInstance().updateP(emplyeeToUpdate, annualSalaryField, overheadMultiField, configFixAnnAmountField,
                       countryField, continentField, ListViewEx, workingHoursField, utilPercentField, employeeTypeField, nameField);

           } else model.getInstance().createP(annualSalaryField, overheadMultiField, configFixAnnAmountField,
                   countryField, continentField, ListViewEx, workingHoursField, utilPercentField, employeeTypeField, nameField);

       }catch (RateCalcException e){
           Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
           e.printStackTrace();
           a.show();
       }
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        nt.showSuccess("Successfully created/updated an employee");
    }

    public void setEmployeeToUpdate(Employee employee) throws RateCalcException {
        this.emplyeeToUpdate = employee;
        model.getInstance().setEmployeeToUpdateM(emplyeeToUpdate, annualSalaryField, overheadMultiField, configFixAnnAmountField,
                countryField, continentField, teamChoiceBox, ListViewEx, workingHoursField, utilPercentField, employeeTypeField, nameField);
    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setMSController(MSController msController) {
        this.msc = msController;
    }

    public boolean fieldCheck() {
        if (annualSalaryField.getText().isEmpty() ||
                overheadMultiField.getText().isEmpty() ||
                configFixAnnAmountField.getText().isEmpty() ||
                countryField.getText().isEmpty() ||
                continentField.getText().isEmpty() ||
                ListViewEx.getItems().isEmpty() ||
                workingHoursField.getText().isEmpty() ||
                utilPercentField.getText().isEmpty() ||
                employeeTypeField.getText().isEmpty() ||
                nameField.getText().isEmpty()) {
            return false;
        }

        return true;
    }

}
