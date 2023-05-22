package group.controller;

import group.dao.Data;
import group.helper.InputValidationException;
import group.model.Appointments;
import group.model.Contacts;
import group.model.Customers;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static group.Main.primaryStage;
import static group.dao.Data.updateApptToDB;
import static group.model.Appointments.apptsList;
import static group.model.Contacts.contactList;
import static group.model.Customers.customerList;
import static group.model.Users.usersList;

/**
 * This class is the controller class for the Modify Appointment pane of the GUI.
 * */
public class ModApptsController implements Initializable {
    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;

    public TextField startDateTimeTextField;
    public TextField endDateTimeTextField;
    public ComboBox contactIDComboBox;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox userIDComboBox;

    public static int apptIndex; // this variable stores the index of the apptsList that was selected for modification

    public Appointments selectedAppt = apptsList.get(apptIndex); // the appointment object that was selected for modification
    public ComboBox customerIDComboBox;

    /**
     * This function is called whenever the ModApptsView FXML file is loaded. This function determines what is displayed
     * in the Modify Appointment pane of the GUI and also populates values for the combo-boxes within this pane of the GUI.
     * @param resourceBundle the ResourceBundle required of this function
     * @param url the URL required of this function
     * */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        appointmentIDTextField.setText(String.valueOf(selectedAppt.getAppointmentID()));
        titleTextField.setText(selectedAppt.getTitle());
        descriptionTextField.setText(selectedAppt.getDescription());
        locationTextField.setText(selectedAppt.getLocation());
        typeTextField.setText(selectedAppt.getType());
        setDateTimeFromTS(selectedAppt.getStartDateTime(), startDatePicker, startDateTimeTextField);
        setDateTimeFromTS(selectedAppt.getEndDateTime(), endDatePicker, endDateTimeTextField);

        // Populate ContactList combo-box
        ObservableList<String> tempContactList = FXCollections.observableArrayList();
        for (Contacts element : contactList) {
            tempContactList.add(element.getName());
        }
        contactIDComboBox.setItems(tempContactList);
        contactIDComboBox.setValue(getContactNameFromID());

        // Populate UserIDList combo-box
        ObservableList<String> tempUserList = FXCollections.observableArrayList();
        for (Users element : usersList) {
            tempUserList.add(element.getUsername());
        }
        userIDComboBox.setItems(tempUserList);
        userIDComboBox.setValue(getUsernameFromID());

