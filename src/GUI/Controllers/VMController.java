package GUI.Controllers;
import BE.Multiplier;
import GUI.Model.model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VMController implements Initializable {



    @FXML
    private TableView<Multiplier> multiTable;

    @FXML
    private TableColumn<Multiplier, String> multiType;

    @FXML
    private TableColumn<Multiplier, Integer> multiPerc;

    @FXML
    private TableColumn<Multiplier, Integer> multiID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        multiType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        multiPerc.setCellValueFactory(cellData ->new SimpleIntegerProperty(cellData.getValue().getPerc()).asObject());
        multiID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        model.getInstance().updateTable(multiTable);
    }



    public void createMulti(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewMulti.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void deleteMulti(ActionEvent actionEvent) throws IOException {
        Multiplier multiplier = multiTable.getSelectionModel().getSelectedItem();
        if(multiplier != null){
            model.getInstance().deleteMulti(multiplier.getId());
            model.getInstance().updateTable(multiTable);
        }
    }
}
