package group.model;

import java.util.ArrayList;

public class Users {

    private String username;
    private String password;
    public static ArrayList<Users> usersList = new ArrayList<Users>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
