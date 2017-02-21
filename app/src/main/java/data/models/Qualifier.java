package data.models;

/**
 * Created by nickromero on 1/19/17.
 */

/**
 * A qualifier is an abstract representation of either a Subscription or Filter
 *
 */
public abstract class Qualifier {


    /**
     * Category taken from the IEEE xplore Gateway
     */
    private final String IEEE_SEARCH_CATEGORY;

    /**
     * Custom description for this qualifer. Optional attribute
     */
    private String sCustomDescription;


    /**
     * Term searched for in an underlying IEEE category
     */
    private String sSearchTerm;

    /**
     * Mapping of a color to colors.xml. Used to represent this filter when it's
     * created
     */
    private final int mColor;

    /**
     * Constructor for a qualifier
     * @param category used to specify a high level description of this qualifier
     * @param colorMapping
     */

    private final String mType;

    public Qualifier(String category, String searchTerm, int colorMapping, String type) {
        IEEE_SEARCH_CATEGORY = category;
        sSearchTerm = searchTerm;
        mColor = colorMapping;

        mType = type;

    }

    /**
     * Used to set a custom description for this qualifier
     * @param customDescription string for the custom description
     */
    public void setDescription(String customDescription) {
        sCustomDescription = customDescription;
    }

    /**
     * Setter for a qualifier's search term
     * @param searchTerm
     */
    public void setSearchTerm(String searchTerm) {
        sSearchTerm = searchTerm;
    }

    /**
     * Get the associated category of this qualifier
     * @return string containing the category of this qualifier
     */
    public String getCategory() {
        return IEEE_SEARCH_CATEGORY;
    }

    /**
     * Get the custom description associated with this qualifier
     * @return string representing the custom description of this qualifier
     */
    public String getDescription() {
        return sCustomDescription;
    }

    /**
     * Getter for the searched term associated with a qualifer
     * @return
     */
    public String getSearchTerm() {
        return sSearchTerm;
    }

    /**
     * Getter for the qualifier's color. Used to represent a qualifier visually
     * within a card.
     * @return int containing a color
     */
    public int getColor() {
        return mColor;
    }


    public String getType() {
        return mType;
    }
}
