package DAO;

import Models.User;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {

    public static ObservableList<User> getAllUsers() throws SQLException {

        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        String query = "SELECT User_ID, User_Name, Password FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userId, username, password);
            userObservableList.add(user);
        }
        return userObservableList;
    }

    public static boolean logInValidation(String username, String password) throws SQLException {
        try {

        String query = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        rs.next();

        if (rs.getString("User_Name").equals(username)) {
            if (rs.getString("Password").equals(password)) {
                return true;
            }
        }
        } catch (SQLException e) {
            return false;
        }
        return false;
        }
    }