        // Populate the customerID combo-box
        ObservableList<String> tempCustomerList = FXCollections.observableArrayList();
        for (Customers element : customerList) {
            tempCustomerList.add(element.getCustomerName());
        }
        customerIDComboBox.setItems(tempCustomerList);
        customerIDComboBox.setValue(getCustomerNameFromID());
    }

    /**
     * This function switches the scene back to the main appointments pane of the GUI.
     * @throws IOException
     * */
    @FXML
    public void switchToAppointmentsController() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader appointmentsController = new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
        root = appointmentsController.load();
        scene = new Scene(root, 1183, 665);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This function is used to break a timestamp value into separate date & time fields to populate DatePickers and TextFields
     * within the Modify Appointment pane of the GUI.
     * @param textField a TextField within the GUI that will display the time portion of a timestamp
     * @param datePicker a DatePicker within the GUI that will display the date portion of a timestamp
     * @param timestamp the timestamp in which the date and time elements will be independently displayed within the GUI
     * */
    public void setDateTimeFromTS(Timestamp timestamp, DatePicker datePicker, TextField textField) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        String dateTime = timestamp.toLocalDateTime().format(formatter);
        LocalDate localDate = LocalDate.parse(dateTime.substring(0, 10));
        String localTime = dateTime.substring(11, 16);

        datePicker.setValue(localDate);
        textField.setText(localTime);
    }

    /**
     * Within the ModApptsController, this function is meant to combine a date chosen in a DatePicker
     * and combine it with a time entered in the GUI such that a Timestamp variable can be returned. This function
     * provides validation that the time has been entered according to the provided pattern.
     * @return Timestamp variable with a strict format of "uuuu-MM-dd HH:mm"
     * @param datePicker the DatePicker in which a date value will be extracted from to obtain the date portion of a timestamp
     * @param textField the TextField in which a time value will be extracted from to obtain the time portion of a timestamp
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
     * @return The contact ID for the contact selected.
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
     * @return The user ID for the User selected.
     * */
    public int findUserID() {
        int userID = 0;
        for (Users userObj : usersList) {
            if (userIDComboBox.getValue().equals(userObj.getUsername())) {
                userID = userObj.getUserID();
            }
        }   return userID;
    }

    /**
     * This function returns the corresponding userID of the User that is selected when adding an appointment.
     * @return The user ID for the User selected.
     * */
    public int findCustomerID() {
        int customerID = 0;
        for (Customers customerObj : customerList) {
            if (customerIDComboBox.getValue().equals(customerObj.getCustomerName())) {
                customerID = customerObj.getCustomerID();
            }
        }   return customerID;

    }

    /**
     * This function exists to convert the hour entered by the user into the corresponding hour of the America/New_York time zone.
     * @return The hour in EST/EDT after being converted from the user-entered time entry in their respective time zone.
     * @param localDateTime the LocalDateTime entered by the user
     * */
    public long hourConversionNYTime(LocalDateTime localDateTime) {

        ZoneId newYork = ZoneId.of("America/New_York");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm Z");

        ZonedDateTime localZoneDateTime = localDateTime.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime newYorkZoneDateTime = localDateTime.atZone(newYork);

        String localDateTimeFormatted = "";
        String newYorkDateTimeFormatted = "";
        String localSubstring = "";
        String newYorkSubstring = "";

        localDateTimeFormatted = localZoneDateTime.format(formatter);       // gets the offset from UTC in hours
        newYorkDateTimeFormatted = newYorkZoneDateTime.format(formatter);   // gets the offset from UTC in hours

        localSubstring = localDateTimeFormatted.substring(17, 20);          // converts string value to long data type
        newYorkSubstring = newYorkDateTimeFormatted.substring(17, 20);      // converts string value to long data type

        long localOffset = Integer.valueOf(localSubstring);
        long newYorkOffset = Integer.valueOf(newYorkSubstring);

        long timeDifference = localOffset - newYorkOffset;                  // the amount of hours the local time is from the New York time zone.

        long adjustedHours = 0;

        adjustedHours = localDateTime.getHour() - timeDifference;           // the hour of the day provided within the local time zone represented as New York time zone.

        return adjustedHours;
    }

    /**
     * The save function performs several input validation procedures to ensure data has been entered correctly.
     * Input validation procedures include checking whether the entered appointment information overlaps with
     * other appointments for the Contact selected and checks whether the dates/times entered is consistent
     * with requirements outlined in the performance assessment. THIS FUNCTION USES A LAMBDA EXPRESSION.
     * @throws IOException
     * @throws InputValidationException
     * @throws SQLException
     * */
    @FXML
    public void save() throws IOException, InputValidationException, SQLException {
        try {
            List<Appointments> apptsByContact = apptsList.stream().filter(element -> element.getContactID() == findContactID()).collect(Collectors.toList());
            apptsByContact.remove(selectedAppt); // removes the appointment already existing so that it doesn't compare to itself
            boolean before = false;
            boolean after = false;
            Timestamp start = formatDateTime(startDatePicker, startDateTimeTextField);
            Timestamp end = formatDateTime(endDatePicker, endDateTimeTextField);
            // if apptsByContact list is empty, this for loop does not fire and no exception is thrown
            for (Appointments element : apptsByContact) {
                if (start.before(element.getStartDateTime()) && (end.before(element.getStartDateTime()) || end.equals(element.getStartDateTime()))) {
                    before = true;
                }

                if ((start.after(element.getEndDateTime()) || start.equals(element.getEndDateTime())) && end.after(element.getEndDateTime())) {
                    after = true;
                }
                if (before == false && after == false) {
                    throw new InputValidationException("Please enter appointment times that do not overlap with existing appointments for the Contact selected");
                }
            }

            boolean timeCheck = true;
            if (hourConversionNYTime(start.toLocalDateTime()) >= 8) {             // checks whether start time, corrected for the New York timezone, is >= 8
                if (hourConversionNYTime(end.toLocalDateTime()) < 22) {          // checks whether the end time, corrected for the New York timezone, is <= 22
                    timeCheck = true;
                }
                else if (hourConversionNYTime(end.toLocalDateTime()) == 22 && end.toLocalDateTime().getMinute() == 0) {
                    timeCheck = true;
                } else {
                    timeCheck = false;
                }
            } else {
                timeCheck = false;
            }

            if (!(start.toLocalDateTime().toLocalDate().equals(end.toLocalDateTime().toLocalDate()))) {
                timeCheck = false;
            }

            if (timeCheck == false) {
                throw new InputValidationException("Please enter appointment times between 8AM and 10PM (EST/EDT) within one given day.");
            }

            if (end.before(start)) {
                throw new InputValidationException("The appointment's end time must occur after the appointment's start time.");
            }

            selectedAppt.setAppointmentID(Integer.valueOf(appointmentIDTextField.getText()));
            selectedAppt.setCustomerID(findCustomerID());
            selectedAppt.setUserID(findUserID());
            selectedAppt.setTitle(titleTextField.getText());
            selectedAppt.setDescription(descriptionTextField.getText());
            selectedAppt.setLocation(locationTextField.getText());
            selectedAppt.setType(typeTextField.getText());
            selectedAppt.setStartDateTime(formatDateTime(startDatePicker, startDateTimeTextField)); // calls on formatStartDateTime to return a Timestamp value refecting the date & time values entered.
            selectedAppt.setEndDateTime(formatDateTime(endDatePicker, endDateTimeTextField));
            selectedAppt.setContactID(findContactID());

            updateApptToDB(selectedAppt);
            switchToAppointmentsController();

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
    }

    /**
     * This function gets the contactName value from the selected contactID.
     * @return the contact name (string)
     * */
    public String getContactNameFromID() {
        String name = "";
        for (Contacts contact : contactList) {
            if (contact.getContactID() == apptsList.get(apptIndex).getContactID()) {
                name = contact.getName();
            }
        }
        return name;
    }

    /**
     * This function gets the username value from the selected userID.
     * @return the username (string)
     * */
    public String getUsernameFromID() {
        String username = "";
        for (Users user : usersList) {
            if (user.getUserID() == apptsList.get(apptIndex).getUserID()) {
                username = user.getUsername();
            }
        }
        return username;
    }

    /**
     * This function gets the customerName value from the selected customerID.
     * @return the customerName (string)
     * */
    public String getCustomerNameFromID() {
        String customerName = "";
        for (Customers customer : customerList) {
            if (customer.getCustomerID() == apptsList.get(apptIndex).getCustomerID()) {
                customerName = customer.getCustomerName();
            }
        }
        return customerName;
    }

}
