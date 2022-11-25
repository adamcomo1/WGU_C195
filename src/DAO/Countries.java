package DAO;

import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for handling SQL queries on the Countries table.
 */
public class Countries {

    public Countries(int countryId, String countryName) {
    }

    /**
     * SQL query used to get all the countries from the country table.
     * @return observable list of country class.
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countriesObservableList = FXCollections.observableArrayList();
        String query = "SELECT Country_ID, Country from countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            countriesObservableList.add(country);
        }
        return countriesObservableList;
    }

    /**
     * SQL query used to get all the country names from the country table.
     * @return observable list of all country names.
     * @throws SQLException
     */
    public static ObservableList<String> getAllCountryNames() throws SQLException {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        String query = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String countryName = rs.getString("Country");
            countryNames.add(countryName);
        }
        return countryNames;
    }


}
