package Controllers;

import DAO.Appointments;
import DAO.Contacts;
import DAO.Countries;
import DAO.FirstLevelDivisions;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAO.Users.currentUser;
import static Utilities.timeUtility.convertToUTC;
import static Utilities.timeUtility.utcToEasternTime;
import static java.lang.String.valueOf;

public class UpdateAppointmentController implements Initializable {
    public TextField customerIdField;
    public TextField userIdField;
    public TextField titleField;
    public TextField descriptionField;
    public ComboBox<String> countryComboBox;
    public ComboBox<String> stateComboBox;
    public ComboBox<String> contactComboBox;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public TextField apptTypeTextBox;
    public ComboBox<String> endTimeCombo;
    public ComboBox<String> startTimeCombo;
    private Appointment appointmentSelected;

    LocalTime startTime = LocalTime.MIN;


    public void saveButtonPressed(ActionEvent actionEvent) throws SQLException, IOException {
        if (descriptionField.getText() == null || apptTypeTextBox.getText() == null ||
                countryComboBox.getSelectionModel().isEmpty() || stateComboBox.getSelectionModel().isEmpty() ||
                contactComboBox.getSelectionModel().isEmpty() || endTimeCombo.getSelectionModel().isEmpty() ||
                startTimeCombo.getSelectionModel().isEmpty() || endDatePicker.getValue() == null ||
                startDatePicker.getValue() == null || titleField.getText() == null) {
            alertCases(1);
        } else {
            int appointmentId = appointmentSelected.getAppointmentId();
            int customerId = Integer.parseInt(customerIdField.getText());
            int userId = currentUser;
            String title = titleField.getText();
            String description = descriptionField.getText();
            String appointmentType = apptTypeTextBox.getText();
            String country = countryComboBox.getSelectionModel().getSelectedItem();
            String state = stateComboBox.getSelectionModel().getSelectedItem();
            String location = country + "-" + state;
            String contact = contactComboBox.getSelectionModel().getSelectedItem();
            int contactId = Contacts.getContactId(contact);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(startTimeCombo.getValue(), timeFormat);
            LocalTime endTime = LocalTime.parse(endTimeCombo.getValue(), timeFormat);
            LocalDate startDate = startDatePicker.getValue();
            //String endTimeString = endTimeCombo.getSelectionModel().getSelectedItem();
            //String startTimeString = startTimeCombo.getSelectionModel().getSelectedItem();
            LocalDate endDate = endDatePicker.getValue();
            String startDateString = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDateString = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startUTC = convertToUTC(startDateString + " " + startTime + ":00");
            String endUTC = convertToUTC(endDateString + " " + endTime + ":00");

            LocalTime easternStart = utcToEasternTime(startUTC);
            LocalTime easternEnd = utcToEasternTime(endUTC);

            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDateString + " " + startTime + ":00", dateTimeFormatter);
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endDateString + " " + endTime + ":00", dateTimeFormatter);


            int appointmentDayEnd = endDate.getDayOfWeek().getValue();
            int appointmentDayStart = startDate.getDayOfWeek().getValue();
            int startWorkWeek = DayOfWeek.MONDAY.getValue();
            int endWorkWeek = DayOfWeek.FRIDAY.getValue();
            LocalTime businessStart = LocalTime.of(8,0,0);
            LocalTime businessEnd = LocalTime.of(22, 0, 0);

