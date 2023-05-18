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
import static group.model.Customers.customerList;
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

    public Appointments selectedAppt = apptsList.get(apptIndex);
    public ComboBox customerIDComboBox;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        appointmentIDTextField.setText(String.valueOf(selectedAppt.getAppointmentID()));
        titleTextField.setText(selectedAppt.getTitle());
        descriptionTextField.setText(selectedAppt.getDescription());
        locationTextField.setText(selectedAppt.getLocation());
        typeTextField.setText(selectedAppt.getType());
        //customerIDTextField.setText(String.valueOf(selectedAppt.getCustomerID()));
        setDateTimeFromTS(selectedAppt.getStartDateTime(), startDatePicker, startDateTimeTextField);
        setDateTimeFromTS(selectedAppt.getEndDateTime(), endDatePicker, endDateTimeTextField);

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

        ObservableList<String> tempCustomerList = FXCollections.observableArrayList();
        for (Customers element : customerList) {
            tempCustomerList.add(element.getCustomerName());
        }
        customerIDComboBox.setItems(tempCustomerList);
        customerIDComboBox.setValue(getCustomerNameFromID());
    }


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

    public int findCustomerID() {
        int customerID = 0;
        for (Customers customerObj : customerList) {
            if (customerIDComboBox.getValue().equals(customerObj.getCustomerName())) {
                customerID = customerObj.getCustomerID();
            }
        }   return customerID;

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


            List<Appointments> apptsByContact = apptsList.stream().filter(element -> element.getContactID() == selectedAppt.getContactID()).collect(Collectors.toList());
            System.out.println(apptsByContact.size());
            boolean before = false;
            boolean after = false;
            for (Appointments element : apptsByContact) {
                if (selectedAppt.getStartDateTime().before(element.getStartDateTime()) && (selectedAppt.getEndDateTime().before(element.getStartDateTime()) || selectedAppt.getEndDateTime().equals(element.getStartDateTime()))) {
                    before = true;
                }

                if ((selectedAppt.getStartDateTime().after(element.getEndDateTime()) || selectedAppt.getStartDateTime().equals(element.getEndDateTime())) && selectedAppt.getEndDateTime().after(element.getEndDateTime())) {
                    after = true;
                }

                if (before == false && after == false) {
                    throw new InputValidationException("Please enter appointment times that do not overlap with existing appointments for the Contact selected");
                }
            }

            boolean timeCheck = true;
            if (hourConversionNYTime(selectedAppt.getStartDateTime().toLocalDateTime()) >= 8) {             // checks whether start time, corrected for the New York timezone, is >= 8
                if (hourConversionNYTime(selectedAppt.getEndDateTime().toLocalDateTime()) < 22) {          // checks whether the end time, corrected for the New York timezone, is <= 22
                    timeCheck = true;
                }
                else if (selectedAppt.getEndDateTime().toLocalDateTime().getHour() == 22 && selectedAppt.getEndDateTime().toLocalDateTime().getMinute() == 0) {
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

            if (selectedAppt.getEndDateTime().before(selectedAppt.getStartDateTime())) {
                throw new InputValidationException("The appointment's end time must occur after the appointment's start time.");
            }

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
