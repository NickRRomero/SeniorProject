package data.providers.api;

import android.util.Xml;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nickromero.seniorproject.views.MainActivity;

import org.simpleframework.xml.core.Persister;

import data.models.XMLRoot;
import data.services.IEEEApiService;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by nickromero on 2/18/17.
 */

public class ApiProvider {
    private static IEEEApiService apiService;

    private static final String IEEE_XPLORE_GATEWAY_URL = "http://ieeexplore.ieee.org/gateway/";



    public static IEEEApiService getApiService() {
        if (apiService ==null) {
            apiService = new Retrofit.Builder()
                    .baseUrl(IEEE_XPLORE_GATEWAY_URL)
                    .client(new OkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(SimpleXmlConverterFactory.create(new Persister()))
                    .build()
                    .create(IEEEApiService.class);
        }
        return apiService;
    }


}
