package data;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nickromero on 1/3/17.
 */

public class Paper {

    //Title of a Paper
    private String mPaperTitle;

    //List of all the authors for a paper
    private ArrayList<String> mPaperAuthors;

    //Saved location of paper.. Maybe not needed?
    private String mPathToFile;

    //Saved name of pdf. Should contain .pdf extension
    private String mFileName;

    //Bitmap to hold the intial page of the paper
    private Bitmap mFirstPageBitMap;

    //Reference to the last read page a user was on
    //may not work with the way I'm opening pdfs
    private int mLastPageRead;

    public Paper(String title, ArrayList<String> authors, String downloadLocation, String fileName) {

        mPaperTitle = title;
        mPaperAuthors = authors;
        mPathToFile = downloadLocation;
        mFileName = fileName;
        mLastPageRead = 0;

        File pdfToRender = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , "pdf.pdf");
        ParcelFileDescriptor fileDescriptor;

        try {
            fileDescriptor = ParcelFileDescriptor.open(pdfToRender,
                    ParcelFileDescriptor.MODE_READ_WRITE);

            PdfRenderer render = new PdfRenderer(fileDescriptor);

            PdfRenderer.Page firstPage;

            firstPage = render.openPage(0);

            Bitmap bitmap = Bitmap.createBitmap(firstPage.getWidth(), firstPage.getHeight(),
                    Bitmap.Config.ARGB_8888);

            firstPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mFirstPageBitMap = bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getTitle() {
        return mPaperTitle;
    }

    public String getFilePath() {
        return mPathToFile;
    }

    public ArrayList<String> getAuthors() {
        return mPaperAuthors;
    }

    public String getFileName() {
        return mFileName;
    }

    public int getLastPageRead() {
        return mLastPageRead;
    }

    public Bitmap getFirstPageBitMap() {
        return mFirstPageBitMap;
    }

    public void setLastPageRead(int lastPageRead) {
        mLastPageRead = lastPageRead;
    }
}
