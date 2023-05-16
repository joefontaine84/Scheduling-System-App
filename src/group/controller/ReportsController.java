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
    public TableView apptsByTypeTableView;
    public static String selectedReport = "";


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Analysis analysis = new Analysis();
        if (selectedReport == "Appointments By Type") {
            analysis.getUniqueTypes();
        }
        if (selectedReport == "Appointments By Month") {
            analysis.getApptsByMonth();
        }

        apptsByTypeTableView.setItems(reportDataOL);
        amount.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("count"));
        typeOrMonth.setCellValueFactory(new PropertyValueFactory<ReportData, String>("type"));


        //LEFT OFF ADJUSTING CODE SO THAT TYPE OR MONTH IN SCENE BUILDER COLUMN CAN FLUCTUATE BASED ON WHAT
        //REPORT IS SELECTED
    }




}
