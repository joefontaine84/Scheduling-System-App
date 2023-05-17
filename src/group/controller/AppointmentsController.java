package group.controller;

import group.model.Appointments;
import group.model.Users;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static group.Main.primaryStage;
import static group.model.Appointments.apptsList;
import static group.controller.ModApptsController.apptIndex;
import static group.controller.ReportsController.selectedReport;

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
    public RadioButton apptsByWeek;
    public RadioButton apptsByMonth;
    public RadioButton allAppts;

    public static Users loggedInUser;
    public ComboBox reportsComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            //Data.populateAppointments();
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

            ObservableList<String> reports = FXCollections.observableArrayList();
            reports.add("Appointments By Type");
            reports.add("Appointments By Month");
            reports.add("Schedules By Contact");
            reportsComboBox.setItems(reports);

    }

    /**
     * This function switches the GUI from the main Appointments view to the Add Appointments view.
     * @throws IOException
     * */
    @FXML
    public void switchToAddAppts() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/AddApptsView.fxml"));
        root = addApptsView.load();
        scene = new Scene(root, 745, 468);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This function changes the view from AppointmentsView to ModApptsView. Additionally, the function records
     * the index of the appointment object selected of the apptsList static variable so this information can
     * be used in the ModApptsController initializer function.
     *
     * This function is triggered when the "Modify Appointment" button is clicked within the appointments page
     * of the GUI.
     *
     * @throws IOException
     * */
    @FXML
    public void switchToModAppts() throws IOException {

        if (!apptsTableView.getSelectionModel().isEmpty()) {
            Appointments selected = apptsTableView.getSelectionModel().getSelectedItem();
            apptIndex = apptsList.indexOf(selected);
            Scene scene;
            Parent root;
            FXMLLoader modApptsView = new FXMLLoader(getClass().getResource("/group/views/ModApptsView.fxml"));
            root = modApptsView.load();
            scene = new Scene(root, 745, 468);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to modify.");
            alert.show();
        }
    }

    /**
     * This function deletes an appointment from the apptsList static variable.
     * */
    @FXML
    public void deleteAppointment() {

        if (!apptsTableView.getSelectionModel().isEmpty()) {
            Appointments selectedAppt = apptsTableView.getSelectionModel().getSelectedItem();
            apptsList.remove(apptsList.indexOf(selectedAppt));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have deleted Appointment ID: " + selectedAppt.getAppointmentID() + "; Type of appointment: " + selectedAppt.getType() + ".");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete.");
            alert.show();
        }
    }

    @FXML
    public void switchToManageCustomers() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader manageCustomersView = new FXMLLoader(getClass().getResource("/group/views/ManageCustomersView.fxml"));
        root = manageCustomersView.load();
        scene = new Scene(root, 1066, 665);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void exit () {
        Platform.exit();
    }

    /**
     * This function applies to the three radio buttons in the Appointments pane of the GUI and controls how the displayed appointments are
     * filtered by the selected button (all appointments, by current month, or by current week).
     * */
    @FXML
    public void timeFilter() {
        ObservableList<Appointments> tempList = FXCollections.observableArrayList();
        if (apptsByWeek.isSelected()) {
            LocalDateTime dateTimeOffset = LocalDateTime.now().plusDays(7);
            tempList = apptsList.stream().filter(element -> element.getStartDateTime().before(Timestamp.valueOf(dateTimeOffset)) && element.getStartDateTime().after(Timestamp.valueOf(LocalDateTime.now()))).collect(Collectors.toCollection(FXCollections::observableArrayList));
            apptsTableView.setItems(tempList);
        } else if (apptsByMonth.isSelected()) {
            int month = LocalDateTime.now().getMonthValue();
            int year = LocalDateTime.now().getYear();
            tempList = apptsList.stream().filter(element -> element.getStartDateTime().toLocalDateTime().getMonthValue() == month && element.getStartDateTime().toLocalDateTime().getYear() == year).collect(Collectors.toCollection(FXCollections::observableArrayList));
            apptsTableView.setItems(tempList);
        } else if (allAppts.isSelected()) {
            apptsTableView.setItems(apptsList);
        }
    }

    public void meetingsInFifteen() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlusFifteenMin = LocalDateTime.now().plusMinutes(15);
        ObservableList<Appointments> tempList = FXCollections.observableArrayList();
        tempList = apptsList.stream().filter(element -> element.getUserID() == loggedInUser.getUserID()).collect(Collectors.toCollection(FXCollections::observableArrayList));
        for (Appointments appts : tempList) {
            LocalDateTime startTime = appts.getStartDateTime().toLocalDateTime();
            if (!((startTime.equals(now) || startTime.equals(nowPlusFifteenMin)) || (startTime.isAfter(now) && startTime.isBefore(nowPlusFifteenMin)))) {
                tempList.remove(appts);
            }
        }
        if (tempList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no appointments within the next 15 minutes.");
            alert.show();
        } else {
            // create alert that prints appointment IDs, date and time
            String apptsInFifteen = "The following appointments are scheduled within fifteen minutes:\n\n";
            for (Appointments appts : tempList) {
                String id = String.valueOf(appts.getAppointmentID());
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String date = appts.getStartDateTime().toLocalDateTime().toLocalDate().toString();
                String time = appts.getStartDateTime().toLocalDateTime().toLocalTime().format(timeFormatter).toString();
                apptsInFifteen = apptsInFifteen + "Appointment ID: " + id + "    " + "Date: " + date + "    " + "Time: " + time + "\n";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, apptsInFifteen);
            alert.show();
        }
    }

    @FXML
    public void reportsSelection() throws IOException {
        selectedReport = reportsComboBox.getValue().toString();
        if (selectedReport.equals("Appointments By Type")) {
            Scene scene;
            Stage stage = new Stage();
            Parent root;
            FXMLLoader apptsByType = new FXMLLoader(getClass().getResource("/group/views/ApptsByTypeView.fxml"));
            root = apptsByType.load();
            scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        }
        if (selectedReport.equals("Appointments By Month")) {
            Scene scene;
            Stage stage = new Stage();
            Parent root;
            FXMLLoader apptsByType = new FXMLLoader(getClass().getResource("/group/views/ApptsByMonthView.fxml"));
            root = apptsByType.load();
            scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        }
        if (selectedReport.equals("Schedules By Contact")) {
            Scene scene;
            Stage stage = new Stage();
            Parent root;
            FXMLLoader apptsByType = new FXMLLoader(getClass().getResource("/group/views/SchedulesByContactView.fxml"));
            root = apptsByType.load();
            scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        }
    }





}
