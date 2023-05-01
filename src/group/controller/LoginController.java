package group.controller;

import group.dao.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import static group.model.Users.usersList;
import static group.Main.primaryStage;

/**
 * This class provides functionality to the LoginView FXML page related to the Login portion of the GUI.
 * */

public class LoginController implements Initializable {

    public Text usernameText;
    public Text passwordText;
    public Text userLocationText;
    public Button loginButton;
    public TextField userLocationTextField;
    private boolean englishLanguage = true;
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * This is the first function called once the Login portion of the GUI is fired. This function determines region/language
     * locale information and adjusts the text translation within the GUI accordingly. In addition, this function populates the User
     * Location field with disabled text based on the locale information.
     * */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializer for login controller");
        if (Locale.getDefault().getLanguage().equals("fr")) {
            englishLanguage = false;
            usernameText.setText("Nom d'utilisateur:");
            passwordText.setText("Pot de passe:");
            userLocationText.setText("Emplacement:");
            loginButton.setText("Connexion");
            userLocationTextField.setText("Canada");
        } else if (Locale.getDefault().getISO3Country().equals("USA")){
            userLocationTextField.setText("United States");
            System.out.println(Locale.getDefault().getISO3Country());
        } else if (Locale.getDefault().getISO3Country().equals("GBR")) {
            userLocationTextField.setText("Great Britain");
        }
    }

    /**
     * This function is called when the "Login" button of the GUI login page is clicked and verifies that a correct combination of
     * username and password fields were entered. If a correct combination is entered, the GUI transfers the user to the GUI appointments page,
     * otherwise, a warning message is displayed. Based on the user's locale, the error message within this function will translate to french
     * or default to english.
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
                FXMLLoader AppointmentsView = new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
                root = AppointmentsView.load();
                scene = new Scene(root, 1022, 612);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }
        if (match == false && englishLanguage == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid combination of username and password were entered. Please try again.");
            alert.show();
        }

        if (match == false && englishLanguage == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Une combinaison invalide de nom d'utilisateur et de mot de passe a été saisie. Veuillez réessayer.");
            alert.setHeaderText("Erreur");
            alert.setTitle("Erreur");
            alert.show();
        }

    }

}
