package Models;
/**
 * Class model for User class.
 */
public class User {

    private int userId;
    private String username;
    private String password;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for user ID.
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for the username.
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the password.
     * @return
     */
    public String getPassword() {
        return password;
    }

}
