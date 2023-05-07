package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Users {

    private String username;
    private String password;
    private int userID;
    public static ObservableList<Users> usersList = FXCollections.observableArrayList();

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

    public int getUserID() {return userID;}

    public void setUserID(int userID){this.userID = userID;}

}
