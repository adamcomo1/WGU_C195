package Models;
/**
 * Class model for Country class.
 */
public class Country {
    /**
     * Constructor for country class.
     * @param countryId
     * @param countryName
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Getter to return country ID.
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Getter to return country name.
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * country ID.
     */
    private int countryId;
    /**
     * Country name.
     */
    private String countryName;

}
