package BE;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private double annualSalary;
    private String fullName;
    private int overheadMultiPercent;
    private double confFixedAnnualAmount;
    private String country;
    private String continent;
    private int workingHours;
    private int utilizationPercent;
    private String employeeType;
    private float dailyRate;
    private float hourlyRate;
    private double annualSalaryUSD;
    private double confFixedAnnualAmountUSD;
    private int id;
    private int teamId;
    private List<Group> teams; // New field to store multiple teams

    public Employee() {
        this.teams = new ArrayList<>(); // Initialize teams
    }

    public Employee(int id, int teamId, String fullName, double annualSalary, int overheadMultiPercent, double confFixedAnnualAmount, String country, String continent, List<Group> teams, int workingHours, int utilizationPercent, String employeeType, float hourlyRate, float dailyRate) {
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.continent = continent;
        this.workingHours = workingHours;
        this.utilizationPercent = utilizationPercent;
        this.employeeType = employeeType;
        this.id = id;
        this.fullName = fullName;
        this.teamId = teamId;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
        this.teams = teams != null ? teams : new ArrayList<>(); // Initialize teams
    }

    public Employee(int teamId, String fullName, double annualSalary, int overheadMultiPercent, double confFixedAnnualAmount, String country, String continent, List<Group> teams, int workingHours, int utilizationPercent, String employeeType, double annualSalaryUSD, double confFixedAnnualAmountUSD) {
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.continent = continent;
        this.workingHours = workingHours;
        this.utilizationPercent = utilizationPercent;
        this.employeeType = employeeType;
        this.annualSalaryUSD = annualSalaryUSD;
        this.confFixedAnnualAmountUSD = confFixedAnnualAmountUSD;
        this.fullName = fullName;
        this.teamId = teamId;
        this.teams = new ArrayList<>(); // Initialize teams
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getOverheadMultiPercent() {
        return overheadMultiPercent;
    }

    public void setOverheadMultiPercent(int overheadMultiPercent) {
        this.overheadMultiPercent = overheadMultiPercent;
    }

    public double getConfFixedAnnualAmount() {
        return confFixedAnnualAmount;
    }

    public void setConfFixedAnnualAmount(double confFixedAnnualAmount) {
        this.confFixedAnnualAmount = confFixedAnnualAmount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public int getUtilizationPercent() {
        return utilizationPercent;
    }

    public void setUtilizationPercent(int utilizationPercent) {
        this.utilizationPercent = utilizationPercent;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public float getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(float dailyRate) {
        this.dailyRate = dailyRate;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getAnnualSalaryUSD() {
        return annualSalaryUSD;
    }

    public void setAnnualSalaryUSD(double annualSalaryUSD) {
        this.annualSalaryUSD = annualSalaryUSD;
    }

    public double getConfFixedAnnualAmountUSD() {
        return confFixedAnnualAmountUSD;
    }

    public void setConfFixedAnnualAmountUSD(double confFixedAnnualAmountUSD) {
        this.confFixedAnnualAmountUSD = confFixedAnnualAmountUSD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public List<Group> getTeams() {
        return teams != null ? teams : new ArrayList<>(); // Return empty list if teams is null
    }

    public void setTeams(List<Group> teams) {
        this.teams = teams != null ? teams : new ArrayList<>(); // Initialize teams
    }

    public float calculateHourlyRate() {
        float hourlyRate = (float) (annualSalary / (((workingHours * 52 * 5) * (utilizationPercent / 100.0)) * (overheadMultiPercent / 100.0)));
        setHourlyRate(hourlyRate);
        return hourlyRate;
    }

    public float calculateDailyRate(int hoursInWorkDay) {
        float dailyRate = calculateHourlyRate() * hoursInWorkDay;
        setDailyRate(dailyRate);
        return dailyRate;
    }

    public String getCalculatedHourlyRate() {
        return String.format("%.2f", calculateHourlyRate());
    }

    public String getCalculatedDailyRate(int hoursInWorkDay) {
        return String.format("%.2f", calculateDailyRate(hoursInWorkDay));
    }
}
