package GUI.Controllers;

import BE.Group;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    private Group groupToEdit;





    public void setMsController(MSController msController) {
        this.msController=msController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.getInstance().setImage(newTeamImage);
    }



    public void ClickCancle(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }



    public void CreateTeam(ActionEvent actionEvent) {
        System.out.println(groupToEdit);

        if (groupToEdit!=null){
            edit();
        }else {
            create();
        }
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }


    public void edit(){
        //model
        groupToEdit.setName(fieldName.getText());
        model.getInstance().updateTeam(groupToEdit);
    }

    //idk
    public void create(){
        String name=fieldName.getText();
        Group group=new Group(name);
        model.getInstance().createTeam(group);
    }


    public void setGroupToEdit(Group selectedGroup) {
        this.groupToEdit=selectedGroup;
        fieldName.setText(groupToEdit.getName());

    }
}
