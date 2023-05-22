package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Countries Class that stores data for Countries objects.
 * */
public class Countries {
    private int countryID;
    private String countryName;
    public static ObservableList<Countries> countriesList = FXCollections.observableArrayList();  // where all Countries objects are stored

    /**
     * Gets the Countries object's countryID value
     * @return  countryID value (int)
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the Countries object's countryID value
     * */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets the Countries object's countryName value
     * @return  countryName value (string)
     * */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the Countries object's countryName value
     * */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
