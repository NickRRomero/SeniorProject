package data.models;

/**
 * Created by nickromero on 1/19/17.
 */

public final class Filter extends Qualifier {

    private final String QUALIFIER_TYPE = "Filter";

    /**
     * Constructor for a qualifier
     *
     * @param category     used to specify a high level description of this qualifier
     * @param searchTerm
     * @param colorMapping
     */
    public Filter(String category, String searchTerm, int colorMapping) {
        super(category, searchTerm, colorMapping, "Filter");
    }

    public Filter(String category, String searchTerm, int colorMapping, int primaryKey) {
        super(category, searchTerm, colorMapping, "Filter", primaryKey);
    }

    /**
     * Getter used to retrieve the type of qualifier
     * @return Filter type
     */
    public String getQUALIFIER_TYPE() {
        return QUALIFIER_TYPE;
    }
}
