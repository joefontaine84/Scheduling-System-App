package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contacts {

    private String name;
    public static ObservableList<Contacts> contactList = FXCollections.observableArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
