package group.controller;

import group.dao.Analysis;
import group.model.Contacts;
import group.model.ReportData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static group.model.Contacts.contactList;
import static group.model.ReportData.reportDataOL;

public class ReportsController implements Initializable {

    public TableColumn<ReportData, Integer> amount;
    public TableColumn<ReportData, String> typeOrMonth;
    public TableView<ReportData> apptsTableView;
    public static String selectedReport = "";
    public TableColumn<ReportData, Integer> appointmentID;
    public TableColumn<ReportData, String> title;
    public TableColumn<ReportData, String> description;
    public TableColumn<ReportData, Timestamp> startDateTime;
    public TableColumn<ReportData, Timestamp> endDateTime;
    public TableColumn<ReportData, Integer> customerID;
    public Text titleText;
    public ComboBox<String> contactComboBox;
    public Text introText;
    Analysis analysis = new Analysis();


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (selectedReport == "Appointments By Type") {
            analysis.getUniqueTypes();
            apptsTableView.setItems(reportDataOL);
            amount.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("count"));
            typeOrMonth.setCellValueFactory(new PropertyValueFactory<ReportData, String>("type_Month"));
        }
        if (selectedReport == "Appointments By Month") {
            analysis.getApptsByMonth();
            apptsTableView.setItems(reportDataOL);
            amount.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("count"));
            typeOrMonth.setCellValueFactory(new PropertyValueFactory<ReportData, String>("type_Month"));
        }
        if (selectedReport == "Schedules By Contact") {
            // pop-up to select Contact
            analysis.getContactSchedule();
            ObservableList<String> tempList = FXCollections.observableArrayList();
            for (Contacts contact : contactList) {
                tempList.add(contact.getName());
            }
            contactComboBox.setItems(tempList);

        }

    }

    @FXML
    public void showSchedule() {
        if (!(contactComboBox.getValue() == null)) {
            ObservableList<ReportData> filteredData = reportDataOL.stream().filter(element -> element.getContactName().equals(contactComboBox.getValue().toString())).collect(Collectors.toCollection(FXCollections::observableArrayList));
            apptsTableView.setItems(filteredData);
            appointmentID.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<ReportData, String>("title"));
            typeOrMonth.setCellValueFactory(new PropertyValueFactory<ReportData, String>("type_Month"));
            description.setCellValueFactory(new PropertyValueFactory<ReportData, String>("description"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<ReportData, Timestamp>("startDateTime"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<ReportData, Timestamp>("endDateTime"));
            customerID.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("customerID"));
            introText.setVisible(false);
            contactComboBox.setVisible(false);
            apptsTableView.setVisible(true);
            titleText.setText("Schedule for " + contactComboBox.getValue().toString() + ":");
            titleText.setVisible(true);
        }
    }




}
