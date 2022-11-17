package DAO;

import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Countries {

    public Countries(int countryId, String countryName) {
    }

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


}
