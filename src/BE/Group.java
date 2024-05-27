package BE;


public class Group {
    private String name;
    private int id;

    private int multiplier;



    public Group(String name) {
        this.name = name;
    }
    public Group(String name,int id) {
        this.name = name;
        this.id=id;
    }
    public Group(String name, int id, int multiplier){
        this.name = name;
        this.id=id;
        this.multiplier=multiplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getMultiplier() {
        return multiplier;
    }



    @Override
    public String toString() {
        return name;
    }
}
