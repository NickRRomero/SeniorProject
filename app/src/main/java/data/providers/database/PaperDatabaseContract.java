package data.providers.database;

import android.provider.BaseColumns;

/**
 * Created by nickromero on 3/1/17.
 */

public class PaperDatabaseContract {

    private PaperDatabaseContract() {}

    public static class PaperEntry implements BaseColumns {
        public static final String TABLE_NAME = "papers";
        public static final String PRIMARY_KEY = "primary_key";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHORS = "authors";
        public static final String COLUMN_NAME_ABSTRACT = "abstract";
        public static final String COLUMN_NAME_ISSN = "issn";
        public static final String COLUMN_NAME_ISBN = "isnbn";
        public static final String COLUMN_NAME_MD_URL = "md_url";
        public static final String COLUMN_NAME_PDF_URL = "pdf_url";
        public static final String COLUMN_NAME_PAPER_TYPE = "type";

    }
}
