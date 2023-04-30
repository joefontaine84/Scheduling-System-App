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

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));
        primaryStage.setScene(new Scene(root, 360, 359));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
        //Data.populateUsers();
        launch(args);
        JDBC.closeConnection();
    }
}
