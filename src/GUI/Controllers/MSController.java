package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import BE.Group;
import GUI.Model.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;




import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MSController implements Initializable {


    @FXML
    public ChoiceBox<String> allTeams;
    private List<Group> groups=new ArrayList<>();
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

        setGroups();
        for (Group group : groups) {
            allTeams.getItems().add(group.getName());
        }

        // Add event handler for selection change
        allTeams.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                deleteSelectedTeam(newValue);
            }
        });

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

    private Group selectedGroup;


    private void setGroups(){
        groups.addAll(GUI.Model.model.getInstance().getAllTeams());
    }


    public void DeleteTeams(ActionEvent actionEvent) {
        // This method will be called from the button click, not from selection change
        Object selectedItem = allTeams.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            deleteSelectedTeam(selectedItem);
        }
    }

    private void deleteSelectedTeam(Object selectedItem) {
        if (selectedItem instanceof String) {
            String selectedName = (String) selectedItem;
            // Find the Group object corresponding to the selected name
            Group selectedGroup = groups.stream()
                    .filter(group -> group.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);
            if (selectedGroup != null) {
                int teamId = selectedGroup.getId();

                // Remove from the database
                model.getInstance().deleteTeam(teamId);

                // Remove from the ChoiceBox
                allTeams.getItems().remove(selectedName);
            }
        }
    }

    public void updateChoiceBox(Group group){
        allTeams.getItems().add(group.getName());
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

    public void CreateTeam(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewTeam.fxml"));
        Parent root = loader.load();
        NewTeamController newTeamController = loader.getController();
        newTeamController.setMsController(this);
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }




}
