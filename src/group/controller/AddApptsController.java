package group.controller;

import group.dao.Data;
import group.helper.InputValidationException;
import group.model.Appointments;
import group.model.Contacts;
import group.model.Users;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static group.Main.primaryStage;
import static group.dao.Data.getNextAppointmentID;
import static group.dao.Data.populateContacts;
import static group.model.Appointments.apptsList;
import static group.model.Contacts.contactList;
import static group.model.Users.usersList;

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
    public ComboBox userIDComboBox;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Establish immutable appointmentID text field
        try {
            appointmentIDTextField.setText(String.valueOf(getNextAppointmentID()));
            //populateContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Populate ContactList
        ObservableList<String> tempContactList = FXCollections.observableArrayList();
        for (Contacts element : contactList) {
            tempContactList.add(element.getName());
        }
        contactIDComboBox.setItems(tempContactList);

        // Populate User ID List
        ObservableList<String> tempUserList = FXCollections.observableArrayList();
        for (Users element : usersList) {
            tempUserList.add(element.getUsername());
        }
        userIDComboBox.setItems(tempUserList);

    }




    @FXML
    public void save() throws IOException, InputValidationException {
        try {
            Appointments appt = new Appointments();
            appt.setAppointmentID(Integer.valueOf(appointmentIDTextField.getText()));
            appt.setCustomerID(Integer.valueOf(customerIDTextField.getText()));
            appt.setUserID(findUserID());
            appt.setTitle(titleTextField.getText());
            appt.setDescription(descriptionTextField.getText());
            appt.setLocation(locationTextField.getText());
            appt.setType(typeTextField.getText());
            appt.setStartDateTime(formatDateTime(startDatePicker, startDateTimeTextField)); // calls on formatStartDateTime to return a Timestamp value refecting the date & time values entered.
            appt.setEndDateTime(formatDateTime(endDatePicker, endDateTimeTextField));
            appt.setContactID(findContactID());


            List<Appointments> apptsByContact = apptsList.stream().filter(element -> element.getContactID() == appt.getContactID()).collect(Collectors.toList());
            for (Appointments element : apptsByContact) {
                // how are overlapping meetings defined? : start time or end time begins between appt
                if (!(appt.getStartDateTime().before(element.getStartDateTime()) && appt.getEndDateTime().before(element.getStartDateTime())) || !(appt.getStartDateTime().after(element.getStartDateTime()) && appt.getEndDateTime().after(element.getEndDateTime()))) {
                    throw new InputValidationException("Please enter appointment times that do not overlap with existing appointments for the Contact selected");
                }
            }

            boolean timeCheck = true;
            if (hourConversionNYTime(appt.getStartDateTime().toLocalDateTime()) >= 8) {             // checks whether start time, corrected for the New York timezone, is >= 8
                if (hourConversionNYTime(appt.getEndDateTime().toLocalDateTime()) <= 22) {          // checks whether the end time, corrected for the New York timezone, is <= 22
                    if (appt.getEndDateTime().toLocalDateTime().getHour() == 22 && appt.getEndDateTime().toLocalDateTime().getMinute() == 0) {  // checks whether the end time is exactly 10:00 PM for the New York timezone.
                        timeCheck = true;
                    } else {
                        timeCheck = false;
                    }
                } else {
                    timeCheck = false;
                }
            } else {
                timeCheck = false;
            }

            if (timeCheck = false) {
                throw new InputValidationException("Please enter appointment times between 8AM and 10PM, EST/EDT.");
            }

            apptsList.add(appt);
            switchToAppointmentsController();
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure integer values are entered where they are expected");
            alert.show();
        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select and/or enter values for a contact ID, user ID, dates, and times.");
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
        FXMLLoader appointmentsController = new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
        root = appointmentsController.load();
        scene = new Scene(root, 1066, 665);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Within the AddApptsController, this function is meant to combine a date chosen in a DatePicker
     * and combine it with a time entered in the GUI such that a Timestamp variable can be returned. This function
     * provides validation that the time has been entered according to the provided pattern.
     */

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

    /**
     * This function returns the corresponding contactID of the Contact that is selected when adding an appointment.
     *
     * @returns The contact ID for the contact selected.
     */

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

    /**
     * This function returns the corresponding userID of the User that is selected when adding an appointment.
     *
     * @returns The user ID for the User selected.
     * */
    public int findUserID() {
        int userID = 0;
        for (Users userObj : usersList) {
            if (userIDComboBox.getValue().equals(userObj.getUsername())) {
                userID = userObj.getUserID();
            }
        }   return userID;
    }


    public long hourConversionNYTime(LocalDateTime localDateTime) {

        ZoneId newYork = ZoneId.of("America/New_York");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm Z");

        ZonedDateTime localZoneDateTime = localDateTime.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime newYorkZoneDateTime = localDateTime.atZone(newYork);

        String localDateTimeFormatted = "";
        String newYorkDateTimeFormatted = "";
        String localSubstring = "";
        String newYorkSubstring = "";

        localDateTimeFormatted = localZoneDateTime.format(formatter);
        newYorkDateTimeFormatted = newYorkZoneDateTime.format(formatter);

        localSubstring = localDateTimeFormatted.substring(17, 20);
        newYorkSubstring = newYorkDateTimeFormatted.substring(17, 20);

        long localOffset = Integer.valueOf(localSubstring);
        long newYorkOffset = Integer.valueOf(newYorkSubstring);

        long timeDifference = localOffset - newYorkOffset;

        long hoursFromNYTime = 0;

        hoursFromNYTime = localDateTime.getHour() - timeDifference;

        return hoursFromNYTime;


    }



}
