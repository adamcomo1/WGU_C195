package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ReportsController {

    public TableView byContactTable;
    public TableColumn contactApptIdColumn;
    public TableColumn contactTitleColumn;
    public TableColumn contactCustomerIdColumn;
    public TableColumn contactContactIdColumn;
    public TableColumn contactDescriptionColumn;
    public TableColumn contactLocationColumn;
    public TableColumn contactTypeColumn;
    public TableColumn contactStartColumn;
    public TableColumn contactEndColumn;
    public ComboBox typeComboBox;
    public Label typeCount;
    public Label monthCount;
    public Label contactCount;
    public Label countryCount;
    public ComboBox monthComboBox;
    public ComboBox contactComboBox;
    public ComboBox countryComboBox;

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
