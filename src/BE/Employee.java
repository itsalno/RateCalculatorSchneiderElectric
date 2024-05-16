package BE;

public class Employee {

    private double annualSalary;
    private String fullName;
    private int overheadMultiPercent;
    private double confFixedAnnualAmount;
    private String country;
    private String team;
    private int workingHours;
    private int utilizationPercent;
    private String continent;
    private String employeeType;
    private  float dailyRate;
    //private String hourlyRate;

    private float hourlyRate;
    private double annualSalaryUSD;
    private double confFixedAnnualAmountUSD;
    private int id;

    private int TeamId;

    public Employee() {
    }

    public float getDailyRate() {
        return dailyRate;
    }

    public float getHourlyRate() {
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

    public Employee(int id,int TeamId,String fullName,double annualSalary, int overheadMultiPercent, double confFixedAnnualAmount, String country,String continent, String team, int workingHours, int utilizationPercent,String employeeType) {
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
        this.fullName=fullName;
        this.TeamId=TeamId;

    }
    public Employee(int TeamId,String fullName,double annualSalary, int overheadMultiPercent, double confFixedAnnualAmount, String country,String continent, String team, int workingHours, int utilizationPercent,String employeeType, double annualSalaryUSD, double confFixedAnnualAmountUSD) {
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
        this.fullName=fullName;
        this.TeamId=TeamId;

    }

    public Employee(int id,int TeamId,String fullName,double annualSalary, int overheadMultiPercent, double confFixedAnnualAmount, String country,String continent, String team, int workingHours, int utilizationPercent,String employeeType, float hourlyRate, float dailyRate) {
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
        this.fullName=fullName;
        this.TeamId=TeamId;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
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

    public int getTeamId(){
        return TeamId;
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

    public void setDailyRate(float dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }


    public void setAnnualSalaryUSD(double annualSalaryUSD) {
        this.annualSalaryUSD = annualSalaryUSD;
    }
    public void setConfFixedAnnualAmountUSD(double confFixedAnnualAmountUSD) {
        this.confFixedAnnualAmountUSD = confFixedAnnualAmountUSD;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setTeamId(int TeamId){
        this.TeamId = TeamId;
    }

    public float calculateHourlyRate() {

        float hourlyRate = (float) ( (annualSalary) / (((workingHours * 52 * 5) * (utilizationPercent / 100.0)) * (overheadMultiPercent / 100.0)));
        setHourlyRate(hourlyRate);
        return hourlyRate;
    }

    // Calculating day rate based on the hourly rate and the number of working hours per day.
    // You'll need to decide how you want to pass the number of working hours in a day.
    // Here it's assumed to be a constant value, typical values are 7-9 hours depending on the country's full-time work policy.
    public float calculateDailyRate(int hoursInWorkDay) {
        float dailyRate = calculateHourlyRate() * (hoursInWorkDay);
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
