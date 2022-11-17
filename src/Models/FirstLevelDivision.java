package Models;

public class FirstLevelDivision {
    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryId() {
        return countryId;
    }

    /**
     * Division ID.
     */
    private int divisionId;
    /**
     * Division's name.
     */
    private String division;
    /**
     * Country's ID.
     */
    private int countryId;

    /**
     * Constructor for FirstLevelDivision class.
     * @param divisionId
     * @param division
     * @param countryId
     */
    public FirstLevelDivision(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }
}
