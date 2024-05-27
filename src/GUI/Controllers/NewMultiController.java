package GUI.Controllers;

import BE.Multiplier;
import GUI.Notifications;
import Exceptions.RateCalcException;
import GUI.Model.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewMultiController implements Initializable {
    @FXML
    private TextField textPerc;

    @FXML
    private ChoiceBox<String> choiceType;
    private Notifications nt=new Notifications();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> types= FXCollections.observableArrayList();
        types.addAll("Markup", "Gross market");
        choiceType.setItems(types);
    }

    public void createMulti(ActionEvent actionEvent) {

        try {
            if (!fieldCheck()) {
                nt.showError("You have left an empty field!");
                return;
            }

            model.getInstance().createMulti(new Multiplier(choiceType.getSelectionModel().getSelectedItem(), Integer.parseInt(textPerc.getText())));

        } catch (RateCalcException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        nt.showSuccess("Successfully created the multiplier");

    }

    private boolean fieldCheck() {
        if (textPerc.getText().isEmpty()||choiceType.getItems().isEmpty()) {
            return false;
        }

        return true;
    }


    public void close(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
