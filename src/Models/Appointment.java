package Models;

import DAO.Appointments;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class model for Appointment class.
 */
public class Appointment {
    /**
     * holds the Integer value of the appointment ID
     */
    private int appointmentId;
    /**
     * Holds the String value of the Appointment Title.
     */
    private String apptTitle;
    /**
     * Hold the string value of the appointment description.
     */
    private String apptDescription;
    /**
     * Holds the string value of the appointment location.
     */
    private String apptLocation;
    /**
     * Holds the string value of the appointment type.
     */
    private String apptType;
    /**
     * Holds the LocalDateTime value of the appointment start.
     */
    private LocalDateTime apptStart;
    /**
     * Holds the LocalDateTime value of the appointment end.
     */
    private LocalDateTime apptEnd;
    /**
     * Holds the int value of the appointment's customer Id.
     */
    private int customerId;
    /**
     * Holds the int value of the appointment's user Id.
     */
    private int userId;
    /**
     * Holds the int value of the appointment's contact Id.
     */
    private int contactId;


    /**
     * Method used to generate a new unique appointment Id.
     * @return a new unique appointment Id.
     * @throws SQLException
     */
    public static int getNewAppointmentId() throws SQLException {
        ObservableList<Appointment> allAppointments = Appointments.getAllAppointments();
        int max = 0;
        for (Appointment appointment : allAppointments) {
            int apptId = appointment.getAppointmentId();
            if (apptId > max) {
                max = apptId;
            }
        }
        return ++max;
    }

    /**
     * Method used to return the selected appointments ID.
     * @return appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Method used to return the selected appointments title.
     * @return apptTitle
     */
    public String getApptTitle() {
        return apptTitle;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * Method used to get the selected appointments description.
     * @return apptDescription
     */
    public String getApptDescription() {
        return apptDescription;
    }

    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /**
     * Method used to get the selected appointments location.
     * @return apptLocation.
     */
    public String getApptLocation() {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * Method used to get the selected appointments type.
     * @return apptType
     */
    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * Method used to get the selected appointments start time and date.
     * @return apptStart
     */
    public LocalDateTime getApptStart() {
        return apptStart;
    }

    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    /**
     * Getter method used to get the selected appointments end time and date.
     * @return apptEnd
     */
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }

    /**
     * Getter method used to get the selected appointments customer Id.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method used to set the selected appointments customer Id.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method used to get the selected appointments user Id.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter method used to return the selected appointments contact Id.
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Constructor method for the Appointment class.
     * @param appointmentId
     * @param apptTitle
     * @param apptDescription
     * @param apptLocation
     * @param apptType
     * @param apptStart
     * @param apptEnd
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointment(int appointmentId, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
}
