package DAO;

import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customers {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                "customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN " +
                "first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId,
                    divisionName);
            customerObservableList.add(customer);
        }
        return customerObservableList;
    }

    public static void deleteCustomer(int customerID) throws SQLException {
        try {
            String update = "DELETE FROM customers WHERE Customer_ID = " + customerID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(update);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error with delete execution");
        }
        }

    public static void saveCustomer(int customerId, String name, String address, String postalCode,
                                    String phone, int divisionId) throws SQLException {
        String query = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                "VALUES (" + customerId + ", '" + name + "', '" + address + "', '" +
                postalCode + "', '" + phone + "', " + divisionId + ");";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ps.executeUpdate();
    }
}
