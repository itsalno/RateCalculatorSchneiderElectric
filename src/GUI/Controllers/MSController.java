package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MSController implements Initializable {


    @FXML
    private TableView<Employee> profileTable;

    @FXML
    private TableColumn<Employee, String> countryColumn;
    @FXML
    private TableColumn<Employee, String> continentColumn;
    @FXML
    private TableColumn<Employee, String> teamColumn;
    @FXML
    private TableColumn<Employee, Integer> workingHoursColumn;
    @FXML
    private TableColumn<Employee, String> employeeTypeColumn;
    @FXML
    private TableColumn<Employee,String> dailyRateCollumn;
    @FXML
    private TableColumn<Employee, String> hourlyRateCollumn;

    model model=new model();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        continentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContinent()));
        teamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeam()));
        dailyRateCollumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDailyRate()));
        hourlyRateCollumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHourlyRate()));
        workingHoursColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWorkingHours()).asObject());



        populateTable();
    }

    public void populateTable()  {

        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            employees.addAll(model.getAllEmployees());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        profileTable.setItems(employees);
    }

    public void createProfile(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewProfile.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnHidden(event -> {
            populateTable();
        });
        primaryStage.show();
    }

    public void deleteProfile(ActionEvent actionEvent) {
        Employee selectedEmployee = profileTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                model.deleteEmployee(selectedEmployee);

                profileTable.getItems().remove(selectedEmployee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void viewProfile(){

    }
    public void editProfile(ActionEvent actionEvent) {

    }
}
