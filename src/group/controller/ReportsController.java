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

    public TableColumn amount;
    public TableColumn typeOrMonth;
    public TableView apptsTableView;
    public static String selectedReport = "";
    public TableColumn appointmentID;
    public TableColumn title;
    public TableColumn description;
    public TableColumn startDateTime;
    public TableColumn endDateTime;
    public TableColumn customerID;
    public Text titleText;
    public ComboBox contactComboBox;
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
            analysis.getContactSchedule();
            apptsTableView.setItems(filteredData);
            appointmentID.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<ReportData, String>("title"));
            typeOrMonth.setCellValueFactory(new PropertyValueFactory<ReportData, String>("typeOrMonth"));
            description.setCellValueFactory(new PropertyValueFactory<ReportData, String>("description"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<ReportData, Timestamp>("startDateTime"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<ReportData, Timestamp>("endDateTime"));
            introText.setVisible(false);
            contactComboBox.setVisible(false);
            apptsTableView.setVisible(true);
            titleText.setText("Schedule for " + contactComboBox.getValue().toString() + ":");
            titleText.setVisible(true);
        }
    }




}
