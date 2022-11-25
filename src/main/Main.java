package main;

import DAO.Countries;
import DAO.Customers;
import DAO.FirstLevelDivisions;
import DAO.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;


/**
 * Appointment scheduling application used to manage a companies customers and appointments.
 * @author Adam Comoletti
 */
public class Main extends Application {
    /**
     * Method that creates and loads the main screen.
     *
     * @param primaryStage The main stage of the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/LoginView.fxml")));
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Method that loads test data and launches appliation.
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
        //Locale.setDefault(new Locale("fr"));
        launch(args);
        JDBC.closeConnection();
    }
}
