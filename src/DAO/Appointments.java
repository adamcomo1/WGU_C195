package DAO;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Appointments {

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {

        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(apptId, title, description, location, type, start, end, customerId, userId, contactId);
            appointmentObservableList.add(appointment);
        }
        return appointmentObservableList;
    }

    public static void deleteAppointment(int apptId) throws SQLException {
        try {
            String update = "DELETE FROM appointments WHERE Appointment_ID = " + apptId;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(update);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error with delete execution");
        }
    }

   public static void addAppointment (int apptId, String title, String desc, String location, String type, String start,
                                      String end, int customerId, int userId, int contactId) throws SQLException {
        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, "
       + "Customer_ID, User_ID, Contact_ID) VALUES ('" + apptId + "', '" + title + "', '" + desc + "', '" + location +
                "', '" + type + "', '" + start + "', '" + end + "', '" + customerId + "', '" + userId + "', '" +
                contactId + "');";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ps.executeUpdate();
   }

   public static void updateAppointment (int apptId, String title, String desc, String location, String type, String start,
                                         String end, int customerId, int userId, int contactId) throws SQLException {
        String query = "UPDATE appointments SET Appointment_ID = '" + apptId + "', Title = '" + title +
                "', Description = '" + desc + "', Location = '" + location + "', Type = '" + type + "', Start = '" +
                start + "', End = '" + end + "', Customer_ID = '" + customerId + "', User_ID = '"
                + userId + "', Contact_ID = '" + contactId + "' WHERE Appointment_ID = '" + apptId + "';";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ps.executeUpdate();
   }

   public static boolean checkForAppointment(int customerId) throws SQLException {
        ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
        String query = "SELECT Customer_ID FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            if (customerID == customerId) {
                return true;
            }
       }
        return false;
   }

}
