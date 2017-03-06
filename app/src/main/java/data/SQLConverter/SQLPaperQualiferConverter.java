package data.SQLConverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import data.models.Paper;
import data.models.Qualifier;
import data.providers.database.PaperQualifierRelationalProvider;

import static data.providers.database.PaperQualifierRelationalContract.RelationalEntry.COLUMN_NAME_PAPER_ID;
import static data.providers.database.PaperQualifierRelationalContract.RelationalEntry.COLUMN_NAME_QUALIFIER_ID;
import static data.providers.database.PaperQualifierRelationalContract.RelationalEntry.TABLE_NAME;

/**
 * Created by nickromero on 3/5/17.
 */

public class SQLPaperQualiferConverter {

    private static  SQLPaperQualiferConverter sqlPaperQualiferConverter;

    private static PaperQualifierRelationalProvider mDbHelper;

    private static SQLiteDatabase mDBWriter;
    private static SQLiteDatabase mDBReader;
    private Context context;

    public static SQLPaperQualiferConverter getInstance(Context context) {
        if (sqlPaperQualiferConverter == null) {
            sqlPaperQualiferConverter = new SQLPaperQualiferConverter();
            mDbHelper = new PaperQualifierRelationalProvider(context);

            mDBWriter = mDbHelper.getWritableDatabase();
            mDBReader = mDbHelper.getReadableDatabase();
            return sqlPaperQualiferConverter;
        }
        else
            return sqlPaperQualiferConverter;
    }

    public void setQualifiersForPaper(Paper paper, Qualifier qualifier) {
        int primary_key_paper = paper.getPrimaryKey();
        int primary_key_qualifier = qualifier.getPrimaryKey();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_PAPER_ID, primary_key_paper);
        contentValues.put(COLUMN_NAME_QUALIFIER_ID, primary_key_qualifier);

        mDBWriter.insertOrThrow(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Qualifier> getPapersQualifiers(Paper paper) {
        int primary_key = paper.getPrimaryKey();

        String selection = "SELECT * FROM " + TABLE_NAME + " WHERE paper_id = " + primary_key;
        Cursor cursor = mDBWriter.rawQuery(selection, null);
        int qualifer_key;

        ArrayList<Qualifier> qualifiers =  new ArrayList<>();
        SQLQualifierConverter sqlQualifierConverter = SQLQualifierConverter.getInstance(context);



        while  (cursor.moveToNext()) {
            qualifer_key = cursor.getInt(2);//Have primary key of qualifier associated with this paper
            qualifiers.add(sqlQualifierConverter.getQualifierFromDatabase(qualifer_key));
        }

        cursor.close();
        return qualifiers;

    }
}
