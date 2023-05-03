package group.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static group.model.Appointments.apptsList;

public class ModApptsController implements Initializable {
    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField typeTextField;
    public TextField startDateTimeTextField;
    public TextField userIDTextField;
    public TextField customerIDTextField;
    public TextField endDateTimeTextField;
    public TextField contactIDTextField;

    public static int apptIndex;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        appointmentIDTextField.setText(String.valueOf(apptsList.get(apptIndex).getAppointmentID()));
        titleTextField.setText(apptsList.get(apptIndex).getTitle());
        descriptionTextField.setText(apptsList.get(apptIndex).getDescription());
        locationTextField.setText(apptsList.get(apptIndex).getLocation());
        typeTextField.setText(apptsList.get(apptIndex).getType());
        startDateTimeTextField.setText(String.valueOf(apptsList.get(apptIndex).getStartDateTime()));
        userIDTextField.setText(String.valueOf(apptsList.get(apptIndex).getUserID()));
        customerIDTextField.setText(String.valueOf(apptsList.get(apptIndex).getCustomerID()));
        endDateTimeTextField.setText(String.valueOf(apptsList.get(apptIndex).getEndDateTime()));
        contactIDTextField.setText(String.valueOf(apptsList.get(apptIndex).getContactID()));



    }


}
