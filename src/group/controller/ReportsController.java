package group.controller;

import group.dao.Analysis;
import group.model.ReportData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

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


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Analysis analysis = new Analysis();
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
            analysis.getContactSchedule();
            apptsTableView.setItems(reportDataOL);
            appointmentID.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("appointmentID"));
        }



    }




}
