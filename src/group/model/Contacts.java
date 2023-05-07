package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contacts {

    private String name;
    private int contactID;

    public static ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    //public ObservableList<Appointments> contactApptList = FXCollections.observableArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

}
