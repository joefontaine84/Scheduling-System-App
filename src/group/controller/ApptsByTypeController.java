package group.controller;

import group.dao.Analysis;
import group.model.Appointments;
import group.model.ApptsByType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import static group.model.Appointments.apptsList;
import static group.model.ApptsByType.apptsByTypeOL;

public class ApptsByTypeController implements Initializable {

    public TableColumn amount;
    public TableColumn type;
    public TableView apptsByTypeTableView;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Analysis analysis = new Analysis();
        analysis.getUniqueTypes();
        apptsByTypeTableView.setItems(apptsByTypeOL);
        amount.setCellValueFactory(new PropertyValueFactory<ApptsByType, Integer>("count"));
        type.setCellValueFactory(new PropertyValueFactory<ApptsByType, String>("type"));
    }




}
