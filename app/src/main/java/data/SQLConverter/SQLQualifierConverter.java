package data.SQLConverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import data.models.Filter;
import data.models.Qualifier;
import data.providers.database.QualifierContract;
import data.providers.database.QualifierProvider;
import data.models.Subscription;
import retrofit2.http.Query;

import static data.providers.database.QualifierContract.QualifierEntry.COLUMN_NAME_COLOR;
import static data.providers.database.QualifierContract.QualifierEntry.COLUMN_NAME_DESCRIPTION;
import static data.providers.database.QualifierContract.QualifierEntry.COLUMN_NAME_FIELD;
import static data.providers.database.QualifierContract.QualifierEntry.COLUMN_NAME_TERM;
import static data.providers.database.QualifierContract.QualifierEntry.COLUMN_NAME_TYPE;
import static data.providers.database.QualifierContract.QualifierEntry.TABLE_NAME;

/**
 * Created by nickromero on 2/9/17.
 */

public class SQLQualifierConverter {

    public static final String WHERE = " WHERE ";
    public static final String DELETE_FROM = "DELETE FROM ";
    public static final String AND = " AND ";
    public static final String EQUALS = " = ";
    public static SQLQualifierConverter sqlQualifierConverter;

    public static QualifierProvider mDbHelper;

    static SQLiteDatabase mDBWriter;
    static SQLiteDatabase mDBReader;

    private final String[] projection = {
            COLUMN_NAME_TYPE,
            COLUMN_NAME_TERM,
            QualifierContract.QualifierEntry.COLUMN_NAME_FIELD,
            COLUMN_NAME_DESCRIPTION,
            COLUMN_NAME_COLOR
    };

    public void removeTable() {
        mDbHelper.dropTable();
    }


    public static  SQLQualifierConverter getInstance(Context context) {
        if (sqlQualifierConverter == null) {
            sqlQualifierConverter = new SQLQualifierConverter();
            mDbHelper = new QualifierProvider(context);
            mDBWriter = mDbHelper.getWritableDatabase();
            mDBReader = mDbHelper.getReadableDatabase();
            return sqlQualifierConverter;
        } else
            return sqlQualifierConverter;
    }

    public void addQualiferToDatabase(Qualifier qualifier) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_TYPE, qualifier.getType());
        contentValues.put(COLUMN_NAME_TERM, qualifier.getSearchTerm());
        contentValues.put(COLUMN_NAME_FIELD, qualifier.getCategory());
        contentValues.put(COLUMN_NAME_DESCRIPTION, qualifier.getDescription());
        contentValues.put(COLUMN_NAME_COLOR, qualifier.getColor());

        mDBWriter.insert(TABLE_NAME, null, contentValues);


    }

    public ArrayList<Qualifier> getQualifiersFromDatabase() {
        String selection =  "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = mDBWriter.rawQuery(selection, null);
        Qualifier newQualifier;
        ArrayList<Qualifier> qualifiers = new ArrayList<>();
        String type, term, field, description;
        int color;


        while (cursor.moveToNext()) {
            long itemid = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_FIELD));
            type = cursor.getString(0);
            term = cursor.getString(1);
            field = cursor.getString(2);
            description = cursor.getString(3);
            color = cursor.getInt(4);


            if (type.equals("Subscription"))
                newQualifier = new Subscription(field, term, color);
            else
                newQualifier = new Filter(field, term, color);
            newQualifier.setDescription(description);
            qualifiers.add(newQualifier);
        }
        cursor.close();


        return qualifiers;
    }

    public void deleteQualifierFromDatabase(Qualifier qualifier) {
        String selection = DELETE_FROM + TABLE_NAME + WHERE
                + COLUMN_NAME_TYPE + EQUALS + qualifier.getType() + AND
                + COLUMN_NAME_FIELD + EQUALS + qualifier.getCategory() + AND
                + COLUMN_NAME_TERM + EQUALS + qualifier.getSearchTerm();
        mDBWriter.delete(TABLE_NAME,
                "term=?", new String[] {qualifier.getSearchTerm()});
    }


}
