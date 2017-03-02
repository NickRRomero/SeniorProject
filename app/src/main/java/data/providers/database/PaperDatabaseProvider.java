package data.providers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nickromero on 3/1/17.
 */

public class PaperDatabaseProvider extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Papers.db";

    private SQLiteDatabase mDb;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PaperDatabaseContract.PaperEntry.TABLE_NAME + " (" +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_TITLE + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_AUTHORS + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_ABSTRACT + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_ISSN + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_ISBN + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_MD_URL + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_PDF_URL + " TEXT," +
                    PaperDatabaseContract.PaperEntry.COLUMN_NAME_PAPER_TYPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE " + PaperDatabaseContract.PaperEntry.TABLE_NAME;

    public PaperDatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDb = db;
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dropTable() {
        mDb.execSQL("DROP TABLE papers");
        mDb.execSQL(SQL_CREATE_ENTRIES);
    }
}
