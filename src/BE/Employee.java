package BE;

public class Employee {

    private double annualSalary;
    private int overheadMultiPercent;
    private double confFixedAnnualAmount;
    private String country;
    private String team;
    private int workingHours;
    private int utilizationPercent;
    private String continent;
    private String employeeType;
    private  String dailyRate;
    private String hourlyRate;
    private double annualSalaryUSD;
    private double confFixedAnnualAmountUSD;
    private int id;

    public Employee() {
    }

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
    public Employee(double annualSalary, int overheadMultiPercent, double confFixedAnnualAmount, String country,String continent, String team, int workingHours, int utilizationPercent,String employeeType, double annualSalaryUSD, double confFixedAnnualAmountUSD) {
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.team = team;
        this.workingHours = workingHours;
        this.utilizationPercent = utilizationPercent;
        this.continent=continent;
        this.employeeType=employeeType;
        this.annualSalaryUSD = annualSalaryUSD;
        this.confFixedAnnualAmountUSD = confFixedAnnualAmountUSD;

    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public int getOverheadMultiPercent() {
        return overheadMultiPercent;
    }

    public double getConfFixedAnnualAmount() {
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

    public double getAnnualSalaryUSD() {
        return annualSalaryUSD;
    }

    public double getConfFixedAnnualAmountUSD() {
        return confFixedAnnualAmountUSD;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public void setOverheadMultiPercent(int overheadMultiPercent) {
        this.overheadMultiPercent = overheadMultiPercent;
    }

    public void setConfFixedAnnualAmount(double confFixedAnnualAmount) {
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

    public void setAnnualSalaryUSD(int annualSalaryUSD) {
        this.annualSalaryUSD = annualSalaryUSD;
    }
    public void setConfFixedAnnualAmountUSD(double confFixedAnnualAmountUSD) {
        this.confFixedAnnualAmountUSD = confFixedAnnualAmountUSD;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double calculateHourlyRate() {

        double hourlyRate = (double) (annualSalary + confFixedAnnualAmount) / (workingHours * (utilizationPercent / 100.0)) * (overheadMultiPercent / 100.0);
        return hourlyRate;
    }

    // Calculating day rate based on the hourly rate and the number of working hours per day.
    // You'll need to decide how you want to pass the number of working hours in a day.
    // Here it's assumed to be a constant value, typical values are 7-9 hours depending on the country's full-time work policy.
    public double calculateDailyRate(int hoursInWorkDay) {
        double dailyRate = calculateHourlyRate() * hoursInWorkDay;
        return dailyRate;
    }
    public String getCalculatedHourlyRate() {
        return String.format("%.2f", calculateHourlyRate());
    }

    public String getCalculatedDailyRate(int hoursInWorkDay) {
        return String.format("%.2f", calculateDailyRate(hoursInWorkDay));
    }


}