            boolean noErrors = true;
            if (appointmentDayStart > endWorkWeek || appointmentDayEnd > endWorkWeek) {
                alertCases(2);
                noErrors = false;
            }
            else if (easternStart.isBefore(businessStart) || easternStart.isAfter(businessEnd) || easternStart.equals(businessEnd)) {
                alertCases(3);
                noErrors = false;
            }
            else if (easternEnd.isBefore(businessStart) || easternEnd.isAfter(businessEnd)) {
                alertCases(4);
                noErrors = false;
            }
            else if (startLocalDateTime.isBefore(LocalDateTime.now()) || endLocalDateTime.isBefore(LocalDateTime.now())) {
                alertCases(5);
                noErrors = false;
            }
            else if (endLocalDateTime.isBefore(startLocalDateTime) || endLocalDateTime.isEqual(startLocalDateTime)) {
                alertCases(6);
                noErrors = false;
            }
            for (Appointment appointment: Appointments.getAllAppointments()) {

                LocalDateTime start = appointment.getApptStart();
                LocalDateTime end = appointment.getApptEnd();


                if (customerId == appointment.getCustomerId() && appointmentId != appointment.getAppointmentId() &&
                        startLocalDateTime.isBefore(start) && endLocalDateTime.isAfter(end)) {
                    alertCases(7);
                    noErrors = false;
                    return;
                }
                else if (customerId == appointment.getCustomerId() && appointmentId != appointment.getAppointmentId() &&
                        startLocalDateTime.isAfter(start) && endLocalDateTime.isBefore(end)) {
                    alertCases(7);
                    noErrors = false;
                    return;
                }

            }
            if(noErrors) {
                Appointments.updateAppointment(appointmentId, title, description, location, appointmentType, startUTC,
                        endUTC, customerId, userId, contactId);

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/MainScreenView.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentSelected = MainScreenController.getAppointmentToModify();
        customerIdField.setText(valueOf(appointmentSelected.getCustomerId()));
        userIdField.setText(valueOf(appointmentSelected.getUserId()));
        int appointmentId = appointmentSelected.getAppointmentId();
        titleField.setText(appointmentSelected.getApptTitle());
        descriptionField.setText(appointmentSelected.getApptDescription());
        apptTypeTextBox.setText(appointmentSelected.getApptType());

        try {
            ObservableList<Country> allCountry = Countries.getAllCountries();
            ObservableList<String> allCountryName = FXCollections.observableArrayList();
            // Lambda
            allCountry.forEach(country -> allCountryName.add(country.getCountryName()));
            countryComboBox.setItems(allCountryName);
            countryComboBox.setPromptText("Country");

        } catch (SQLException e) {
            System.out.println("Error loading countries");
        }
        String currentLocation = appointmentSelected.getApptLocation();
        String[] split = currentLocation.split("-");
        String currentCountry = split[0];
        String currentState = split[1];
        //System.out.println(currentCountry + "\n" + currentState);
        countryComboBox.setValue(currentCountry);
        String countrySelected = countryComboBox.getSelectionModel().getSelectedItem();
        //System.out.println(countrySelected);
        if (countrySelected.equals("U.S")) {
            stateComboBox.setPromptText("State");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getUSDivision();
                stateComboBox.setItems(divisionNames);

            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
        if (countrySelected.equals("UK")) {
            stateComboBox.setPromptText("Region");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getUKDivision();
                stateComboBox.setItems(divisionNames);
            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
        if (countrySelected.equals("Canada")) {
            stateComboBox.setPromptText("Province");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getCanadaDivision();
                stateComboBox.setItems(divisionNames);

            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
        stateComboBox.setValue(currentState);

        try {
            ObservableList<Contact> allContacts = Contacts.allContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            // Lambda
            allContacts.forEach(contact -> contactNames.add(contact.getContactName()));
            contactComboBox.setItems(contactNames);
            contactComboBox.setPromptText("Contact Name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String currentContactName = Contacts.getContactName(appointmentSelected.getContactId());
            contactComboBox.setValue(currentContactName);
        } catch (SQLException e) {
            System.out.println("error getting contact name");
        }
        String currentStart = valueOf(appointmentSelected.getApptStart());
        String[] startSplit = currentStart.split("T");
        String currentStartTime = startSplit[1];
        System.out.println(currentStartTime);

        String currentEnd = valueOf(appointmentSelected.getApptEnd());
        String[] endSplit = currentEnd.split("T");
        String currentEndTime = endSplit[1];
        System.out.println(currentEndTime);

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        for (int i = 0;  i < 48; i++) {
            appointmentTimes.add(String.valueOf(startTime));
            startTime = startTime.plusMinutes(30);
        }
        startTimeCombo.setItems(appointmentTimes);
        endTimeCombo.setItems(appointmentTimes);

        startTimeCombo.setValue(currentStartTime);
        endTimeCombo.setValue(currentEndTime);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentStartDate = LocalDate.parse(startSplit[0], dateFormat);
        startDatePicker.setValue(currentStartDate);

        LocalDate currentEndDate = LocalDate.parse(endSplit[0], dateFormat);
        endDatePicker.setValue(currentEndDate);
    }

    public void countrySelected(ActionEvent actionEvent) {
        String countrySelected = countryComboBox.getSelectionModel().getSelectedItem();
        //System.out.println(countrySelected);
        if (countrySelected.equals("U.S")) {
            stateComboBox.setPromptText("State");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getUSDivision();
                stateComboBox.setItems(divisionNames);

            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
        if (countrySelected.equals("UK")) {
            stateComboBox.setPromptText("Region");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getUKDivision();
                stateComboBox.setItems(divisionNames);
            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
        if (countrySelected.equals("Canada")) {
            stateComboBox.setPromptText("Province");
            try {
                ObservableList<String> divisionNames = FirstLevelDivisions.getCanadaDivision();
                stateComboBox.setItems(divisionNames);

            } catch (SQLException e) {
                System.out.println("Error getting first level division data");
            }
        }
    }


    private void alertCases(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("There are blank fields and/or invalid values.");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Appointment must be during a day of operation (Mon - Fri)");
                alert.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Appointment start must be within business hours (8am - 10pm EST");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Appointment end must be within business hours (8am - 10PM EST");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("New appointments cannot be in the past");
                alert.showAndWait();
            }
            case 6 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Appointment end must be before appointment start.");
                alert.showAndWait();
            }
            case 7 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Appointment overlaps with existing customer appointment");
                alert.showAndWait();
            }
        }
    }


}

