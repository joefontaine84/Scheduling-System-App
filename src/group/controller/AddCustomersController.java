package group.controller;

import group.model.Countries;
import group.model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static group.dao.Data.getNextCustomerID;
import static group.model.Countries.countriesList;
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
}
