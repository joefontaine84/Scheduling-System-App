package group.controller;

import group.dao.Data;
import group.model.Appointments;
import javafx.beans.property.Property;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import static group.model.Appointments.apptsList;

/**
 * This class provides various functionality for the AppointmentsView FXML page related to the GUI pane observed
 * by the user after logging in.
 * */
public class AppointmentsController implements Initializable {

    public TableView<Appointments> apptsTableView;
    public TableColumn<Appointments, Integer> appointmentID;
    public TableColumn<Appointments, String> title;
    public TableColumn<Appointments, String> description;
    public TableColumn<Appointments, String> location;
    public TableColumn<Appointments, String> type;
    public TableColumn<Appointments, Timestamp> startDateTime;
    public TableColumn<Appointments, Timestamp> endDateTime;
    public TableColumn<Appointments, Integer> customerID;
    public TableColumn<Appointments, Integer> userID;
    public TableColumn<Appointments, Integer> contactID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Data.populateAppointments();
            apptsTableView.setItems(apptsList);
            appointmentID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
            description.setCellValueFactory((new PropertyValueFactory<Appointments, String>("description")));
            location.setCellValueFactory((new PropertyValueFactory<Appointments, String>("location")));
            type.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("startDateTime"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("endDateTime"));
            customerID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerID"));
            userID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("userID"));
            contactID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("contactID"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
