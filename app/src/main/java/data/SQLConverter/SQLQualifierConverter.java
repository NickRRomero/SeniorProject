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
import static data.providers.database.QualifierContract.QualifierEntry.PRIMARY_KEY;
import static data.providers.database.QualifierContract.QualifierEntry.TABLE_NAME;

/**
 * Created by nickromero on 2/9/17.
 */

public class SQLQualifierConverter {


    private static SQLQualifierConverter sqlQualifierConverter;

    private static QualifierProvider mDbHelper;

    private static SQLiteDatabase mDBWriter;
    private static SQLiteDatabase mDBReader;



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

        //contentValues.put(PRIMARY_KEY, qualifier.getPrimaryKey());
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
        int color, col, primary_key;


        while (cursor.moveToNext()) {
            col = 0;
            primary_key = cursor.getInt(col++);
            type = cursor.getString(col++);
            term = cursor.getString(col++);
            field = cursor.getString(col++);
            description = cursor.getString(col++);
            color = cursor.getInt(col++);


            if (type.equals("Subscription"))
                newQualifier = new Subscription(field, term, color, primary_key);
            else
                newQualifier = new Filter(field, term, color, primary_key);
            newQualifier.setDescription(description);
            qualifiers.add(newQualifier);
        }
        cursor.close();


        return qualifiers;
    }

    public void deleteQualifierFromDatabase(Qualifier qualifier) {
        mDBWriter.delete(TABLE_NAME,
                "term=?", new String[] {qualifier.getSearchTerm()});
    }


    public Qualifier getQualifierFromDatabase(int qualifer_key) {
        String selection = "SELECT * FROM " + TABLE_NAME + " WHERE primary_key = " + qualifer_key;
        Cursor cursor = mDBWriter.rawQuery(selection, null);
        String type, term , field, description;
        Qualifier newQualifier = null;
        int color, col = 0;

        while (cursor.moveToNext()) {

            qualifer_key = cursor.getInt(col++);
            type = cursor.getString(col++);
            term = cursor.getString(col++);
            field = cursor.getString(col++);
            description = cursor.getString(col++);
            color = cursor.getInt(col++);
            if (type.equals("Subscription"))
                newQualifier = new Subscription(field, term, color, qualifer_key);
            else
                newQualifier = new Filter(field, term, color, qualifer_key);
            newQualifier.setDescription(description);
        }
        return newQualifier;


    }
}
