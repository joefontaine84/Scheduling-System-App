package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;

/**
 * This is the Appointments Class that stores data for Appointment objects.
 * */
public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerID;
    private int userID;
    private int contactID;
    public static ObservableList<Appointments> apptsList = FXCollections.observableArrayList(); // where all appointments are stored

    /**
     * Sets the Appointment object's appointmentID value
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the Appointment object's appointmentID value
     * @return  appointmentID (int)
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the Appointment object's title value
     * @param title the title to be set
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the Appointment object's title value
     * @return  title (string)
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Appointment object's description value
     * @param description the description to be set
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the Appointment object's description value
     * @return  description (string)
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Appointment object's location value
     * @param location the location to be set
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the Appointment object's location value
     * @return  location (string)
     * */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the Appointment object's type value
     * @param type the type to be set
     * */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the Appointment object's type value
     * @return  type (string)
     * */
    public String getType() {
        return type;
    }

    /**
     * Sets the Appointment object's startDateTime value
     * @param startDateTime the startDateTime to be set
     * */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Gets the Appointment object's timestamp value
     * @return  start date/time (timestamp)
     * */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the Appointment object's endDateTime value
     * @param endDateTime the endDateTime to be set
     * */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets the Appointment object's timestamp value
     * @return  end date/time (timestamp)
     * */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets the Appointment object's customerID value
     * @param customerID the customerID to be set
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the Appointment object's customerID value
     * @return  customerID (int)
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the Appointment object's userID value
     * @param userID the userID to be set
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the Appointment object's userID value
     * @return  userID (int)
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the Appointment object's contactID value
     * @param contactID the contactID to be set
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the Appointment object's contactID value
     * @return  contactID (int)
     * */
    public int getContactID() {
        return contactID;
    }

}



