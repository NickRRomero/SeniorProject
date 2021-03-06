package data.services;

import android.util.Xml;

import java.util.List;
import java.util.Map;

import data.models.Paper;
import data.models.XMLRoot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by nickromero on 2/18/17.
 */

public interface IEEEApiService {


    /**
     * Get a a list of papers by a certain author
     * @param author
     * @return
     */
    @GET("ipsSearch.jsp?au=")
    Observable<List<Paper>> getPaperByAuthor(@Query("author") String author);

    @GET("ipsSearch.jsp")
    Observable<XMLRoot> getRoot(@QueryMap Map<String, String>  options);

    /**
     * Searches the IEEE Gateway using the md parameter. Searches all metadata, titles, abstracts
     * @param options
     * @return
     */
    @GET("ipsSearch.jsp")
    Observable<XMLRoot> getPapersByMetaText(@QueryMap Map<String, String>  options);


    /**
     * Uses a built querytext string to query the IEEE Xplore database with titles
     * @param paperTitles
     * @return
     */
    @GET("ipsSearch.jsp")
    Observable<XMLRoot> getInitialpapers(@Query("querytext") String paperTitles);



    //@GET("{url}")
    //Observable<XMLRoot> getRoot(@Path("url") String url);


}
