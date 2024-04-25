package GUI.Controllers;

import BE.Group;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTeamController {

    @FXML
    private TextField fieldName;

    private MSController msController;

    public void setMsController(MSController msController) {
        this.msController=msController;
    }

    public void ClickCancle(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void CreateTeam(ActionEvent actionEvent) {
        String name=fieldName.getText();
        Group group=new Group(name);
        model.getInstance().createTeam(group);

        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        msController.updateChoiceBox(group);
    }
}
