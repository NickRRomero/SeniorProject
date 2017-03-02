package data.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by nickromero on 2/18/17.
 */

@Root(name ="root")
public class XMLRoot {

    public static final String TOTAL_PAPERS_FOUND = "totalfound";
    public static final String TOTAL_PAPER_SEARCHED = "totalsearched";
    public static final String DOCUMENT_ROOT = "document";

    @Element(name = TOTAL_PAPERS_FOUND)
    public long totalfound;
    @Element(name = TOTAL_PAPER_SEARCHED)
    public long totalsearched;
    @ElementList(name = DOCUMENT_ROOT, required = false, inline = true)
     public List<Paper> foundPapers;

    public List<Paper> getFoundPapers() {
        return foundPapers;
    }


}
