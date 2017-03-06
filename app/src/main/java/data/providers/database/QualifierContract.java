package data.providers.database;

import android.provider.BaseColumns;

/**
 * Created by nickromero on 2/9/17.
 */

public final class QualifierContract {

    private QualifierContract() {}

    public static class QualifierEntry implements BaseColumns {
        public static final String TABLE_NAME = "qualifiers";
        public static final String PRIMARY_KEY = "primary_key";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TERM = "term";
        public static final String COLUMN_NAME_FIELD = "field";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

    }
}
