package data.providers;

import java.util.Collection;
import java.util.List;

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
        System.out.println(apiService.getRoot(author).toString());
        return apiService.getRoot(author);
    }

    public static String getString() {
        return apiService.getString();
    }

}
