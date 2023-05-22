package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/**
 * This is the ReportData class meant to store information for data objects when creating various reports within the application.
 * */
public class ReportData {

    private String type_Month;
    private int count;
    private int appointmentID;
    private String title;
    private String description;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerID;
    private String customerName;
    private int contactID;
    private String contactName;

    public static ObservableList<ReportData> reportDataOL = FXCollections.observableArrayList(); // where ReportData objects are stored

    /**
     * Gets the type or month associated with a ReportData object. Whether the string value represents a "type" or a "month" is dependent on which
     * report is selected within the GUI.
     * @return the type or month associated with a ReportData object
     * */
    public String getType_Month() {
        return type_Month;
    }

    /**
     * Gets the count variable of a ReportData object.
     * @return the count variable of a ReportData object.
     * */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count variable of a ReportData object.
     * @param count the count variable to be set.
     * */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Sets the type_Month variable of a ReportData object
     * @param type_Month the type_Month variable to be set.
     * */
    public void setType_Month(String type_Month) {
        this.type_Month = type_Month;
    }

    /**
     * Sets the customerID value of a ReportData object
     * @param customerID the customerID value to be set
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets the appointmentID value of a ReportData object
     * @param appointmentID the appointmentID value to be set
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     *Sets the contactID value of a ReportData object
     * @param contactID the contactID value to bet set
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Sets the description value of a ReportData object
     * @param description the description value to be set
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the endDateTime value of a ReportData object
     * @param endDateTime the endDateTime to be set
     * */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Sets the startDateTme value of a ReportData object
     * @param startDateTime the startDateTime to be set
     * */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Sets the title value of a ReprotData object
     * @param title the title to be set
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the contact name value of a ReportData object
     * @param contactName the contact name to be set
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the contact name of a ReportData object
     * @return the contact name of a ReportData object
     * */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the customerID of a ReportData object
     * @return the customerID of a ReportData object
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the appointmentID of a ReportData object
     * @return the appointmentID of a ReportData object
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets the contactID of a ReportData object
     * @return the contactID of a ReportData object
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * Gets the description of a ReportData object
     * @return the description of a ReportData object
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the endDateTime of a ReportData object
     * @return the endDateTime of a ReportData object
     * */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * Gets the startDateTime of a ReportData object
     * @return the startDateTime of a ReportData object
     * */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * Gets the title of a ReportData object
     * @return the title of a ReportData object
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the customerName of a ReportData object
     * @return the customerName of a ReportData object
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customerName of a ReportData object
     * @param customerName the customerName to be set
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
