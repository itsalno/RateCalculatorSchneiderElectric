package GUI.Controllers;
import BE.Employee;
import GUI.Model.model;
import javafx.beans.property.SimpleStringProperty;
import BE.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MSController implements Initializable {

    @FXML
    private ToggleButton curencyBTN;
    @FXML
    private TableColumn<Group, Integer> groupMulti;
    @FXML
    private TableColumn<Employee,String> fullNameCollumn;
    @FXML
    private TableView<Group> groupTable;
    @FXML
    private TableColumn<Group, String> teamNameColumn;
    @FXML
    private TextField searchBar;
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
    private TableColumn<Employee, Integer> OMP;
    @FXML
    private TableColumn<Employee, Integer> CFAA;
    @FXML
    private TableColumn<Employee, Integer> annualSalaryColumn;
    @FXML
    private TableColumn<Employee, Integer> utilizationPercentage;

    @FXML
    private TableColumn<Employee, String> employeeTypeColumn;
    @FXML
    private TableColumn<Employee, String> dailyRateCollumn;
    @FXML
    private TableColumn<Employee, String> hourlyRateCollumn;



    //don't know which
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fullNameCollumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        continentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContinent()));
        teamColumn.setCellValueFactory(cellData -> {
            List<Group> teams = cellData.getValue().getTeams();
            String teamNames = teams != null ? teams.stream()
                    .map(Group::getName)
                    .collect(Collectors.joining(", ")) : "";
            return new SimpleStringProperty(teamNames);
        });
        dailyRateCollumn.setCellValueFactory(cellData -> {
            String modifiedValueString = String.valueOf(model.getInstance().calculateDailyMulti(cellData.getValue())).replace(',', '.');
            String modifiedValueAsString = Double.parseDouble(modifiedValueString) + "€";
            return new SimpleStringProperty(modifiedValueAsString);
        });

        hourlyRateCollumn.setCellValueFactory(cellData -> {
            String modifiedValueString = String.valueOf(model.getInstance().calculateHourlyMulti(cellData.getValue())).replace(",", ".");
            String modifiedValueAsString = Double.parseDouble(modifiedValueString) + "€";
            return new SimpleStringProperty(modifiedValueAsString);
        });
        workingHoursColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWorkingHours()).asObject());
        annualSalaryColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int) cellData.getValue().getAnnualSalary()).asObject());
        OMP.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOverheadMultiPercent()).asObject());
        CFAA.setCellValueFactory(cellData -> new SimpleIntegerProperty((int) cellData.getValue().getConfFixedAnnualAmount()).asObject());
        utilizationPercentage.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUtilizationPercent()).asObject());
        employeeTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeType()));
        teamNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        groupMulti.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMultiplier()).asObject());


        //remember
        model.getInstance().populateEmpTable(profileTable);
        model.getInstance().populateGrpTable(groupTable);

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

        groupTable.setRowFactory(tv -> {
            TableRow<Group> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Group selectedGroup = row.getItem();
                    model.getInstance().searchInfo(searchBar,profileTable,selectedGroup.getName());
                }
            });
            return row;
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            model.getInstance().searchInfo(searchBar, profileTable,null);
        });
        curencyBTN.setText("EUR");
        model.getInstance().setUSDtoEURRate(EURtoUSDRate);
    }

    public void DeleteTeams(ActionEvent actionEvent){
        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            model.getInstance().deleteTeam(selectedGroup);
            groupTable.getItems().remove(selectedGroup);
        }
    }

    public void createProfile (ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewProfile.fxml"));
        Parent root = loader.load();

        NPController npController = loader.getController();
        npController.setMsController(this);

        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnHidden(event -> {
            model.getInstance().populateEmpTable(profileTable);
        });
        primaryStage.show();
    }


    public void deleteProfile (ActionEvent actionEvent){
        Employee selectedEmployee = profileTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            model.getInstance().deleteEmployee(selectedEmployee);
            profileTable.getItems().remove(selectedEmployee);
        }
    }

    public void viewProfile (Employee employee) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ViewProfile.fxml"));
        Parent root = loader.load();

        ViewProfController controller = loader.getController();
        controller.setMsController(this);
        controller.setEmployee(employee);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void editProfile (ActionEvent actionEvent) throws IOException {
        Employee selectedEmployee = (Employee) profileTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewProfile.fxml"));
                    Parent root = loader.load();

                    NPController newProfileController = loader.getController();
                    newProfileController.setMSController(this);
                    newProfileController.setEmployeeToUpdate(selectedEmployee);

                    Stage primaryStage = new Stage();
                    primaryStage.setScene(new Scene(root));
                    primaryStage.setOnHidden(event -> {
                        model.getInstance().populateEmpTable(profileTable);
                    });
                    primaryStage.show();
            }
    }

    public void CreateTeam (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewTeam.fxml"));
        Parent root = loader.load();
        NewTeamController newTeamController = loader.getController();
        newTeamController.setMsController(this);
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnHidden(event -> {
            model.getInstance().populateGrpTable(groupTable);
        });
        primaryStage.show();
    }

    public void editTeam (ActionEvent actionEvent) throws IOException {
        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/NewTeam.fxml"));
            Parent root = loader.load();

            NewTeamController newTeamController = loader.getController();
            newTeamController.setMsController(this);
            newTeamController.setGroupToEdit(selectedGroup);

            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.setOnHidden(event -> {
                model.getInstance().populateGrpTable(groupTable);
            });
            primaryStage.show();

        }
    }
    //Starts here

    //fix so its private
    private int curentCurency = 0;
    private double EURtoUSDRate = 1.0775286;

    public void swichCurency (ActionEvent actionEvent){

        curentCurency = model.getInstance().swichCurency(curentCurency, profileTable, curencyBTN, hourlyRateCollumn, dailyRateCollumn);
    }

    public void openMultipliers(ActionEvent actionEvent) throws IOException {
        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ViewMulti.fxml"));
        Parent root = loader.load();
        VMController vmController= loader.getController();
        vmController.setMsController(this);
        vmController.setSelectedGroup(selectedGroup);
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public void resetTable(MouseEvent actionEvent) {
        groupTable.getSelectionModel().clearSelection();
        profileTable.getSelectionModel().clearSelection();
        model.getInstance().populateEmpTable(profileTable);
    }


    public void removeFromTeam(ActionEvent actionEvent) {
      Group selectedGroup=groupTable.getSelectionModel().getSelectedItem();
        Employee selectedEmployee=profileTable.getSelectionModel().getSelectedItem();

        if (selectedGroup!=null && selectedEmployee!=null){
                model.getInstance().removeTeamFromEmployee(selectedEmployee.getId(), selectedGroup.getId());
                model.getInstance().populateEmpTable(profileTable);
                groupTable.getSelectionModel().clearSelection();
        }
    }
    public void updateGroupTable(Group group){
       model.getInstance().updateGroupTable(group, groupTable);
       model.getInstance().populateEmpTable(profileTable);
    }

    public void addToTeam(ActionEvent actionEvent) {
        Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        Employee selectedEmployee = profileTable.getSelectionModel().getSelectedItem();

        if (selectedGroup != null && selectedEmployee != null && !selectedEmployee.getTeams().contains(selectedGroup)) {
            selectedEmployee.getTeams().add(selectedGroup);
            model.getInstance().editEmployee(selectedEmployee);
            model.getInstance().populateEmpTable(profileTable);
            groupTable.getSelectionModel().clearSelection();
            profileTable.getSelectionModel().clearSelection();
        }
    }
}










