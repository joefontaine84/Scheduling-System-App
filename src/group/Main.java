package group;

import group.controller.LoginController;
import group.dao.Data;
import group.dao.JDBC;
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
        //System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm")));
        Timestamp test1 = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(test1);
        Timestamp test2 = Timestamp.valueOf(LocalDateTime.now().plusDays(2));
        System.out.println(test2);
        System.out.println(test1.before(test2));
        //Data.populateUsers();
        launch(args);
        JDBC.closeConnection();
    }
}
