package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    public TextField customerNameField;
    public TextField addressField;
    public TextField phoneField;
    public TextField postalField;
    public ComboBox stateDropDown;
    public ComboBox countryDropDown;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void stateSelected(ActionEvent actionEvent) {
    }

    public void CountrySelected(ActionEvent actionEvent) {
    }

    public void saveNewCustomer(ActionEvent actionEvent) {
    }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
