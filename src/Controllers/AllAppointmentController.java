package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AllAppointmentController {
    public TableView appointmentTable;
    public TableColumn aptIdColumn;
    public TableColumn titleColumn;
    public TableColumn descColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIdColumn;
    public TableColumn userIdColumn;
    public RadioButton WeekRadio;
    public ToggleGroup MonthWeek;
    public RadioButton MonthRadio;

    public void WeekSelected(ActionEvent actionEvent) {
    }

    public void MonthSelected(ActionEvent actionEvent) {
    }

    public void addAptButton(ActionEvent actionEvent) {
    }

    public void updateAptButton(ActionEvent actionEvent) {
    }

    public void deleteAptButton(ActionEvent actionEvent) {
    }

    public void backButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
