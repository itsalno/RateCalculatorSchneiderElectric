package BE;

public class Employee {

    private int annualSalary;
    private int overheadMultiPercent;
    private int confFixedAnnualAmount;
    private String country;
    private String team;
    private int workingHours;
    private int utilizationPercent;
    private String continent;
    private String employeeType;
    private  String dailyRate;
    private String hourlyRate;
    private int id;

    public String getDailyRate() {
        return dailyRate;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }



    public int getId() {
        return id;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public String getContinent() {
        return continent;
    }

    public Employee(int id,int annualSalary, int overheadMultiPercent, int confFixedAnnualAmount, String country,String continent, String team, int workingHours, int utilizationPercent,String employeeType) {
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.team = team;
        this.workingHours = workingHours;
        this.utilizationPercent = utilizationPercent;
        this.continent=continent;
        this.employeeType=employeeType;
        this.id=id;

    }
    public Employee(int annualSalary, int overheadMultiPercent, int confFixedAnnualAmount, String country,String continent, String team, int workingHours, int utilizationPercent,String employeeType) {
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.team = team;
        this.workingHours = workingHours;
        this.utilizationPercent = utilizationPercent;
        this.continent=continent;
        this.employeeType=employeeType;

    }

    public int getAnnualSalary() {
        return annualSalary;
    }

    public int getOverheadMultiPercent() {
        return overheadMultiPercent;
    }

    public int getConfFixedAnnualAmount() {
        return confFixedAnnualAmount;
    }

    public String getCountry() {
        return country;
    }

    public String getTeam() {
        return team;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public int getUtilizationPercent() {
        return utilizationPercent;
    }

    public void setAnnualSalary(int annualSalary) {
        this.annualSalary = annualSalary;
    }

    public void setOverheadMultiPercent(int overheadMultiPercent) {
        this.overheadMultiPercent = overheadMultiPercent;
    }

    public void setConfFixedAnnualAmount(int confFixedAnnualAmount) {
        this.confFixedAnnualAmount = confFixedAnnualAmount;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public void setUtilizationPercent(int utilizationPercent) {
        this.utilizationPercent = utilizationPercent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public void setDailyRate(String dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void updateEmployee(int annualSalary, int overheadMultiPercent, int confFixedAnnualAmount, String country, String continent, String team, int workingHours, int utilizationPercent, String employeeType){
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.team = team;
        this.workingHours = workingHours;
        this.utilizationPercent = utilizationPercent;
        this.continent=continent;
        this.employeeType=employeeType;
        this.id=id;
    }
}
