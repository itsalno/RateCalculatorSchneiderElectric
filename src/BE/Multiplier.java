package BE;

public class Multiplier {
    private String type;
    private int percentage;

    private int id;



    public Multiplier(String type, int percentage, int id) {
        this.type = type;
        this.percentage = percentage;
        this.id = id;
    }
    public Multiplier(String type,int percentage) {
        this.type = type;
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public int getPerc() {
        return percentage;
    }

    public int getId() {
        return id;
    }
}
