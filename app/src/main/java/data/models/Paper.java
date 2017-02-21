package data.models;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickromero on 1/3/17.
 */

@Root(name="document", strict = false)
public class Paper implements Serializable{


    @Element(name = RANK, required = false)
    private int mRank;

    @Element(name = TITLE, required = false)
    private String mPaperTitle;

    @Element(name = AUTHORS, required = false)
    private String mAuthors;

    @Element(name = AFFILIATIONS, required = false)
    private String mAffiliations;

    @Element(name = CONTROLLED_TERMS, required = false)
    private String mControlledTerms;

    @Element(name = THESAURUS_TERMS, required = false)
    private String mThesTerms;

    @Element(name = PUB_TITLE, required = false)
    private String mPubTitle;

    @Element(name = PUB_NUMBER, required = false)
    private String mPubNumber;

    @Element(name = PUB_TYPE, required = false)
    private String mPubType;

    @Element(name = PUBLISHER, required = false)
    private String mPublisher;

    @Element(name = PUB_YEAR, required = false)
    private String mPubYear;

    @Element(name = START_PAGE, required = false)
    private String mStartPage;

    @Element(name = END_PAGE, required = false)
    private String mEndPage;

    @Element(name = ABSTRACT, required = false)
    private String mAbstract;

    @Element(name = ISSN, required = false)
    private String mISSN;

    @Element(name = ISBN, required = false)
    private String mISBN;

    @Element(name = HTML_FLAG, required = false)
    private String mHTMLFlag;

    @Element(name = ARTICAL_NUMBER, required = false)
    private String mArticalNumber;

    @Element(name = DOI, required = false)
    private String mDOI;

    @Element(name = PUBLICATION_ID, required = false)
    private String mPublicationId;

    @Element(name = PART_NUMBER, required = false)
    private String mPartNumber;

    @Element(name = MD_URL, required = false)
    private String mMdURL;

    @Element(name = PDF_URL, required = false)
    private String mPDFURL;


    public static final String RANK = "rank";
    public static final String TITLE = "title";
    public static final String AUTHORS = "authors";
    public static final String AFFILIATIONS = "affiliations";
    public static final String CONTROLLED_TERMS = "controlledterms";
    public static final String THESAURUS_TERMS = "thesaurusterms";
    public static final String PUB_TITLE = "pubtitle";
    public static final String PUB_NUMBER = "punumber";
    public static final String PUB_TYPE = "pubtype";
    public static final String PUBLISHER = "publisher";
    public static final String PUB_YEAR = "py";
    public static final String START_PAGE = "spage";
    public static final String END_PAGE = "epage";
    public static final String ABSTRACT = "abstract";
    public static final String ISSN = "issn";
    public static final String ISBN = "isbn";
    public static final String HTML_FLAG = "htmlFlag";
    public static final String ARTICAL_NUMBER = "arnumber";
    public static final String DOI = "doi";
    public static final String PUBLICATION_ID = "publicationId";
    public static final String PART_NUMBER = "partnum";
    public static final String MD_URL = "mdurl";
    public static final String PDF_URL = "pdf";



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

    private List<String> mPaperAuthors;

    private ArrayList<Qualifier> myQualifers = new ArrayList<>();

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

    }



    public Paper() {


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

    public List<String> getAuthors() {

        if (mAuthors != null && mPaperAuthors == null) {
            mPaperAuthors = new ArrayList<>();
            for (String s : mAuthors.split(";")) {
                mPaperAuthors.add(s.trim());
            }
        }
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
        mAbstract = paperAbstract;
    }

    public String getAbstract() {
        return mAbstract;
    }
}
