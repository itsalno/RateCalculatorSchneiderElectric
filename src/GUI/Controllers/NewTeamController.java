package GUI.Controllers;

import BE.Group;
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

public class NewTeamController implements Initializable {


    @FXML
    private ImageView newTeamImage;
    @FXML
    private TextField fieldName;

    private MSController msController;





    public void setMsController(MSController msController) {
        this.msController=msController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img =new Image("file:src/SchneiderLogo.png");
        newTeamImage.setImage(img);
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
