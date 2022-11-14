package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignInController {
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
    public void loginPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
