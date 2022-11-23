package Controllers;

import DAO.Appointments;
import DAO.Contacts;
import DAO.Countries;
import Models.Appointment;
import Models.Contact;
import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class ReportsController implements Initializable {

    public TableView<Appointment> byContactTable;
    public TableColumn contactApptIdColumn;
    public TableColumn contactTitleColumn;
    public TableColumn contactCustomerIdColumn;
    public TableColumn contactContactIdColumn;
    public TableColumn contactDescriptionColumn;
    public TableColumn contactLocationColumn;
    public TableColumn contactTypeColumn;
    public TableColumn contactStartColumn;
    public TableColumn contactEndColumn;
    public ComboBox<String> typeComboBox;
    public Label typeCount;
    public Label monthCount;
    public Label contactCount;
    public Label countryCount;
    public ComboBox<String> monthComboBox;
    public ComboBox<String> contactComboBox;
    public ComboBox<String> countryComboBox;

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void contactSelected(ActionEvent actionEvent) throws SQLException {
        try {
            ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();

            String contactSelected = contactComboBox.getSelectionModel().getSelectedItem();
            int contactSelectedId = Contacts.getContactId(contactSelected);
            ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
            for (Appointment appts : allAppointments) {
                int contactId = appts.getContactId();
                if (contactId == contactSelectedId) {
                    contactAppointments.add(appts);
                }
            }
            byContactTable.setItems(contactAppointments);
            contactApptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
            contactCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactContactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            contactDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
            contactLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
            contactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            contactStartColumn.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
            contactEndColumn.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        } catch (SQLException e) {
            System.out.println("error getting appointment info");
        }
    }

    public void typeSelected(ActionEvent actionEvent) throws SQLException {
        try {
            ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();
            ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
            for (Appointment appointment : allAppointments) {
                String apptType = appointment.getApptType();
                if (!appointmentTypes.contains(apptType)) {
                    appointmentTypes.add(apptType);
                }


                int typeCounter = 0;
                String typeSelected = typeComboBox.getSelectionModel().getSelectedItem();

                for (Appointment appointments : allAppointments) {
                    String type = appointments.getApptType();
                    if (type.equals(typeSelected)) {
                        typeCounter++;
                    }
                    typeCount.setText("Count: " + typeCounter);
                }
            }
        } catch (SQLException e) {
               System.out.println("Error getting type");
            }
    }

    public void countrySelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();
        ObservableList<String> allCountryNames = Countries.getAllCountryNames();
        String countrySelected = countryComboBox.getSelectionModel().getSelectedItem();
        int countryCounter = 0;

        for (Appointment appt : allAppointments) {
            String location = appt.getApptLocation();
            String[] locationSplit = location.split("-");
            String countryName = locationSplit[0];
            if (countryName.equals(countrySelected)) {
                countryCounter++;
            }
            countryCount.setText("Count: " + countryCounter);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> allMonths = FXCollections.observableArrayList();
        String[] months = new DateFormatSymbols().getMonths();

        allMonths.addAll(Arrays.asList(months).subList(0, 13));
        monthComboBox.setItems(allMonths);
        monthComboBox.setValue(months[0]);

        try {
            ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();
            ObservableList<Appointment> monthAppointment = FXCollections.observableArrayList();
            String monthSelected = monthComboBox.getSelectionModel().getSelectedItem().toUpperCase(Locale.ROOT);
            int monthCounter = 0;
            for (Appointment appointment : allAppointments) {
                LocalDateTime appointmentStart = appointment.getApptStart();
                String nameOfMonth = String.valueOf(appointmentStart.getMonth());

                if (nameOfMonth.equals(monthSelected)) {
                    monthCounter++;
                }
                monthCount.setText("Count: " + monthCounter);
            }
            ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
            for (Appointment appointment : allAppointments) {
                String apptType = appointment.getApptType();
                if (!appointmentTypes.contains(apptType)) {
                    appointmentTypes.add(apptType);
                }
                typeComboBox.setItems(appointmentTypes);
                typeComboBox.setValue(appointmentTypes.get(0));

                int typeCounter = 0;
                String typeSelected = typeComboBox.getSelectionModel().getSelectedItem();

                for (Appointment appointments : allAppointments) {
                    String type = appointments.getApptType();
                    if (type.equals(typeSelected)) {
                        typeCounter++;
                    }
                    typeCount.setText("Count: " + typeCounter);
                }
            }
            ObservableList<String> allCountryNames = Countries.getAllCountryNames();
            countryComboBox.setItems(allCountryNames);
            countryComboBox.setValue(allCountryNames.get(0));
            String countrySelected = countryComboBox.getSelectionModel().getSelectedItem();
            int countryCounter = 0;

            for (Appointment appt : allAppointments) {
                String location = appt.getApptLocation();
                String[] locationSplit = location.split("-");
                String countryName = locationSplit[0];
                if (countryName.equals(countrySelected)) {
                    countryCounter++;
                }
                countryCount.setText("Count: " + countryCounter);
            }

            ObservableList<Contact> contact = Contacts.allContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();

            for (Contact contacts : contact) {
                String contactName = contacts.getContactName();
                contactNames.add(contactName);
            }
            contactComboBox.setItems(contactNames);
            contactComboBox.setValue(contactNames.get(0));
            String contactSelected = contactComboBox.getSelectionModel().getSelectedItem();
            int contactSelectedId = Contacts.getContactId(contactSelected);
            ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
            for (Appointment appts : allAppointments) {
                int contactId = appts.getContactId();
                if (contactId == contactSelectedId) {
                    contactAppointments.add(appts);
                }
            }
            byContactTable.setItems(contactAppointments);
            contactApptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
            contactCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactContactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            contactDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
            contactLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
            contactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            contactStartColumn.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
            contactEndColumn.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public void monthSelected(ActionEvent actionEvent) throws SQLException {
        try {
            ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();
            ObservableList<Appointment> monthAppointment = FXCollections.observableArrayList();
            String monthSelected = monthComboBox.getSelectionModel().getSelectedItem().toUpperCase(Locale.ROOT);
            int monthCounter = 0;
            for (Appointment appointment : allAppointments) {
                LocalDateTime appointmentStart = appointment.getApptStart();
                String nameOfMonth = String.valueOf(appointmentStart.getMonth());

                if (nameOfMonth.equals(monthSelected)) {
                    monthCounter++;
                }
                monthCount.setText("Count: " + monthCounter);
            }
            ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
            for (Appointment appointment : allAppointments) {
                String apptType = appointment.getApptType();
                if (!appointmentTypes.contains(apptType)) {
                    appointmentTypes.add(apptType);
                }
                typeComboBox.setItems(appointmentTypes);
                typeComboBox.setValue(appointmentTypes.get(0));

                int typeCounter = 0;
                String typeSelected = typeComboBox.getSelectionModel().getSelectedItem();

                for (Appointment appointments : allAppointments) {
                    String type = appointments.getApptType();
                    if (type.equals(typeSelected)) {
                        typeCounter++;
                    }
                    typeCount.setText("Count: " + typeCounter);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting month data");
        }
    }
}
