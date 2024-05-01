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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

public class MSController implements Initializable {


    @FXML
    private TableView<Group> groupTable;
    @FXML
    private TableColumn<Group, String> teamNameColumn;
    public Label curency;

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


    model model = new model();

    private Group selectedGroup;
    private List<Group> groups = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        continentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContinent()));
        teamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeam()));
        dailyRateCollumn.setCellValueFactory(cellData -> {
            String modifiedValueString = cellData.getValue().getCalculatedDailyRate(8).replace(',', '.');
            String modifiedValueAsString = Double.parseDouble(modifiedValueString) + "€";
            return new SimpleStringProperty(modifiedValueAsString);
        });
        hourlyRateCollumn.setCellValueFactory(cellData -> {
            String modifiedValueString = cellData.getValue().getCalculatedHourlyRate().replace(',', '.');
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


        populateEmpTable();
        populateGrpTable();




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
        curency.setText("EUR");
        setUSDtoEURRate();
    }


    // This method is also used to refresh the table when updating a emplyee
    public void populateEmpTable() {

            ObservableList<Employee> employees = FXCollections.observableArrayList();
            try {
                employees.addAll(model.getAllEmployees());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            profileTable.setItems(employees);

        }

        public void populateGrpTable () {
            ObservableList<Group> teams = FXCollections.observableArrayList();


            teams.addAll(model.getAllTeams());
            groupTable.setItems(teams);
        }




            public void DeleteTeams (ActionEvent actionEvent){
                Group selectedGroup = groupTable.getSelectionModel().getSelectedItem();

                if (selectedGroup != null) {
                    model.deleteTeam(selectedGroup);
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
                    populateEmpTable();
                });
                primaryStage.show();
            }


            public void deleteProfile (ActionEvent actionEvent){
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

            public void viewProfile (Employee employee) throws IOException {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ViewProfile.fxml"));
                    Parent root = loader.load();

                    ViewProfController controller = loader.getController();
                    controller.setMsController(this);
                    controller.setEmployee(employee);

                    controller.updateUIInfo();
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
                primaryStage.show();
            }


            public void searchInfo () {
                String searchText = searchBar.getText().trim().toLowerCase();
                List<Employee> matchingEmployees = model.searchEmployees(searchText);
                profileTable.setItems(FXCollections.observableArrayList(matchingEmployees));
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
                    primaryStage.show();

                }
            }
              //Starts here
            public int curentCurency = 0;
            public double EURtoUSDRate = 1.07;

            //Used for creating and edditing when program is in the USD mode
            public double USDtoEURRate =0;



            public void swichCurency (ActionEvent actionEvent){
                // this method swches curentCurency int between 1 and 0.
                // Each number represent a curency and everything is set acordingly to that curency.
                curentCurency = (curentCurency + 1) % 2;

                setCurencyLabel();
                calculateExchange();
                profileTable.refresh();
            }
            private void setUSDtoEURRate(){
                //this method sets USDtoEUR exchage its done as a method because if we change EURtoUSD the USDtoEUR changes
                //this setting is accured when the program starts in initialize method
                //this is the formula so we dont have to change it twice if the rate of exchane changes
                double USDtoEURRate1 = (1 - EURtoUSDRate) + 1;

                // Set the locale to use a period as the decimal separator ad not a comma because it gives a error
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat df = new DecimalFormat("#.##", symbols);

                // Format the double value with the specified decimal format
                String formattedValue = df.format(USDtoEURRate1);
                USDtoEURRate = Double.parseDouble(formattedValue);
            }


            private void setCurencyLabel () {
                if (curentCurency == 0) {
                    curency.setText("EUR");
                }
                if (curentCurency == 1) {
                    curency.setText("USD");
                }
            }

            private void calculateExchange () {
                if (curentCurency == 0) {
                    hourlyRateCollumn.setCellValueFactory(cellData -> {
                        String originalValueString = cellData.getValue().getCalculatedHourlyRate();
                        // Replace commas with periods if present because math would work with commas
                        String modifiedValueString = originalValueString.replace(',', '.');
                        // Parse the modified string to a double
                        Double originalValue = Double.valueOf(modifiedValueString);
                        // Convert the original value to a string
                        String modifiedValueAsString = String.valueOf(originalValue);
                        //we are not multiplying because this is the original value and the defolt is EUR
                        //so when we come back from dollar we want the value that was already there
                        return new SimpleStringProperty(modifiedValueAsString + "€");
                    });
                    dailyRateCollumn.setCellValueFactory(cellData -> {
                        //has to be changed to accomodate different hours in work days
                        String originalValueString = cellData.getValue().getCalculatedDailyRate(8);
                        // Replace commas with periods if present because math would work with commas
                        String modifiedValueString = originalValueString.replace(',', '.');
                        // Parse the modified string to a double
                        Double originalValue = Double.valueOf(modifiedValueString);
                        // Convert the original value to a string
                        String modifiedValueAsString = String.valueOf(originalValue);
                        //we are not multiplying because this is the original value and the defolt is EUR
                        //so when we come back from dollar we want the value that was already there
                        return new SimpleStringProperty(modifiedValueAsString + "€");
                    });
                }
                if (curentCurency == 1) {
                    hourlyRateCollumn.setCellValueFactory(cellData -> {
                        String originalValueString = cellData.getValue().getCalculatedHourlyRate();
                        // Replace commas with periods if present
                        String modifiedValueString = originalValueString.replace(',', '.');
                        // Parse the modified string to a double
                        Double originalValue = Double.valueOf(modifiedValueString);
                        // Multiply the original value by conversion rate
                        Double modifiedValue = originalValue * EURtoUSDRate;
                        // Convert the modified value to a string
                        String modifiedValueAsString = String.valueOf(modifiedValue);
                        // Return the modified value as an ObservableValue<String>
                        return new SimpleStringProperty("$" + modifiedValueAsString);
                    });
                    dailyRateCollumn.setCellValueFactory(cellData -> {
                        //has to be changed to accomodate different hours in work days
                        String originalValueString = cellData.getValue().getCalculatedDailyRate(8);
                        // Replace commas with periods if present
                        String modifiedValueString = originalValueString.replace(',', '.');
                        // Parse the modified string to a double
                        Double originalValue = Double.valueOf(modifiedValueString);
                        // Multiply the original value by conversion rate
                        Double modifiedValue = originalValue * EURtoUSDRate;
                        // Convert the modified value to a string
                        String modifiedValueAsString = String.valueOf(modifiedValue);
                        // Return the modified value as an ObservableValue<String>
                        return new SimpleStringProperty("$" + modifiedValueAsString);
                    });
                }
            }

    public void openMultipliers(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ViewMulti.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}








