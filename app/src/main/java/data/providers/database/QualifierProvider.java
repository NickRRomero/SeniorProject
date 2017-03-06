package data.providers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nickromero on 2/9/17.
 */

public class QualifierProvider extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Qualifiers.db";

    private SQLiteDatabase mDb;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QualifierContract.QualifierEntry.TABLE_NAME + " (" +
                    QualifierContract.QualifierEntry.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    QualifierContract.QualifierEntry.COLUMN_NAME_TYPE + " TEXT," +
                    QualifierContract.QualifierEntry.COLUMN_NAME_TERM + " TEXT," +
                    QualifierContract.QualifierEntry.COLUMN_NAME_FIELD + " TEXT," +
                    QualifierContract.QualifierEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    QualifierContract.QualifierEntry.COLUMN_NAME_COLOR + " SMALLINT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE " + QualifierContract.QualifierEntry.TABLE_NAME;

    public QualifierProvider(Context context) {
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
        mDb.execSQL("DROP TABLE qualifiers");
        mDb.execSQL(SQL_CREATE_ENTRIES);
    }
}
