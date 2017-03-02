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

    public static Observable<List<Paper>> getAuthorPapers(String author) {
        return apiService.getPaperByAuthor(author);
    }

    public static Observable<XMLRoot> getRoot(String author) {

        return apiService.getRoot(author);
    }

    public static Observable<XMLRoot> getPapersByMetadata(String text, int curPageOffset) {

        //String query = author  + curPageOffset;
        //System.out.println(query);
        Map<String, String> data = new HashMap<>();
        data.put("querytext", text);
        data.put("rs", (String.valueOf(curPageOffset)));

        return apiService.getPapersByMetaText(data);
    }

}
