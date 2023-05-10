package group.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static group.Main.primaryStage;
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


}
