package group.controller;

import group.dao.Data;
import group.model.Appointments;
import group.model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static group.dao.Data.getNextAppointmentID;
import static group.dao.Data.populateContacts;
import static group.model.Contacts.contactList;

public class AddApptsController implements Initializable {

    //get ID from sql
    public TextField appointmentIDTextField;
    public ComboBox<String> contactIDComboBox;
    public DatePicker startDatePicker;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDTextField.setText(String.valueOf(getNextAppointmentID()));
            populateContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ObservableList<String> templist = FXCollections.observableArrayList();
        for (Contacts element : contactList) {
            templist.add(element.getName());
        }
        contactIDComboBox.setItems(templist);


    }

    @FXML
    public void test() {
        //contactIDComboBox.getValue(); ... this is to get value from contactIDComboBox

/*        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        if (startDatePicker.getValue() != null) {
            System.out.println(startDatePicker.getValue().format(formatter));
        }..... this is to obtain the date from the datePicker*/

        //to manage appointments so that they don't overlap:
        //enter in time (24-hour clock)
        //localdatetime.before & localdatetime.after... compare start times & end times to see if they overlap if they have
        //same date
        //create list of selecteddates?


    }


}
