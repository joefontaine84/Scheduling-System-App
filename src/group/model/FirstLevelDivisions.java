package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the FirstLevelDivisions Class that stores data for FirstLevelDivision objects.
 * */
public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    private int countryID;
    public static ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList(); // where all FirstLevelDivision objects are stored

    /**
     * Gets the FirstLevelDivisions object's country ID value
     * @return the country ID value
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the FirstLevelDivisions object's country ID value
     * @param countryID the country ID value to be set
     * */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets the FirstLevelDivisions object's division ID value
     * @return the division ID value
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the FirstLevelDivisions object's division ID value
     * @param divisionID the division ID value to be set
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets the FirstLevelDivisions object's division name value
     * @return the division name value
     * */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the FirstLevelDivisions object's division name value
     * @param divisionName the division name value to be set
     * */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
