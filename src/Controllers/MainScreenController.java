package Controllers;

import DAO.Appointments;
import DAO.Customers;
import Models.Appointment;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    public TableView<Customer> allCustomerTable;
    public TableColumn<Customer, String> customerDivisionColumn;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerPhoneColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerPostalCode;
    public TableColumn apptIdColumn;
    public TableColumn apptTitleColumn;
    public TableColumn aptCustomerIdColumn;
    public TableColumn aptDescriptionColumn;
    public TableColumn aptLocationColumn;
    public TableColumn aptTypeColumn;
    public TableColumn aptStartColumn;
    public TableColumn aptEndColumn;
    public RadioButton viewByMonthRadio;
    public RadioButton viewAllRadio;
    public ToggleGroup weekMonth;
    public RadioButton viewByWeekRadio;
    public TableColumn aptContactColumn;
    public TableColumn aptUserIdColumn;
    public TableColumn<Customer, Integer> customerDivisionIdColumn;
    public TableView<Appointment> allApptTable;
    /**
     * Boolean value to store if an alert has been given already.
     */
    public static boolean alerted = false;
    /**
     * Boolean to store if there is an upcoming appointment or not.
     */
    public static boolean upomingAppointment = false;
    public Label upcomingApptLabel;

    /**
     * Method used to get the customer to modify.
     * @return
     */
    public static Customer getCustomerToModify() {
        return customerToModify;
    }

    /**
     * Method used to get the appointment to modify.
     * @return
     */
    public static Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    private static Customer customerToModify;

    private static Appointment appointmentToModify;

    /**
     * Method to get to the add customer view.
     * @param actionEvent add customer button pressed.
     * @throws IOException
     */
    public void addCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AddCustomerView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to delete selected customer object.
     * Provided a confirmation pop-up and cannot delete customer if they have appointments.
     * @param actionEvent delete button pressed.
     * @throws SQLException
     */
    public void customerDeleteButton(ActionEvent actionEvent) throws SQLException {
        Customer customerSelected = allCustomerTable.getSelectionModel().getSelectedItem();
        int customer = allCustomerTable.getSelectionModel().getSelectedItem().getCustomerId();
        if (customerSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Customer Selected");
            alert.showAndWait();
        }
        else if (Appointments.checkForAppointment(customer)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't Delete");
            alert.setContentText("Cannot delete a customer who still has appointments associated.");
            alert.showAndWait();
        }
        else  {
            int customerID = allCustomerTable.getSelectionModel().getSelectedItem().getCustomerId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you wish to delete the selected customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Customers.deleteCustomer(customerID);

                ObservableList<Customer> allCustomers = Customers.getAllCustomers();
                allCustomerTable.setItems(allCustomers);
            }
        }
    }

    /**
     * Method used to get to update customer view.
     * @param actionEvent update customer button pressed.
     * @throws IOException
     */
    public void customerUpdateButton(ActionEvent actionEvent) throws IOException {
        customerToModify = allCustomerTable.getSelectionModel().getSelectedItem();
         if (customerToModify == null) {
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("No Customer Selected");
             alert.setContentText("Please select a customer to update.");
             alert.showAndWait();
         }
         else {
             Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/UpdateCustomerView.fxml")));
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
         }
    }

    /**
     * Method used to add an appointment to a customer.
     * @param actionEvent add appointment button pressed.
     * @throws IOException
     */
    public void addAptButton(ActionEvent actionEvent) throws IOException {
        customerToModify = allCustomerTable.getSelectionModel().getSelectedItem();
        if (customerToModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select a customer to add an appointment.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AddAppointmentView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method to filter appointment table based on current month.
     * @param actionEvent month radio selected.
     */
    public void monthSelected(ActionEvent actionEvent) {
        try {
        ObservableList<Appointment> appointmentObservableList = Appointments.getAllAppointments();
        ObservableList<Appointment> monthObservableList = FXCollections.observableArrayList();
        LocalDateTime month = LocalDateTime.now().plusMonths(1);

        for (Appointment appointment : appointmentObservableList) {
            if (appointment.getApptEnd().isBefore(month) &&
                    appointment.getApptEnd().isAfter(LocalDateTime.now().minusDays(1))) {
                monthObservableList.add(appointment);
            }
        }
        allApptTable.setItems(monthObservableList); } catch (SQLException e) {
            System.out.println("Bad appointment data");
        }
    }
    /**
     * Method to filter appointment table based on current week.
     * @param actionEvent week radio selected.
     */
    public void weekSelected(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> appointmentObservableList = Appointments.getAllAppointments();
            ObservableList<Appointment> weekObservableList = FXCollections.observableArrayList();
            LocalDateTime week = LocalDateTime.now().plusWeeks(1);

            for (Appointment appointment : appointmentObservableList) {
                if (appointment.getApptEnd().isBefore(week) &&
                        appointment.getApptEnd().isAfter(LocalDateTime.now().minusDays(1))) {
                    weekObservableList.add(appointment);
                }
            }
            allApptTable.setItems(weekObservableList); } catch (SQLException e) {
            System.out.println("Bad appointment data");
        }

    }

    /**
     * Method used to delete the selected appointment record.
     * @param actionEvent delete appointment button pressed.
     * @throws SQLException
     */
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment appointmentSelected = allApptTable.getSelectionModel().getSelectedItem();

        if (appointmentSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Appointment Selected");
            alert.showAndWait();
        }
        else {
            int ApptId = allApptTable.getSelectionModel().getSelectedItem().getAppointmentId();
            String apptType = appointmentSelected.getApptType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you wish to delete appointment " + ApptId + " of type " + apptType + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Appointments.deleteAppointment(ApptId);

                ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();
                allApptTable.setItems(allAppointments);
            }
        }

    }

    /**
     * Method used to get to the update appointment view.
     * @param actionEvent update appointment button pressed.
     * @throws IOException
     */
    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        appointmentToModify = allApptTable.getSelectionModel().getSelectedItem();
        if (appointmentToModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select an appointment to update.");
            alert.showAndWait();
        }
        else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/UpdateAppointmentView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Initialize method called on during screen load.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointment> appointmentObservableList = Appointments.getAllAppointments();
            LocalDateTime upComingAppt = LocalDateTime.now().plusMinutes(15);

            for (Appointment appointment : appointmentObservableList) {
                LocalDateTime start = appointment.getApptStart();
                LocalDateTime now = LocalDateTime.now();
                if (start.isBefore(upComingAppt) && start.isAfter(now) || start.isEqual(now)) {
                    upomingAppointment = true;
                    upcomingApptLabel.setText("Upcoming appointment: ID " + appointment.getAppointmentId() + " Start: "
                            + appointment.getApptStart());
                    if (!alerted) {
                        alerted = true;

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Upcoming Appointment");
                        alert.setContentText("There is an upcoming appointment within the next 15 minutes.");
                        alert.showAndWait();
                    }
                }
                if (!upomingAppointment) {
                    upcomingApptLabel.setText("No upcoming appointments");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error");
        } try {
            allCustomerTable.setItems(Customers.getAllCustomers());
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerDivisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
            customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        } catch (SQLException e) {
            System.out.println("Customer data invalid");
        }
        try {
            allApptTable.setItems(Appointments.getAllAppointments());
        } catch (SQLException e) {
            System.out.println("Appointment Data invalid");
        }
        apptIdColumn.setCellValueFactory((new PropertyValueFactory<>("appointmentId")));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        aptCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Method used to get to the reports view.
     * @param actionEvent
     * @throws IOException
     */
    public void reportsButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/ReportsView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to display all appointments in appointments table.
     * @param actionEvent view all radio selected.
     */
    public void viewAllSelected(ActionEvent actionEvent) {
        try {
            allApptTable.setItems(Appointments.getAllAppointments());
        } catch (SQLException e) {
            System.out.println("Appointment Data invalid");
        }
        apptIdColumn.setCellValueFactory((new PropertyValueFactory<>("appointmentId")));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        aptCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));

    }
}
