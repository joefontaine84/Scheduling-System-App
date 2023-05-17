package group.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class ReportData {

    private String type_Month;
    private int count;
    private int appointmentID;
    private String title;
    private String description;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerID;
    private int contactID;
    private String contactName;

    public static ObservableList<ReportData> reportDataOL = FXCollections.observableArrayList();

    public String getType_Month() {
        return type_Month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType_Month(String type_Month) {
        this.type_Month = type_Month;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }
}
