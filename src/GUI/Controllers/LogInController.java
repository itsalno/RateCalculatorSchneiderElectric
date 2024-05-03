package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image loginImage=new Image("file:src/SchneiderLogo.png");
        logInImage.setImage(loginImage);
        usernameField.setPromptText("Enter username");
        passwordField.setPromptText("Enter password");
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/MainScreen.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
