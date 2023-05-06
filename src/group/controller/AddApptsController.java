package group.controller;

import group.dao.Data;
import group.model.Appointments;
import group.model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ResourceBundle;

import static group.Main.primaryStage;
import static group.dao.Data.getNextAppointmentID;
import static group.dao.Data.populateContacts;
import static group.model.Appointments.apptsList;
import static group.model.Contacts.contactList;

public class AddApptsController implements Initializable {

    //get ID from sql
    public TextField appointmentIDTextField;
    public ComboBox<String> contactIDComboBox;
    public DatePicker startDatePicker;
    public TextField startDateTimeTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextField userIDTextField;
    public TextField customerIDTextField;
    public TextField endDateTimeTextField;
    public DatePicker endDatePicker;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDTextField.setText(String.valueOf(getNextAppointmentID()));
            //populateContacts();
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
    public void save() throws IOException {
        try {
            Appointments appt = new Appointments();
            appt.setAppointmentID(Integer.valueOf(appointmentIDTextField.getText()));
            appt.setCustomerID(Integer.valueOf(customerIDTextField.getText()));
            appt.setUserID(Integer.valueOf(userIDTextField.getText()));
            appt.setTitle(titleTextField.getText());
            appt.setDescription(descriptionTextField.getText());
            appt.setLocation(locationTextField.getText());
            appt.setType(typeTextField.getText());
            appt.setStartDateTime(formatDateTime(startDatePicker, startDateTimeTextField)); // calls on formatStartDateTime to return a Timestamp value refecting the date & time values entered.
            appt.setEndDateTime(formatDateTime(endDatePicker, endDateTimeTextField));
            appt.setContactID(findContactID());
            apptsList.add(appt);
            switchToAppointmentsController();
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure integer values are entered where they are expected");
            alert.show();
        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Contact.");
            alert.show();
        }//NOTE THAT APPOINTMENTID WILL ALWAYS BE 3 UNTIL THE APPOINTMENTS ARE ADDED INTO THE DATABASE, SEE GETNEXTAPPOINTID function in Data Class.
    }

    @FXML
    public void switchToAppointmentsController() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader appointmentsController= new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
        root = appointmentsController.load();
        scene = new Scene(root, 1066, 665);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Within the AddApptsController, this function is meant to combine a date chosen in a DatePicker
     * and combine it with a time entered in the GUI such that a Timestamp variable can be returned. This function
     * provides validation that the time has been entered according to the provided pattern.
     * */

    public Timestamp formatDateTime(DatePicker datePicker, TextField textField) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

        if (datePicker.getValue() != null) {
            String combinedDateTime = "";
            try {
                String date = datePicker.getValue().format(formatter);
                String time = textField.getText();
                LocalTime localTime = LocalTime.parse(time, timeFormatter);
                combinedDateTime = date + " " + localTime + ":00";
            } catch (DateTimeException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a time for the appointment in the format of HH:mm");
                alert.show();
            }
            return Timestamp.valueOf(combinedDateTime);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a start and end date.");
            alert.show();
            return null;
        }
    }

    /**
     * This function returns the corresponding contactID of the Contact that is selected when adding an appointment.
     * @returns The contact ID for the contact selected.
     * */
    @FXML
    public Integer findContactID() {
        if (contactIDComboBox.getValue() != null) {
            for (Contacts contact : contactList) {
                if (contactIDComboBox.getValue().equals(contact.getName())) {
                    return contact.getContactID();
                }
            }
        }
        return null;
    }




        // this is to obtain the date from the datePicker*/

        //to manage appointments so that they don't overlap:
        //enter in time (24-hour clock)
        //localdatetime.before & localdatetime.after... compare start times & end times to see if they overlap if they have
        //same date
        //create list of selecteddates?





}
