package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static group.model.Countries.countriesList;
import static group.model.FirstLevelDivisions.divisionList;

public class Customers {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;

    private String countryName;
    private String divisionName;

    public static ObservableList<Customers> customerList = FXCollections.observableArrayList();

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivisionName () {
        String divisionName = "";
        for (FirstLevelDivisions element : divisionList) {
            if (element.getDivisionID() == getDivisionID()) {
                divisionName = element.getDivisionName();
            }
        }
        return divisionName;
    }

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
