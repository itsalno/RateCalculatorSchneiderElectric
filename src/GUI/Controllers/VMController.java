package GUI.Controllers;
import BE.Group;
import BE.Multiplier;
import GUI.Notifications;
import Exceptions.RateCalcException;
import GUI.Model.model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

    private MSController msController;

    private Group selectedGroup;
    private Notifications nt=new Notifications();

   public void setMsController(MSController msController){
       this.msController=msController;
   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        multiType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        multiPerc.setCellValueFactory(cellData ->new SimpleIntegerProperty(cellData.getValue().getPerc()).asObject());
        multiID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        try {
            model.getInstance().updateTable(multiTable);
        } catch (RateCalcException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }

    }



    public void createMulti(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewMulti.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnHidden(event ->{
            try {
                model.getInstance().updateTable(multiTable);
            } catch (RateCalcException e) {
                Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                e.printStackTrace();
                a.show();
            }
        });
        primaryStage.show();
    }

    public void deleteMulti(ActionEvent actionEvent) {
        Multiplier multiplier = multiTable.getSelectionModel().getSelectedItem();
        if(multiplier != null){
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Are you sure you want to delete this employee?");
            confirmationDialog.setContentText("Any unsaved changes will be lost.");
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    model.getInstance().deleteMulti(multiplier.getId());
                    model.getInstance().updateTable(multiTable);
                    nt.showSuccess("Successfully deleted multiplier");
                } catch (RateCalcException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    e.printStackTrace();
                    a.show();
                }
            }
        }
    }

    public void ApplyMutiplier(ActionEvent actionEvent) {
        try {
            model.getInstance().applyMultiplierToGroup(multiTable.getSelectionModel().getSelectedItem().getPerc(), selectedGroup.getId());
            Group allGroups= model.getInstance().updateGroupTable(selectedGroup.getId());
            msController.updateGroupTable(allGroups);
            nt.showSuccess("Successfully applied multiplier");
        } catch (RateCalcException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

    }

    public void setSelectedGroup(Group selectedGroup){
       this.selectedGroup=selectedGroup;
    }

}
