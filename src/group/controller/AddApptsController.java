package group.controller;

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
import static group.dao.Data.*;
import static group.model.Appointments.apptsList;
import static group.model.Contacts.contactList;
import static group.model.Customers.customerList;
import static group.model.Users.usersList;
import static java.time.ZoneId.SHORT_IDS;

/**
 * This class is the controller class for the "Add Appointment" pane of the GUI.
 * */
public class AddApptsController implements Initializable {

    public TextField appointmentIDTextField;
    public ComboBox<String> contactIDComboBox;
    public DatePicker startDatePicker;
    public TextField startDateTimeTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextField endDateTimeTextField;
    public DatePicker endDatePicker;
    public ComboBox<String> userIDComboBox;
    public ComboBox<String> customerIDComboBox;

    /**
     * This function is called when the AddApptsView FXML file is loaded. The function establishes
     * what the appointmentIDTextField variable will display as an appointmentID value, in addition to populating combo-box values
     * within the Add Appointment pane of the GUI.
     * @param resourceBundle the ResourceBundle required for this function
     * @param url the URL required for this function
     * */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sets the appointmentID value within the appropriate TextField
        try {
            appointmentIDTextField.setText(String.valueOf(getNextAppointmentID()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Populate ContactList combo-box
        ObservableList<String> tempContactList = FXCollections.observableArrayList();
        for (Contacts element : contactList) {
            tempContactList.add(element.getName());
        }
        contactIDComboBox.setItems(tempContactList);

        // Populate UserIDList combo-box
        ObservableList<String> tempUserList = FXCollections.observableArrayList();
        for (Users element : usersList) {
            tempUserList.add(element.getUsername());
        }
        userIDComboBox.setItems(tempUserList);

        // Populate the customerID combo-box
        ObservableList<String> tempCustomerList = FXCollections.observableArrayList();
        for (Customers element : customerList) {
            tempCustomerList.add(element.getCustomerName());
        }
        customerIDComboBox.setItems(tempCustomerList);

    }

    /**
     * The save function performs several input validation procedures to ensure data has been entered correctly.
     * Input validation procedures include checking whether the entered appointment information overlaps with
     * other appointments for the Contact selected and checks whether the dates/times entered is consistent
     * with requirements outlined in the performance assessment.
     *
     * THIS FUNCTION USES A LAMBDA EXPRESSION. The lambda expression below is used to create a list
     * of Appointments objects based on the apptsList static variable, in which the list stores Appointments
     * only if the customerID for each Appointment in apptsList matches the user-selected customerID.
     * @throws IOException
     * @throws InputValidationException
     * @throws SQLException
     * */
    @FXML
    public void save() throws IOException, InputValidationException, SQLException {

        try {
            // Variables below are used for input validation
            // lambda expression below used to create list of Appointments objects based on apptsList static variable, in which the list stores Appointments only if the customerID for each Appointment in apptsList matches the user-selected customerID.
            List<Appointments> apptsByCustomer = apptsList.stream().filter(element -> element.getCustomerID() == findCustomerID()).collect(Collectors.toList());
            boolean before = false;
            boolean after = false;
            boolean timeCheck = true;
            Timestamp start = formatDateTime(startDatePicker, startDateTimeTextField);
            Timestamp end = formatDateTime(endDatePicker, endDateTimeTextField);

            /*The for loop below will check if the appointment date/time entered will overlap with
            other appointments scheduled with the same Customer. If there is overlap, an InputValidationException will be thrown. */
            for (Appointments element : apptsByCustomer) {
                if (end.equals(element.getStartDateTime()) || end.before(element.getStartDateTime())) {
                    before = true;
                }
                if (start.after(element.getEndDateTime()) || start.equals(element.getEndDateTime())) {
                    after = true;
                }
                if (before == false && after == false) {
                    throw new InputValidationException("Please enter appointment times that do not overlap with existing appointments for the Customer selected");
                }
            }

            // The if/else statements below check whether the start & end times entered, corrected for the New York timezone, is between 8AM & 10PM.
            if (hourConversionNYTime(start.toLocalDateTime()) >= 8) {             // checks whether start time, corrected for the New York timezone, is >= 8
                if (hourConversionNYTime(end.toLocalDateTime()) < 22) {          // checks whether the end time, corrected for the New York timezone, is <= 22
                    timeCheck = true;
                }
                else if (hourConversionNYTime(end.toLocalDateTime()) == 22 && end.toLocalDateTime().getMinute() == 0) {     // checks whether the end time is exactly 22:00
                    timeCheck = true;
                } else {
                    timeCheck = false;
                }
            } else {
                timeCheck = false;
            }

            // The if statement below checks whether the start & end date/times provided are exactly the same
            if (!(start.toLocalDateTime().toLocalDate().equals(end.toLocalDateTime().toLocalDate()))) {
                timeCheck = false;
            }

            // The if statement below checks to see if the timeCheck variable, after passing through the above input validation code, is false.
            // If so, an InputValidation Exception is thrown.
            if (timeCheck == false) {
                throw new InputValidationException("Please enter appointment times between 8AM and 10PM (EST/EDT) within one given day.");
            }

            // This is the final input validation check, which determines whether the end date/time is before the start date/time
            if (end.before(start)) {
                throw new InputValidationException("The appointment's end time must occur after the appointment's start time.");
            }

            // Provided no errors are thrown based on the above code, a new appointment object is created based on user entered information.
            Appointments appt = new Appointments();
            appt.setAppointmentID(Integer.valueOf(appointmentIDTextField.getText()));
            appt.setCustomerID(findCustomerID());
            appt.setUserID(findUserID());
            appt.setTitle(titleTextField.getText());
            appt.setDescription(descriptionTextField.getText());
            appt.setLocation(locationTextField.getText());
            appt.setType(typeTextField.getText());
            appt.setStartDateTime(formatDateTime(startDatePicker, startDateTimeTextField)); // calls on formatStartDateTime to return a Timestamp value refecting the date & time values entered.
            appt.setEndDateTime(formatDateTime(endDatePicker, endDateTimeTextField));
            appt.setContactID(findContactID());

            apptsList.add(appt);    // appointment object added to the apptsList static variable
            addApptToDB(appt);      // appointment object added to database
            switchToAppointmentsController();

        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select and/or enter values for a contact ID, user ID, dates, and times.");
            alert.show();
        } catch (DateTimeException exception) {     // This exception is thrown if the time is not entered correctly in the appropriate textfields. See formatDateTime function.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter time values in accordance with the following format: HH:mm (hours:minutes).");
            alert.show();
        } catch (InputValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
            alert.show();
        }
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
     * Within the AddApptsController, this function is meant to combine a date chosen in a DatePicker
     * and combine it with a time entered in the GUI such that a Timestamp variable can be returned. This function
     * provides validation that the time has been entered according to the provided pattern.
     * @return Timestamp variable with a strict format of "uuuu-MM-dd HH:mm"
     * @param datePicker a DatePicker within the GUI in which a date will be extracted from
     * @param textField a TextField within the GUI in which a time will be extracted from
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
     * This function exists to convert the hour entered by the user into the corresponding hour of the Eastern Standard Time (EST) time zone.
     * @return The hour in EST after being converted from the user-entered time entry in their respective time zone.
     * @param localDateTime a LocalDateTime entered by the user
     * */
    public long hourConversionNYTime(LocalDateTime localDateTime) {

        String EST = SHORT_IDS.get("EST");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm Z");

        ZonedDateTime localZoneDateTime = localDateTime.atZone(TimeZone.getDefault().toZoneId());

        String localDateTimeFormatted = "";

        String localSubstring = "";
        String ESTSubstring = "";

        localDateTimeFormatted = localZoneDateTime.format(formatter);

        localSubstring = localDateTimeFormatted.substring(17, 20);      // gets the offset from UTC in hours
        ESTSubstring = EST.substring(0,3);  // gets the offset from UTC in hours

        long localOffset = Integer.valueOf(localSubstring);            // converts string value to long data type
        long ESTOffset = Integer.valueOf(ESTSubstring);        // converts string value to long data type

        long timeDifference = localOffset - ESTOffset;              // the amount of hours the local time is from the New York time zone.

        long adjustedHours = 0;

        adjustedHours = localDateTime.getHour() - timeDifference;       // the hour of the day provided within the local time zone represented as New York time zone.

        return adjustedHours;


    }



}
