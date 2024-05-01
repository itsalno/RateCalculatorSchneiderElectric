package GUI.Controllers;

import BE.Multiplier;
import GUI.Model.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewMultiController implements Initializable {
    public TextField textPerc;
    public ChoiceBox<String> choiceType;

    model model = new model();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> types= FXCollections.observableArrayList();
        types.add("Markup");
        types.add("Gross market");
        choiceType.setItems(types);
    }

    public void createMulti(ActionEvent actionEvent) {
        //Needs alert pls
        System.out.println(choiceType.getSelectionModel().getSelectedItem());
        model.createMulti(new Multiplier(choiceType.getSelectionModel().getSelectedItem(),Integer.parseInt(textPerc.getText())));
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }


    public void close(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
