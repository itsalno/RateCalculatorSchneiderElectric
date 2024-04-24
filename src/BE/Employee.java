package BE;

public class Employee {

    private int annualSalary;
    private int overheadMultiPercent;
    private int confFixedAnnualAmount;
    private String country;
    private String team;
    private int workingHours;
    private int uitlizationPercent;
    private String continent;

    public String getContinent() {
        return continent;
    }

    public Employee(int annualSalary, int overheadMultiPercent, int confFixedAnnualAmount, String country,String continent, String team, int workingHours, int uitlizationPercent) {
        this.annualSalary = annualSalary;
        this.overheadMultiPercent = overheadMultiPercent;
        this.confFixedAnnualAmount = confFixedAnnualAmount;
        this.country = country;
        this.team = team;
        this.workingHours = workingHours;
        this.uitlizationPercent = uitlizationPercent;
        this.continent=continent;
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

    public int getUitlizationPercent() {
        return uitlizationPercent;
    }
}
