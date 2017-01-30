package data;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickromero on 1/3/17.
 */

public class Paper {

    /**
     * Title of a paper
     */
    private String mPaperTitle;

    /**
     * List containing all authors of a paper
     */
    private ArrayList<String> mPaperAuthors;

    /**
     * Absolute path to where a file is saved on the device
     */
    private String mPathToFile;

    /**
     * Name of the downloaded file
     */
    private String mFileName;

    /**
     * Possible bitmap to hold the image of a paper
     */
    private Bitmap mFirstPageBitMap;

    /**
     * Reference to the last page read by the user. Useful when
     * returning to a paper.
     */
    private int mLastPageRead;

    /**
     * This list holds any subscriptions that were used to recommend this paper.
     * A larger list of subscriptions associated with any paper implies a higher
     * recommendation for the paper/
     */
    private List<Subscription> listOfSubscriptions;

    /**
     * Holder for a papers abstract. Can be retrieved with a paper's metadata
     */
    private String sAbstract = "Fake abstract";

    private ArrayList<Qualifier> myQualifers;

    /**
     * Paper constructor. Called whenever a paper is saved to the device.
     * @param title String title of a paper
     * @param authors Collection of strings representing author names
     * @param downloadLocation String representation of a file's downloaded location
     * @param fileName String name of the downloaded file
     */
    public Paper(String title, ArrayList<String> authors, String downloadLocation, String fileName) {

        mPaperTitle = title;
        mPaperAuthors = authors;
        mPathToFile = downloadLocation;
        mFileName = fileName;
        mLastPageRead = 0;
        myQualifers = new ArrayList<>();

        File pdfToRender = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , fileName);
        ParcelFileDescriptor fileDescriptor;

        /*
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
        }*/


    }

    /**
     * Called whenever a paper is retrieved from the database using a created qualifier
     * @param newQualifier
     */
    public void addQualifier(Qualifier newQualifier) {
        myQualifers.add(newQualifier);
    }

    /**
     * Getter is used when displaying a paper on the screen.
     * @return any qualifiers associated with the paper
     */
    public ArrayList<Qualifier> getQualifiers() {
        return myQualifers;
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

    public void setAbstract(String paperAbstract) {
        sAbstract = paperAbstract;
    }

    public String getAbstract() {
        return sAbstract;
    }
}
