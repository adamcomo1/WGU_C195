package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UpdateAppointmentController {
    public TextField customerIdField;
    public TextField userIdField;
    public TextField titleField;
    public TextField descriptionField;
    public ComboBox countryComboBox;
    public ComboBox stateComboBox;
    public ComboBox contactComboBox;
    public ComboBox typeComboBox;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public TextField startTimeField;
    public TextField endTimeField;

    public void saveButtonPressed(ActionEvent actionEvent) {
    }

    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
