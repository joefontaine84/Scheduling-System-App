package group.controller;

import group.dao.Data;
import group.helper.InputValidationException;
import group.model.Appointments;
import group.model.Customers;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static group.Main.primaryStage;
import static group.controller.ModCustomersController.customerIndex;
import static group.dao.Data.deleteCustomerFromDB;
import static group.model.Appointments.apptsList;
import static group.model.Customers.customerList;

/**
 * This class is the controller class for the Manage Customers pane of the GUI.
 * */
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

    /**
     * This function is called whenever the ManageCustomersView FXML file is loaded. This function sets the customersTableView.
     * @param resourceBundle the ResourceBundle required for this function
     * @param url the URl required for this function
     * */
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

    /**
     * This function switches the scene to the Appointments pane of the GUI.
     * @throws IOException
     * */
    @FXML
    public void backToAppointments() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
        root = addApptsView.load();
        scene = new Scene(root, 1183, 665);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This function switches the scene to the Add Customers pane of the GUI.
     * @throws IOException
     * */
    @FXML
    public void switchToAddCustomers() throws IOException {
        Scene scene;
        Parent root;
        FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/AddCustomersView.fxml"));
        root = addApptsView.load();
        scene = new Scene(root, 745, 468);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This function swithces the scene to the Modify Customers pane of the GUI.
     * @throws IOException
     * */
    @FXML
    public void switchToModifyCustomers() throws IOException {
        if (!customersTableView.getSelectionModel().isEmpty()) {
            Customers selected = customersTableView.getSelectionModel().getSelectedItem();
            customerIndex = customerList.indexOf(selected);
            Scene scene;
            Parent root;
            FXMLLoader addApptsView = new FXMLLoader(getClass().getResource("/group/views/ModCustomersView.fxml"));
            root = addApptsView.load();
            scene = new Scene(root, 745, 468);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Customer to modify.");
            alert.show();
        }
    }

    /**
     * This function deletes a customer from the customer list, in addition to all associated appointments with the customer. The associated appointments
     * are deleted before the customer object is deleted. If a customer object is not selected within the tableview within the GUI, an alert message appears
     * informing the user that they must first select a customer object.
     * @throws InputValidationException
     * @throws SQLException
     * */
    @FXML
    public void delete () throws InputValidationException, SQLException {

        if (!(customersTableView.getSelectionModel().isEmpty())) {
            ArrayList<Appointments> tempList = new ArrayList<>();
            for (Appointments appts : apptsList) {
                if (appts.getCustomerID() == customersTableView.getSelectionModel().getSelectedItem().getCustomerID()) {
                    tempList.add(appts);
                }
            }
            for (Appointments appts : tempList) {
                Data.deleteApptFromDB(appts);
                apptsList.remove(appts);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You have successfully deleted the selected Customer with Customer ID: " + customersTableView.getSelectionModel().getSelectedItem().getCustomerID() + ", in addition to all associated appointments with this Customer.");
            alert.show();
            deleteCustomerFromDB(customersTableView.getSelectionModel().getSelectedItem());
            customerList.remove(customersTableView.getSelectionModel().getSelectedItem());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer record to delete.");
            alert.show();
        }
    }
}
