package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Countries {
    private int countryID;
    private String countryName;
    public static ObservableList<Countries> countriesList = FXCollections.observableArrayList();

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
