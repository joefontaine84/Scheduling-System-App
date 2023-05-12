package group.controller;

import group.model.Appointments;
import group.model.Countries;
import group.model.Customers;
import group.model.FirstLevelDivisions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static group.Main.primaryStage;
import static group.controller.ModApptsController.apptIndex;
import static group.controller.ModCustomersController.customerIndex;
import static group.model.Customers.customerList;

public class ManageCustomersController implements Initializable {

    public TableView<Customers> customersTableView;
    public TableColumn<Customers, String> customerName;
    public TableColumn<Customers, String> address;
    public TableColumn<Customers, String> postalCode;
    public TableColumn<Customers, String> phoneNumber;
    public TableColumn<Customers, Integer> customerID;
    public TableColumn<Customers, Integer> divisionID;
    public TableColumn <Customers, String> country;
    public TableColumn<Customers, String> division;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersTableView.setItems(customerList);
        customerID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<Customers, String>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customers, String>("phoneNumber"));
        country.setCellValueFactory(new PropertyValueFactory<Customers, String>("countryName"));
        division.setCellValueFactory(new PropertyValueFactory<Customers, String>("divisionName"));
    }

    @FXML
    public void backToAppointments() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
        root = addApptsView.load();
        scene = new Scene(root, 1129, 652);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void switchToAddCustomers() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/AddCustomersView.fxml"));
        root = addApptsView.load();
        scene = new Scene(root, 1129, 652);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void switchToModifyCustomers() throws IOException {
        if (!customersTableView.getSelectionModel().isEmpty()) {
            Customers selected = customersTableView.getSelectionModel().getSelectedItem();
            customerIndex = customerList.indexOf(selected);
            Scene scene;
            Parent root;
            FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/ModCustomersView.fxml"));
            root = addApptsView.load();
            scene = new Scene(root, 1129, 652);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Customer to modify.");
            alert.show();
        }
    }


}
