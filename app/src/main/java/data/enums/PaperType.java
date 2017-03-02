package data.enums;

/**
 * Created by nickromero on 2/5/17.
 */

public enum PaperType {
    SAVED("Saved"), SUBSCRIBED("Subscribed"), SUGGESTED("Suggested");

    private final String type;

    PaperType(String s) {
        type = s;
    }

    public String getType() {
        return type;
    }
}
