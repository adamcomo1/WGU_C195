package Models;

import DAO.Appointments;
import DAO.Customers;
import javafx.collections.ObservableList;

import java.sql.SQLException;
/**
 * Class model for Customer class.
 */
public class Customer {
    /**
     * Holds the int value of the Customer's customer ID.
     */
    private int customerId;
    /**
     * Holds the String value of the Customer's name.
     */
    private String customerName;
    /**
     * Holds the String value of the Customer's address.
     */
    private String address;
    /**
     * Holds the String value of the Customer's postal code.
     */
    private String postalCode;
    /**
     * Holds the String value of the Customer's phone number.
     */
    private String phoneNumber;
    /**
     * Holds the int value of the Customer's division Id.
     */
    private int divisionId;
    /**
     * Holds the String value of the Customer's divison name.
     */
    private String divisionName;

    /**
     * Getter for returning customer ID.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for customer ID.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for customer name.
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for customer name.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for customer address.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter for customer address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for customer postal code.
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for customer postal code.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for customer phone number.
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for customer phone number.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for Customer's division ID.
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter for Customer's division ID.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter for customer division name.
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Setter for customer division name
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Method for generating an new unique customer Id.
     * @return new unique customer Id.
     * @throws SQLException
     */
    public static int newCustomerId() throws SQLException {
    ObservableList<Customer> allCustomers = Customers.getAllCustomers();
    int max = 0;
        for (Customer customer : allCustomers) {
        int customerId = customer.getCustomerId();
        if (customerId > max) {
            max = customerId;
        }
    }
        return ++max;
}

    /**
     * Constructor for customer class.
     * @param customerId
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @param divisionName
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId, String divisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }
}
