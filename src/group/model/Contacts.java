package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Contacts Class that stores data for Contacts objects.
 * */
public class Contacts {

    private String name;
    private int contactID;

    public static ObservableList<Contacts> contactList = FXCollections.observableArrayList(); // where all Contacts objects are stored

    /**
     * Gets the Contacts object's name value
     * @return  name (string)
     * */
    public String getName() {
        return name;
    }

    /**
     * Sets the Contacts object's name value
     * @param name the name to be set
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Contacts object's contactID value
     * @return  contactID (int)
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the Contacts object's contactID value
     * @param contactID the contactID to be set
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

}
