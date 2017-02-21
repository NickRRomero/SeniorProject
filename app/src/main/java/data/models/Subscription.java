package data.models;

/**
 * Created by nickromero on 1/19/17.
 */

public final class Subscription extends Qualifier {

    private final String QUALIFIER_TYPE = "Subscription";

    /**
     * Constructor for a qualifier
     *
     * @param category     used to specify a high level description of this qualifier
     * @param searchTerm
     * @param colorMapping
     */
    public Subscription(String category, String searchTerm, int colorMapping) {
        super(category, searchTerm, colorMapping, "Subscription");
    }


    /**
     * Getter used to retrieve the type of qualifier
     * @return Subscription type
     */
    public String getQUALIFIER_TYPE() {
        return QUALIFIER_TYPE;
    }

}
