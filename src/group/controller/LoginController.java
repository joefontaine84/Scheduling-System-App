package group.controller;

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import static group.model.Users.usersList;
import static group.Main.primaryStage;
import static group.controller.AppointmentsController.loggedInUser;

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
     * @param url the URL required for this function
     * @param resourceBundle the ResourceBundle required for this function
     * */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            englishLanguage = false;
            usernameText.setText("Nom d'utilisateur:");
            passwordText.setText("Pot de passe:");
            userLocationText.setText("Emplacement:");
            loginButton.setText("Connexion");
            userLocationTextField.setText("Canada");
        } else if (Locale.getDefault().getISO3Country().equals("USA")){
            userLocationTextField.setText("United States");
        } else if (Locale.getDefault().getISO3Country().equals("GBR")) {
            userLocationTextField.setText("Great Britain");
        }
    }

    /**
     * This function is called when the "Login" button of the GUI login page is clicked and verifies that a correct combination of
     * username and password fields were entered. If a correct combination is entered, the GUI transfers the user to the GUI appointments page,
     * otherwise, a warning message is displayed. Based on the user's locale, the error message within this function will translate to french
     * or default to english.
     * @throws IOException
     * */
    @FXML
    public void verifyUser() throws IOException {
        String providedUsername = username.getText();
        String providedPassword = password.getText();
        boolean match = false;
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(providedUsername) && usersList.get(i).getPassword().equals(providedPassword)) {
                loggedInUser = usersList.get(i);
                match = true;
                Scene scene;
                Parent root;
                FXMLLoader AppointmentsView = new FXMLLoader(getClass().getResource("/group/views/AppointmentsView.fxml"));
                root = AppointmentsView.load();
                scene = new Scene(root, 1183, 665);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }
        // error message in english
        if (match == false && englishLanguage == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid combination of username and password were entered. Please try again.");
            alert.show();
        }
        // error message in french
        if (match == false && englishLanguage == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Une combinaison invalide de nom d'utilisateur et de mot de passe a été saisie. Veuillez réessayer.");
            alert.setHeaderText("Erreur");
            alert.setTitle("Erreur");
            alert.show();
        }
        loginActivity(match);   // calls the loginActivity function with the match variable as a parameter
    }

    /**
     * This function is used to create a login_activity text file, meant to track user login activity.
     * @param successfulLogin a boolean value representing whether the user attempt of logging in was successful (true) or unsuccessful (false)
     * */
    public void loginActivity(boolean successfulLogin) {
        try {
            File loginTracker = new File("login_activity.txt");
            loginTracker.createNewFile();

            String text = "User login attempt...\n"
                    + "Provided Username: " + username.getText() +"\n"
                    + "Local Date & Time: " + Timestamp.valueOf(LocalDateTime.now()).toString() + "\n"
                    + "Successful Login?: " + String.valueOf(successfulLogin) + "\n\n";

            FileWriter write = new FileWriter("login_activity.txt", true);
            write.append(text);
            write.close();

        } catch (IOException exception) {
            exception.getMessage();
        }
    }
}
