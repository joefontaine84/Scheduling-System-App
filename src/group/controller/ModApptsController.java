package group.controller;

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

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static group.Main.primaryStage;
import static group.model.Appointments.apptsList;
import static group.model.Contacts.contactList;
import static group.model.Users.usersList;

public class ModApptsController implements Initializable {
    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextField userIDTextField;
    public TextField customerIDTextField;

    public TextField startDateTimeTextField;
    public TextField endDateTimeTextField;
    public ComboBox contactIDComboBox;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox userIDComboBox;

    public static int apptIndex;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        appointmentIDTextField.setText(String.valueOf(apptsList.get(apptIndex).getAppointmentID()));
        titleTextField.setText(apptsList.get(apptIndex).getTitle());
        descriptionTextField.setText(apptsList.get(apptIndex).getDescription());
        locationTextField.setText(apptsList.get(apptIndex).getLocation());
        typeTextField.setText(apptsList.get(apptIndex).getType());

        customerIDTextField.setText(String.valueOf(apptsList.get(apptIndex).getCustomerID()));
        setDateTimeFromTS(apptsList.get(apptIndex).getStartDateTime(), startDatePicker, startDateTimeTextField);
        setDateTimeFromTS(apptsList.get(apptIndex).getEndDateTime(), endDatePicker, endDateTimeTextField);

        // Populate ContactList
        ObservableList<String> tempContactList = FXCollections.observableArrayList();
        for (Contacts element : contactList) {
            tempContactList.add(element.getName());
        }
        contactIDComboBox.setItems(tempContactList);
        contactIDComboBox.setValue(getContactNameFromID());

        // Populate User ID List
        ObservableList<String> tempUserList = FXCollections.observableArrayList();
        for (Users element : usersList) {
            tempUserList.add(element.getUsername());
        }
        userIDComboBox.setItems(tempUserList);
        userIDComboBox.setValue(getUsernameFromID());
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

    public void setDateTimeFromTS(Timestamp timestamp, DatePicker datePicker, TextField textField) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        String dateTime = timestamp.toLocalDateTime().format(formatter);
        LocalDate localDate = LocalDate.parse(dateTime.substring(0, 10));
        String localTime = dateTime.substring(11, 16);

        datePicker.setValue(localDate);
        textField.setText(localTime);
    }

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
            System.out.println(apptsByContact.size());
            boolean before = false;
            boolean after = false;
            for (Appointments element : apptsByContact) {
                if (appt.getStartDateTime().before(element.getStartDateTime()) && (appt.getEndDateTime().before(element.getStartDateTime()) || appt.getEndDateTime().equals(element.getStartDateTime()))) {
                    before = true;
                }

                if ((appt.getStartDateTime().after(element.getEndDateTime()) || appt.getStartDateTime().equals(element.getEndDateTime())) && appt.getEndDateTime().after(element.getEndDateTime())) {
                    after = true;
                }

                if (before == false && after == false) {
                    throw new InputValidationException("Please enter appointment times that do not overlap with existing appointments for the Contact selected");
                }
            }

            boolean timeCheck = true;
            if (hourConversionNYTime(appt.getStartDateTime().toLocalDateTime()) >= 8) {             // checks whether start time, corrected for the New York timezone, is >= 8
                if (hourConversionNYTime(appt.getEndDateTime().toLocalDateTime()) < 22) {          // checks whether the end time, corrected for the New York timezone, is <= 22
                    timeCheck = true;
                }
                else if (appt.getEndDateTime().toLocalDateTime().getHour() == 22 && appt.getEndDateTime().toLocalDateTime().getMinute() == 0) {
                    timeCheck = true;
                } else {
                    timeCheck = false;
                }
            } else {
                timeCheck = false;
            }


            if (timeCheck == false) {
                throw new InputValidationException("Please enter appointment times between 8AM and 10PM, EST/EDT.");
            }

            if (appt.getEndDateTime().before(appt.getStartDateTime())) {
                throw new InputValidationException("The appointment's end time must occur after the appointment's start time.");
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

    public String getContactNameFromID() {
        String name = "";
        for (Contacts contact : contactList) {
            if (contact.getContactID() == apptsList.get(apptIndex).getContactID()) {
                name = contact.getName();
            }
        }
        return name;
    }

    public String getUsernameFromID() {
        String username = "";
        for (Users user : usersList) {
            if (user.getUserID() == apptsList.get(apptIndex).getUserID()) {
                username = user.getUsername();
            }
        }
        return username;
    }



}
