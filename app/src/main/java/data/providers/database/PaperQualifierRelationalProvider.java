package data.providers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nickromero on 3/5/17.
 */

public class PaperQualifierRelationalProvider extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Papers_Qualifiers.db";

    private SQLiteDatabase mDb;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PaperQualifierRelationalContract.RelationalEntry.TABLE_NAME + "( " +
                    PaperQualifierRelationalContract.RelationalEntry.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PaperQualifierRelationalContract.RelationalEntry.COLUMN_NAME_PAPER_ID + " INTEGER," +
                    PaperQualifierRelationalContract.RelationalEntry.COLUMN_NAME_QUALIFIER_ID + " INTEGER)";

    public PaperQualifierRelationalProvider(Context context) {
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
}
