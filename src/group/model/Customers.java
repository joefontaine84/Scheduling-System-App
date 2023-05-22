package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static group.model.Countries.countriesList;
import static group.model.FirstLevelDivisions.divisionList;

/**
 * This is the Customers Class that stores data for Customers objects.
 * */
public class Customers {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;

    public static ObservableList<Customers> customerList = FXCollections.observableArrayList(); // where all Customers objects are stored

    /**
     * Gets the Customer object's customerID value
     * @return  customerID value (int)
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the Customers object's customerID value
     * @param customerID the customer ID to be set
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the Customer object's customerName value
     * @return  the customer name value (string)
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the Customers object's customerName value
     * @param customerName the customer name to be set
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the Customer object's customerAddress value
     * @return  the customer address value (string)
     * */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the Customers object's customerAddress value
     * @param address the address to be set
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the Customer object's postalCode value
     * @return  the customer postal code value (string)
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the Customers object's postalCode value
     * @param postalCode the postal code to be set
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the Customer object's phoneNumber value
     * @return  the customer phoneNumber value (string)
     * */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the Customers object's phoneNumber value
     * @param phoneNumber the phone number to be set
     * */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the Customer object's divisionID value
     * @return  the customer divisionID value (int)
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the Customers object's divisionID value
     * @param divisionID the division ID to be set
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets the Customer object's divisionName value
     * @return  the customer division name value (string)
     * */
    public String getDivisionName () {
        String divisionName = "";
        for (FirstLevelDivisions element : divisionList) {
            if (element.getDivisionID() == getDivisionID()) {
                divisionName = element.getDivisionName();
            }
        }
        return divisionName;
    }

    /**
     * Gets the Customer object's countryName value
     * @return  the customer country name value (string)
     * */
    public String getCountryName() {
        String countryName = "";
        for (FirstLevelDivisions element : divisionList) {
            if (element.getDivisionID() == getDivisionID()) {
                for (Countries country : countriesList) {
                    if (country.getCountryID() == element.getCountryID()) {
                        countryName = country.getCountryName();
                    }
                }
            }
        }
        return countryName;
    }


}
