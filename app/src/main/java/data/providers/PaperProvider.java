package data.providers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.models.Paper;
import data.models.XMLRoot;
import data.providers.api.ApiProvider;
import data.services.IEEEApiService;
import io.reactivex.Observable;

/**
 * Created by nickromero on 2/18/17.
 */

public class PaperProvider {
    private static IEEEApiService apiService = ApiProvider.getApiService();

    private static HashMap<String, String> QUERY_HASHMAP = new HashMap<String, String>() {{
        put("Metadata", "md");
        put("Document Title", "ti");
        put("Author(s)", "au");
        put("Publication Title", "jn");
        put("Publication Year", "py");
        put("Abstract", "ab");
        put("Index Terms", "idxterms");
        put("DOI", "doi");
        put("Publisher", "pu");
    }};




    public static Observable<List<Paper>> getAuthorPapers(String author) {
        return apiService.getPaperByAuthor(author);
    }

    public static Observable<XMLRoot> getRoot(String field, String searchTerm) {

        Map<String, String> data = new HashMap<>();
        data.put(QUERY_HASHMAP.get(field), searchTerm);

        return apiService.getRoot(data);
    }

    public static Observable<XMLRoot> getPapersByMetadata(String text, int curPageOffset) {


        Map<String, String> data = new HashMap<>();
        data.put("querytext", text);
        data.put("rs", (String.valueOf(curPageOffset)));

        return apiService.getPapersByMetaText(data);
    }

    public static Observable<XMLRoot> getInitalPapers(List<String> paperTitles) {
        String queryTemplate = "(";
        for (int i = 0; i < paperTitles.size(); i++) {
            String title = paperTitles.get(i);
            queryTemplate += "\"" + title + "\"";
            if (i < paperTitles.size() - 1)
                queryTemplate += " OR ";
        }
        queryTemplate += ")";

        return apiService.getInitialpapers(queryTemplate);


    }
}
