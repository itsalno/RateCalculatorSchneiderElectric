package BE;

public class User {
    private String username;

    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getName(){
        return username;
    }

    public String getPass(){
        return password;
    }

}
