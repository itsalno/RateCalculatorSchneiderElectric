package GUI.Controllers;

import Exceptions.RateCalcException;
import GUI.Model.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {


    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ImageView logInImage;
    @FXML
    private String username;
    @FXML
    private String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image loginImage=new Image("file:src/SchneiderLogo.png");
        logInImage.setImage(loginImage);
        usernameField.setPromptText("Enter username");
        passwordField.setPromptText("Enter password");
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        username = usernameField.getText();
        password = passwordField.getText();
        try {
            if (model.getInstance().checkUser(username, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/MainScreen.fxml"));
                Parent root = loader.load();
                Stage primaryStage = new Stage();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

                Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You have inputted the wrong username or password, please check and try again");
                alert.show();
            }
        }catch (RateCalcException e){
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }

    }
}
