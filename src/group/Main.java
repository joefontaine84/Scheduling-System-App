package group;

import group.controller.LoginController;
import group.dao.Data;
import group.dao.JDBC;
import group.model.Appointments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        JDBC.makeConnection();
        //Data.populateUsers();

        // Question: can a string be converted to a timestamp when seconds aren't included?

        String test = "2021-03-30 07:00:00";
        Timestamp timestamp = Timestamp.valueOf(test);
        System.out.println(timestamp);


        launch(args);
        JDBC.closeConnection();
    }
}
