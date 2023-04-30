package group.controller;

import group.dao.Data;
import group.dao.JDBC;
import group.dao.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static group.dao.Data.usersList;
import static group.Main.primaryStage;

/**
 * This class provides functionality to the LoginView FXML page related to the Login portion of the GUI.
 * */

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializer for login controller");
    }

    /**
     * This function is called when the "Login" button of the GUI login page is clicked and verifies that a correct combination of
     * username and password fields were entered. If a correct combination is entered, the GUI transfers the user to the GUI appointments page,
     * otherwise, a warning message is displayed.
     * */
    @FXML
    public void verifyUser() throws SQLException, IOException {
        Data.populateUsers();
        String providedUsername = username.getText();
        String providedPassword = password.getText();
        boolean match = false;
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(providedUsername) && usersList.get(i).getPassword().equals(providedPassword)) {
                // switch to new scene
                match = true;
                Scene scene;
                Parent root;
                FXMLLoader AppointmentsView = new FXMLLoader(getClass().getResource("/views/AppointmentsView.fxml"));
                root = AppointmentsView.load();
                scene = new Scene(root, 1076, 520);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }
        if (match == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid combination of username and password were entered. Please try again.");
            alert.show();
        }
    }

}
