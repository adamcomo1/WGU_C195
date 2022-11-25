package DAO;

import Models.FirstLevelDivision;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used to handle SQL statements for the firstleveldivsion table.
 */
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

    /**
     * SQL query used to get all the divisions associated with US.
     * @return observable list of all divisions associated with the US.
     * @throws SQLException
     */
    public static ObservableList<String> getUSDivision() throws SQLException {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        String query = "SELECT Division FROM first_level_divisions WHERE Country_ID = '1'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String division = rs.getString("Division");
            divisionNames.add(division);
        }
        return divisionNames;
    }
    /**
     * SQL query used to get all the divisions associated with UK.
     * @return observable list of all divisions associated with the UK.
     * @throws SQLException
     */
    public static ObservableList<String> getUKDivision() throws SQLException {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        String query = "SELECT Division FROM first_level_divisions WHERE Country_ID = '2'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String division = rs.getString("Division");
            divisionNames.add(division);
        }
        return divisionNames;
    }
    /**
     * SQL query used to get all the divisions associated with Canada.
     * @return observable list of all divisions associated with the Canada.
     * @throws SQLException
     */
    public static ObservableList<String> getCanadaDivision() throws SQLException {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        String query = "SELECT Division FROM first_level_divisions WHERE Country_ID = '3'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String division = rs.getString("Division");
            divisionNames.add(division);
        }
        return divisionNames;
    }

    /**
     * SQL query used to return the division ID of the selected division.
     * @param divisionName
     * @return selected division ID.
     * @throws SQLException
     */
    public static int getDivisionId(String divisionName) throws SQLException {
        int divisionId = 0;
        String query = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionName + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }



}
