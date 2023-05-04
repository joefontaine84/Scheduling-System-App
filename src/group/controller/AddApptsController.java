package group.controller;

import group.dao.Data;
import group.model.Contacts;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static group.dao.Data.getNextAppointmentID;
import static group.dao.Data.populateContacts;
import static group.model.Contacts.contactList;

public class AddApptsController implements Initializable {

    //get ID from sql
    public TextField appointmentIDTextField;
    public ComboBox contactIDComboBox;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDTextField.setText(String.valueOf(getNextAppointmentID()));
            populateContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        contactIDComboBox.setItems(contactList);
        contactIDComboBox.
    }
}
