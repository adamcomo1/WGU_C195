package DAO;

import Models.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisions {

    public static ObservableList<FirstLevelDivision> getAllFirstLevel() throws SQLException {
        ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = FXCollections.observableArrayList();
        String query = "SELECT Division_ID, Division, Country_ID from first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionId, division, countryId);
            firstLevelDivisionObservableList.add(firstLevelDivision);
        }
        return firstLevelDivisionObservableList;
    }

}
