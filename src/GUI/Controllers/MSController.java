package GUI.Controllers;

import BE.Employee;
import GUI.Model.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BE.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

public class MSController implements Initializable {


    @FXML
    private TextField searchBar;
    @FXML
    private ChoiceBox<String> allTeams;
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
    private  TableColumn<Employee,Integer> OMP;
    @FXML
    private  TableColumn<Employee,Integer> CFAA;
    @FXML
    private  TableColumn<Employee,Integer> annualSalaryColumn;
    @FXML
    private  TableColumn<Employee,Integer> utilizationPercentage;

    @FXML
    private TableColumn<Employee, String> employeeTypeColumn;
    @FXML
    private TableColumn<Employee,String> dailyRateCollumn;
    @FXML
    private TableColumn<Employee, String> hourlyRateCollumn;
    private Group selectedGroup;
    private List<Group> groups=new ArrayList<>();

    model model=new model();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        continentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContinent()));
        teamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeam()));
        dailyRateCollumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCalculatedDailyRate(8)));
        hourlyRateCollumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCalculatedHourlyRate()));
        workingHoursColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWorkingHours()).asObject());
        annualSalaryColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnnualSalary()).asObject());
        OMP.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOverheadMultiPercent()).asObject());
        CFAA.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getConfFixedAnnualAmount()).asObject());
        utilizationPercentage.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUtilizationPercent()).asObject());
        employeeTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeType()));


        populateTable();


        setGroups();
        for (Group group : groups) {
           allTeams.getItems().add(group.getName());
        }

        allTeams.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                deleteSelectedTeam(newValue);
            }
        });

        profileTable.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Employee selectedEmployee = row.getItem();
                    try {
                        viewProfile(selectedEmployee);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            searchInfo();
        });

    }
    // This method is also used to refresh the table when updating a emplyee 
    public void populateTable()  {

        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            employees.addAll(model.getAllEmployees());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        profileTable.setItems(employees);

    }



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
        model.deleteTeam(allTeams.getSelectionModel().getSelectedItem());
        allTeams.getItems().remove(allTeams.getSelectionModel().getSelectedItem());
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

    public void viewProfile(Employee employee) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ViewProfile.fxml"));
        Parent root = loader.load();
        ViewProfController controller = loader.getController();
        controller.setEmployee(employee);
        controller.updateUIInfo();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void editProfile(ActionEvent actionEvent) throws IOException {
        Employee selectedEmployee = (Employee) profileTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/EditProfile.fxml"));
            Parent root = loader.load();

            EditProfileController editProfileController = loader.getController();
            editProfileController.setMsController(this);
            editProfileController.setEmplyeeToUpdate(selectedEmployee);

            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
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

    public void choiceClick(ActionEvent actionEvent) {
        //for filtering
    }

    public void searchInfo(){
        String searchText = searchBar.getText().trim().toLowerCase();
        List<Employee> matchingEmployees = model.searchEmployees(searchText);
        profileTable.setItems(FXCollections.observableArrayList(matchingEmployees));
    }


    public void editTeam(ActionEvent actionEvent) {
        /*
         Object selectedTeam = allTeams.getSelectionModel().getSelectedItem();

        if (selectedTeam != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewTeam.fxml"));
            Parent root = loader.load();

            NewTeamController newTeamController = loader.getController();
            newTeamController.setMsController(this);
            newTeamController.setTeamToEdit(selectedTeam);
            System.out.println(selectedTeam);

            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
    }
         */
    }






}
