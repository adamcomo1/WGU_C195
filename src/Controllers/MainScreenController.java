package Controllers;

import DAO.Appointments;
import Models.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    public TableView allCustomerTable;
    public TableColumn customerDivisionColumn;
    public TableColumn customerIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerAddressColumn;
    public TableColumn customerPostalCode;
    public TableView allCustomerTable1;
    public TableColumn apptIdColumn;
    public TableColumn apptTitleColumn;
    public TableColumn aptCustomerIdColumn;
    public TableColumn aptDescriptionColumn;
    public TableColumn aptLocationColumn;
    public TableColumn aptTypeColumn;
    public TableColumn aptStartColumn;
    public TableColumn aptEndColumn;
    public RadioButton viewByMonthRadio;
    public ToggleGroup weekMonth;
    public RadioButton viewByWeekRadio;
    public TableColumn aptContactColumn;
    public TableColumn customerDivisionIdColumn;

    public void addCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AddCustomerView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void customerDeleteButton(ActionEvent actionEvent) {
    }

    public void customerUpdateButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/UpdateCustomerView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addAptButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AddAppointmentView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void monthSelected(ActionEvent actionEvent) {
    }

    public void weekSelected(ActionEvent actionEvent) {
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/UpdateAppointmentView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointment> appointmentObservableList = Appointments.getAllAppointments();
            LocalDateTime upComingAppt = LocalDateTime.now().plusMinutes(15);

            for (Appointment appointment : appointmentObservableList) {
                LocalDateTime start = appointment.getApptStart();
                LocalDateTime now = LocalDateTime.now();
                if (start.isBefore(upComingAppt) && start.isAfter(now) || start.isEqual(now)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setContentText("There is an upcoming appointment within the next 15 minutes.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error");
        }
    }

    public void reportsButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/ReportsView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
