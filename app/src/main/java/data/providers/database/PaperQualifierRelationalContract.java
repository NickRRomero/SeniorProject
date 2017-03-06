package data.providers.database;

import android.provider.BaseColumns;

/**
 * Created by nickromero on 3/5/17.
 */

public class PaperQualifierRelationalContract {

    private PaperQualifierRelationalContract() {}

    public static class RelationalEntry implements BaseColumns {
        public static final String TABLE_NAME = "paper_qualifiers";
        public static final String PRIMARY_KEY = "primary_key";
        public static final String COLUMN_NAME_PAPER_ID = "paper_id";
        public static final String COLUMN_NAME_QUALIFIER_ID = "qualifier_id";
    }
}
