package data.SQLConverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.models.Paper;
import data.models.Qualifier;
import data.providers.database.PaperDatabaseProvider;
import data.providers.database.QualifierProvider;

import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_ABSTRACT;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_AUTHORS;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_ISBN;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_ISSN;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_MD_URL;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_PAPER_TYPE;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_PDF_URL;
import static data.providers.database.PaperDatabaseContract.PaperEntry.COLUMN_NAME_TITLE;
import static data.providers.database.PaperDatabaseContract.PaperEntry.PRIMARY_KEY;
import static data.providers.database.PaperDatabaseContract.PaperEntry.TABLE_NAME;

/**
 * Created by nickromero on 3/1/17.
 */

public class SQLPaperConverter {


    private static SQLPaperConverter sqlPaperConverter;

    private static PaperDatabaseProvider mDbHelper;

    private static SQLiteDatabase mDBWriter;
    private static SQLiteDatabase mDBReader;

    public static  SQLPaperConverter getInstance(Context context) {
        if (sqlPaperConverter == null) {
            sqlPaperConverter = new SQLPaperConverter();
            mDbHelper = new PaperDatabaseProvider(context);
            mDBWriter = mDbHelper.getWritableDatabase();
            mDBReader = mDbHelper.getReadableDatabase();
            return sqlPaperConverter;
        } else
            return sqlPaperConverter;
    }

    public void addPaperToDatabase(Paper paper) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_TITLE, paper.getTitle());
        contentValues.put(COLUMN_NAME_AUTHORS, paper.getAuthors().toString());
        contentValues.put(COLUMN_NAME_ABSTRACT, paper.getAbstract());
        contentValues.put(COLUMN_NAME_ISSN, paper.getISSN());
        contentValues.put(COLUMN_NAME_ISBN, paper.getISBN());
        contentValues.put(COLUMN_NAME_MD_URL, paper.getMDURL());
        contentValues.put(COLUMN_NAME_PDF_URL, paper.getURL());
        contentValues.put(COLUMN_NAME_PAPER_TYPE, paper.getType());

        mDBWriter.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Paper> getPapersFromDatabase() {
        String selection = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = mDBWriter.rawQuery(selection, null);
        Paper paper;
        ArrayList<Paper> papers = new ArrayList<>();
        String title, authors, pabstract, issn, isbn, mdurl, pdfurl, papertype, colors;
        int primary_key, col;
        String[] colorStrArr;
        int[] colorArr;
        List<String> authorsList;


        while (cursor.moveToNext()) {
            col = 0;
            primary_key = cursor.getInt(col++);

            title = cursor.getString(col++);
            authors = cursor.getString(col++);
            pabstract = cursor.getString(col++);
            issn = cursor.getString(col++);
            isbn = cursor.getString(col++);
            mdurl = cursor.getString(col++);
            pdfurl = cursor.getString(col++);
            papertype = cursor.getString(col);



            authorsList =  Arrays.asList(authors.split(","));



            paper = new Paper(title, authorsList, pabstract, issn
            , isbn, mdurl, pdfurl, papertype, primary_key);

            papers.add(paper);

        }
        cursor.close();

        return papers;


    }

    public void deletePaperFromDatabase(Paper paper) {
        mDBWriter.delete(TABLE_NAME, "primary_key=?",
                new String[] {String.valueOf(paper.getPrimaryKey())});
    }

    public void adjustPaperType(Paper paper) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(PRIMARY_KEY, paper.getPrimaryKey());
        contentValues.put(COLUMN_NAME_TITLE, paper.getTitle());
        contentValues.put(COLUMN_NAME_AUTHORS, paper.getAuthors().toString());
        contentValues.put(COLUMN_NAME_ABSTRACT, paper.getAbstract());
        contentValues.put(COLUMN_NAME_ISSN, paper.getISSN());
        contentValues.put(COLUMN_NAME_ISBN, paper.getISBN());
        contentValues.put(COLUMN_NAME_MD_URL, paper.getMDURL());
        contentValues.put(COLUMN_NAME_PDF_URL, paper.getURL());
        contentValues.put(COLUMN_NAME_PAPER_TYPE, "Saved");



        mDBWriter.update(TABLE_NAME, contentValues, "primary_key=?",
                new String[] {String.valueOf(paper.getPrimaryKey())});
    }
}
