package group.controller;

import group.dao.Data;
import group.helper.InputValidationException;
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
import java.time.*;
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
    public void save() throws IOException, InputValidationException {
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

            //Problem: logic check that user-entered start time and end time is within business hours
            //Facts: (1) business hours are 8a to 10p, EST
            //To-Do: (1) get values entered in start and end times
            //          (2) compare values to business hours, must compare Locale to EST
            //Actions: (1) Created inputvalidation custom exception... now I can throw this error if there is a
            //              logic check that is invalid and at it to the catch list below.

            // LocalTime timeConversion = appt.getStartDateTime().toLocalDateTime().toLocalTime();
            // timeConversion.atOffset()

 /*           ZoneId asiaSingapore = ZoneId.of("Asia/Singapore");
            LocalDateTime currentLocalTime = LocalDateTime.now();

            ZonedDateTime zonedDateTime = currentLocalTime.atZone(asiaSingapore);
            LocalDateTime asiaSingaporeTime = LocalDateTime.now(asiaSingapore);

            */
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
            ZoneId zoneId = ZoneId.of("America/New_York");

            ZonedDateTime start = appt.getStartDateTime().toLocalDateTime().atZone(zoneId); //start time in EST
            ZonedDateTime end = appt.getEndDateTime().toLocalDateTime().atZone(zoneId); // end time in EST

            if (!(start.getHour() >= 8 && end.getHour() <= 22)) {
                throw new InputValidationException("Please enter appointment times between 8AM and 10PM, EST.");
            }



            apptsList.add(appt);
            switchToAppointmentsController();
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure integer values are entered where they are expected");
            alert.show();
        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select and/or enter values for a contact ID, dates, and times.");
            alert.show();
        } catch (DateTimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter time values as the following format: HH:mm");
            alert.show();
        } catch (InputValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
            alert.show();
        }

        //NOTE THAT APPOINTMENTID WILL ALWAYS BE 3 UNTIL THE APPOINTMENTS ARE ADDED INTO THE DATABASE, SEE GETNEXTAPPOINTID function in Data Class.
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

        String combinedDateTime = "";

        String date = datePicker.getValue().format(formatter);
        String time = textField.getText();
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        combinedDateTime = date + " " + localTime + ":00";
        return Timestamp.valueOf(combinedDateTime);
    }
/*        if (datePicker.getValue() != null) {
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
    }*/

    /**
     * This function returns the corresponding contactID of the Contact that is selected when adding an appointment.
     * @returns The contact ID for the contact selected.
     * */
    @FXML
    public int findContactID() {
        int contactID = 0;
        for (Contacts contactObj : contactList) {
            if (contactIDComboBox.getValue().equals(contactObj.getName())) {
                contactID = contactObj.getContactID();
            }
        }
        return contactID;
    }




        // this is to obtain the date from the datePicker*/

        //to manage appointments so that they don't overlap:
        //enter in time (24-hour clock)
        //localdatetime.before & localdatetime.after... compare start times & end times to see if they overlap if they have
        //same date
        //create list of selecteddates?





}
