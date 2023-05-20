package group.controller;

import group.model.Countries;
import group.model.Customers;
import group.model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static group.Main.primaryStage;
import static group.dao.Data.addCustomerToDB;
import static group.dao.Data.getNextCustomerID;
import static group.model.Countries.countriesList;
import static group.model.Customers.customerList;
import static group.model.FirstLevelDivisions.divisionList;

public class AddCustomersController implements Initializable {

    public TextField customerIDTextField;
    public TextField customerNameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneNumberTextField;
    public ComboBox<String> countryComboBox;
    public ComboBox<String> divisionComboBox;
    public ObservableList<String> tempDivListName = FXCollections.observableArrayList();


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerIDTextField.setText(String.valueOf(getNextCustomerID()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ObservableList<String> tempCountriesList = FXCollections.observableArrayList();
        for (Countries country : countriesList) {
            tempCountriesList.add(country.getCountryName());
        }
        countryComboBox.setItems(tempCountriesList);

    }

    @FXML
    public void filterDivisions () {
        if (countryComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a country first.");
            alert.show();
        } else {
            tempDivListName.clear();
            for (FirstLevelDivisions division : divisionList) {
                if (division.getCountryID() == findCountryID()) {
                    tempDivListName.add(division.getDivisionName());
                }
            }
            divisionComboBox.setItems(tempDivListName);
        }
    }

    public int findCountryID() {
        int tempID = 0;
        for (Countries country : countriesList) {
            if (country.getCountryName().equals(countryComboBox.getValue())) {
                tempID = country.getCountryID();
            }
        }
        return tempID;
    }

    public int findDivisionID() throws NullPointerException {
        int tempID = 0;
        for (FirstLevelDivisions divisions : divisionList) {
            if (divisions.getDivisionName().equals(divisionComboBox.getValue())) {
                tempID = divisions.getDivisionID();
                return tempID;
            }
        }

        throw new NullPointerException();

    }

    public void switchToManageCustomers() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/ManageCustomersView.fxml"));
        root = addApptsView.load();
        scene = new Scene(root, 825, 427);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void save () throws IOException, SQLException {
        try {
            Customers customer = new Customers();
            customer.setCustomerID(Integer.valueOf(customerIDTextField.getText()));
            customer.setCustomerName(customerNameTextField.getText());
            customer.setPhoneNumber(phoneNumberTextField.getText());
            customer.setAddress(addressTextField.getText());
            customer.setPostalCode(postalCodeTextField.getText());
            customer.setDivisionID(findDivisionID());

            addCustomerToDB(customer);
            customerList.add(customer);
            switchToManageCustomers();

        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that information has been entered/selected for all fields.");
            alert.show();
        }

    }

}
