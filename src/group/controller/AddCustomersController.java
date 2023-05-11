package group.controller;

import group.model.Countries;
import group.model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

        ObservableList<String> tempDivisionList = FXCollections.observableArrayList();
        for (FirstLevelDivisions division : divisionList) {
            tempDivisionList.add(division.getDivisionName());
        }
        divisionComboBox.setItems(tempDivisionList);
    }


}
