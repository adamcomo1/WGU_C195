package Models;
/**
 * Class model for Contact class.
 */
public class Contact {
    /**
     * Holds the int value of the Contact's contactId.
     */
    private int contactId;
    /**
     * Holds the String value of the Contact's name.
     */
    private String contactName;
    /**
     * Holds the String value of the contact's email.
     */
    private String contactEmail;

    public int getContactId() {
        return contactId;
    }

    /**
     * Getter method used to get the selected contact's name.
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Constructor for the Contact class.
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
}
