package group.controller;

import group.dao.Data;
import group.model.Appointments;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import static group.Main.primaryStage;
import static group.model.Appointments.apptsList;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Data.populateAppointments();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        scene = new Scene(root, 1066, 665);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void switchToModAppts() throws IOException {

        if (!apptsTableView.getSelectionModel().isEmpty()) {
            Appointments selected = apptsTableView.getSelectionModel().getSelectedItem();
            Scene scene;
            Parent root;
            FXMLLoader modApptsView = new FXMLLoader(getClass().getResource("/group/views/ModApptsView.fxml"));
            root = modApptsView.load();

            ModApptsController modApptsController = modApptsView.getController();
            modApptsController.appointmentIDTextField.setText(String.valueOf(selected.getAppointmentID()));
            modApptsController.titleTextField.setText(selected.getTitle());
            modApptsController.descriptionTextField.setText(selected.getDescription());
            modApptsController.locationTextField.setText(selected.getLocation());

            //left off here... instead of creating instance of ModApptsController --> create static variable
            // in ModApptsController that stores index of object selected?


            scene = new Scene(root, 1066, 665);
            primaryStage.setScene(scene);
            primaryStage.show();


/*            Product selected = productTableView.getSelectionModel().getSelectedItem();
            Parent root;
            Scene scene;
            FXMLLoader modifyProductView = new FXMLLoader(getClass().getResource("/group/ims/modifyProductView.fxml"));
            selectedIndex = getAllProducts().indexOf(selected); // selectedIndex is a static variable of the ModifyProductController class... information assigned to this variable in the Main form will be accessible for use in the Modify Product form.
            root = modifyProductView.load();

            ModifyProductController modProdCont = modifyProductView.getController(); // creates an instance of the ModifyProductController so that pre-existing product data can be loaded into the textfields
            modProdCont.Product_ID.setText(String.valueOf(selected.getId())); // sets the Product_ID textfield text to the selected product's ID value
            modProdCont.Product_InventoryLevel.setText(String.valueOf(selected.getStock())); // sets the Product_InventoryLevel textfield text to the selected product's stock value
            modProdCont.Product_Max.setText(String.valueOf(selected.getMax())); // sets the Product_Max textfield text to the selected product's max value
            modProdCont.Product_Min.setText(String.valueOf(selected.getMin())); // sets the Product_Min textfield text to the selected product's min value
            modProdCont.Product_Name.setText(String.valueOf(selected.getName())); // sets the Product_Name textfield text to the selected product's name value
            modProdCont.Product_Price.setText(String.valueOf(selected.getPrice())); // sets the Product_Price textfield text to the selected product's price value


            scene = new Scene(root, 1076, 602);
            primaryStage.setScene(scene);
            primaryStage.show();  */






        }


    }




}
