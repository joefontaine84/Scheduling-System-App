package group;

import group.dao.Data;
import group.dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));
        primaryStage.setScene(new Scene(root, 489, 426));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection(); // connects to database
        Data.populateUsers(); // loads all User information from database
        Data.populateContacts(); // loads all Contact information from database
        Data.populateAppointments(); // loads all Appointment information from database
        Data.populateCustomers(); // loads all Customer information from database
        Data.populateCountries(); // loads all Country information from database
        Data.populateFirstLevelDivisions(); // loads all FirstLevelDivision information from database
        launch(args); // launches application
        JDBC.closeConnection(); // closes database connection
    }
}
