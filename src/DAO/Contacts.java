package DAO;

import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for handling SQL statements with the Contacts table.
 */
public class Contacts {

    public static ObservableList<Contact> allContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String query = "SELECT * FROM contacts;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(contactId, contactName, email);
            allContacts.add(contact);
        }
        return allContacts;
    }

    /**
     * SQL query used to get a contacts ID using their Name.
     * @param contact
     * @return contact ID
     * @throws SQLException
     */
    public static int getContactId(String contact) throws SQLException {
        String query = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contact + "';";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        int contactId = 0;
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /**
     * An SQL query used to get a contacts Name using their ID.
     * @param contactId
     * @return
     * @throws SQLException
     */
    public static String getContactName(int contactId) throws SQLException {
        String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID = '" + contactId + "';";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        String contactName = "";

        while (rs.next()) {
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }
}
