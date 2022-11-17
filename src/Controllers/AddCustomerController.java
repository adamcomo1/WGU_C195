package Controllers;

import DAO.Countries;
import DAO.Customers;
import DAO.FirstLevelDivisions;
import Models.Country;
import Models.Customer;
import Models.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    public TextField customerNameField;
    public TextField addressField;
    public TextField phoneField;
    public TextField postalField;
    public ComboBox<String> stateDropDown;
    public ComboBox<String> countryDropDown;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Country> allCountry = Countries.getAllCountries();
            ObservableList<String> allCountryName = FXCollections.observableArrayList();
            // Lambda
            allCountry.forEach(country -> allCountryName.add(country.getCountryName()));
            countryDropDown.setItems(allCountryName);

        } catch (SQLException e) {
            System.out.println("Error loading countries");
        }

    }


    public void CountrySelected(ActionEvent actionEvent) {
        String countrySelected = countryDropDown.getSelectionModel().getSelectedItem();
        //System.out.println(countrySelected);
        if (countrySelected.equals("U.S")) {
            stateDropDown.setPromptText("State");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getUSDivision();
                stateDropDown.setItems(divisionNames);

            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
            }
        if (countrySelected.equals("UK")) {
            stateDropDown.setPromptText("Region");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getUKDivision();
                stateDropDown.setItems(divisionNames);
            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
        if (countrySelected.equals("Canada")) {
            stateDropDown.setPromptText("Province");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getCanadaDivision();
                stateDropDown.setItems(divisionNames);

            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }

    }

    public void saveNewCustomer(ActionEvent actionEvent) throws IOException {
        try {
            int customerId = Customer.newCustomerId();
            String customerName = customerNameField.getText();
            String address = addressField.getText();
            String postalCode = postalField.getText();
            String phoneNumber = phoneField.getText();
            String division = stateDropDown.getSelectionModel().getSelectedItem();
            int divisionId = FirstLevelDivisions.getDivisionId(division);

            Customers.saveCustomer(customerId, customerName, address, postalCode, phoneNumber, divisionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
