package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Users class which stores data for Users objects.
 * */
public class Users {

    private String username;
    private String password;
    private int userID;
    public static ObservableList<Users> usersList = FXCollections.observableArrayList(); // where Users objects are stored

    /**
     * Gets the username of a Users object
     * @return the username of a Users object
     * */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of a Users object
     * @param username the username to be set
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of a Users object
     * @return the password of a Users object
     * */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of a Users object
     * @param password the password to be set
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the userID of a Users object
     * @return the userID of a Users object
     * */
    public int getUserID() {return userID;}

    /**
     * Sets the userID of a Users object
     * @param userID the userID to be set
     * */
    public void setUserID(int userID){this.userID = userID;}

}
