package Controllers;

import DAO.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.*;

/**
 * Controller for the signIn view.
 */
public class SignInController implements Initializable {
    /**
     * Text Field to hold login username.
     */
    public TextField loginUsername;
    /**
     * Text Field to hold login password.
     */
    public TextField loginPassword;
    /**
     * Label for the username Text Field.
     */
    public Label usernameLabel;
    /**
     * Label for the password Text Field.
     */
    public Label passwordLabel;
    /**
     * Login prompt Label.
     */
    public Label loginPrompt;
    /**
     * Label for displaying the users location.
     */
    public Label loginLocation;
    /**
     * Login button
     */
    public Button loginButton;

    /**
     * Login button press for initiating login with input credentials.
     * Loads Main Screen View.
     * @param actionEvent login button pressed.
     */
    public void loginPressed(ActionEvent actionEvent) throws IOException, SQLException {

        String username = loginUsername.getText();
        String password = loginPassword.getText();
        FileWriter fileWriter = new FileWriter("login_attempts.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        if (Users.logInValidation(username, password)) {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            printWriter.print("Successful login made by " + username + " at " +
                    Timestamp.valueOf(LocalDateTime.now()) + "\n");



        } try {
        if (!Users.logInValidation(username, password)) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Language", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("title"));
            alert.setHeaderText(resourceBundle.getString("header"));
            alert.setContentText(resourceBundle.getString("context"));
            alert.showAndWait();

            printWriter.print("Unsuccessful login made at " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

        } } catch (MissingResourceException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Access Denied");
            alert.setTitle("ALERT");
            alert.setContentText("Invalid Username or Password");
            alert.showAndWait();
        }
        printWriter.close();
    }

    /**
     * Initialize method called on during screen load.
     * Sets text to either english or french based on user's computer settings.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
        ZoneId zoneId = ZoneId.systemDefault();
        loginLocation.setText(String.valueOf(zoneId));

        resourceBundle = ResourceBundle.getBundle("Utilities/Language", Locale.getDefault());
        loginButton.setText(resourceBundle.getString("login"));
        passwordLabel.setText(resourceBundle.getString("password"));
        usernameLabel.setText(resourceBundle.getString("username"));
        loginPrompt.setText(resourceBundle.getString("prompt"));
    } catch (MissingResourceException e) {
            ZoneId zoneId = ZoneId.systemDefault();
            loginLocation.setText(String.valueOf(zoneId));

            loginButton.setText("Login");
            passwordLabel.setText("Password");
            usernameLabel.setText("Username");
            loginPrompt.setText("Please Login");
    }

    }





}
