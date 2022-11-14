package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainScreenController {
    public TableView allCustomerTable;
    public TableColumn customerDivisionColumn;
    public TableColumn customerIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerAddressColumn;
    public TableColumn customerPostalCode;

    public void addCustomerButton(ActionEvent actionEvent) {
    }

    public void customerDeleteButton(ActionEvent actionEvent) {
    }

    public void customerUpdateButton(ActionEvent actionEvent) {
    }

    public void allAppointmentButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AllAppointmentView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
