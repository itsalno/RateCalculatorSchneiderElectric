package BE;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private List<Group> teams;

    public Employee() {
        this.teams = new ArrayList<>();
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
        this.teams = new ArrayList<>();
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
        this.teams = teams != null ? teams : new ArrayList<>();
    }

    public float calculateHourlyRate() {
        float hourlyRate = (float) (annualSalary / (workingHours * (utilizationPercent / 100.0)));
        hourlyRate = roundToTwoDecimalPlaces(hourlyRate);
        setHourlyRate(hourlyRate);
        return hourlyRate;
    }

    public float calculateDailyRate(int hoursInWorkDay) {
        float dailyRate = calculateHourlyRate() * hoursInWorkDay;
        dailyRate = roundToTwoDecimalPlaces(dailyRate);
        setDailyRate(dailyRate);
        return dailyRate;
    }

    private float roundToTwoDecimalPlaces(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public String getCalculatedHourlyRate() {
        return String.format("%.2f", calculateHourlyRate());
    }

    public String getCalculatedDailyRate(int hoursInWorkDay) {
        return String.format("%.2f", calculateDailyRate(hoursInWorkDay));
    }

    public boolean canAddMoreTeams() {
        return this.teams.size() < 2;
    }

    public void addTeam(Group team) {
        if (canAddMoreTeams()) {
            this.teams.add(team);
        } else {
            throw new IllegalStateException("An employee cannot be part of more than two teams.");
        }
    }
}
