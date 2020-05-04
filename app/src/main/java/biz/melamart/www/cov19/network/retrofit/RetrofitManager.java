package biz.melamart.www.cov19.network.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.models.countryStat.countryStatResponse;
import biz.melamart.www.cov19.models.hospitalData.hospitalResponse;
import biz.melamart.www.cov19.models.login.LoginRequest;
import biz.melamart.www.cov19.models.login.LoginResponse;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse;
import biz.melamart.www.cov19.models.nepalehr.Nepalehr;
import biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse;
import biz.melamart.www.cov19.models.ninja.Ninja;
import biz.melamart.www.cov19.models.suspect.suspectResponse;
import biz.melamart.www.cov19.utils.COVSettings;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitManager {

    private static final String HEADER_NAME = "Authorization";
    private static final String PATTERN_BEARER = "Bearer";
    private static final String SPACE = " ";
    private static RetrofitManager retrofitManager;

    private final RetrofitService service;
    private Retrofit retrofit;

    public static RetrofitManager getInstance(Context mContext, boolean hasToken, String urlType) {


        String baseUrl = mContext.getString(R.string.base_url);
if(urlType.trim().equals("2"))
{
    baseUrl= mContext.getString(R.string.base_url_nehr);
}
        else if(urlType.trim().equals("3"))
        {
            baseUrl= mContext.getString(R.string.base_url_ninja);
        }

        // for changing url we need to check this
        retrofitManager = new RetrofitManager(baseUrl, hasToken);
        return retrofitManager;
    }

    RetrofitManager(String baseUrl, boolean hasToken) {
        // for logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client;
        if (!hasToken) {
            client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    Response originalResponse = chain.proceed(builder.build());
                    return originalResponse;
                }
            }).addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS).build();
        } else {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();
                            builder.addHeader("X-DreamFactory-Api-Key", "5bd8e19b1d206bd9862ebf1e032852ad4c6543d4cbcf05f83a8e88671757c317");
                            builder.addHeader("DreamFactory-Application-Name", "viewRoleApp");
//                            builder.addHeader("X-DreamFactory-Session-Token", COVSettings.getInstance().getLoginDetailsFromPref().getSession_token());
                            return chain.proceed(builder.build());
                        }
                    }).addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS).build();
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(RetrofitService.class);
    }

    public void login(LoginRequest loginRequest, Callback<LoginResponse> loginResponseCallback) {
        Call<LoginResponse> apiCall = service.login(loginRequest.getEmail(), loginRequest.getPassword());
        apiCall.enqueue(loginResponseCallback);
    }

    public void getEmergencyContactData(Callback<emergencyContactResponse> feedResponseCallback) {
        Call<emergencyContactResponse> apiCall = service.getEmergencyContacts();
        apiCall.enqueue(feedResponseCallback);
    }
    public void postSuspect(suspectResponse suspectResponse, Callback<suspectResponse> jsonObjectCallback) {
        Call<suspectResponse> apiCall = service.postSuspect(suspectResponse);
        apiCall.enqueue(jsonObjectCallback);
    }



    public void getFilteredEmergencyData(Callback<emergencyContactResponse> feedResponseCallback,String filter) {
        Call<emergencyContactResponse> apiCall = service.getFilteredEmergencyData(filter+"&order=name%20ASC");
        apiCall.enqueue(feedResponseCallback);
    }

    public void getFeedData(Callback<newsFeedResponse> feedResponseCallback) {
        Call<newsFeedResponse> apiCall = service.getFeedData();
        apiCall.enqueue(feedResponseCallback);
    }

    public void getFilteredFeedData(Callback<newsFeedResponse> feedResponseCallback,String filter) {
        Call<newsFeedResponse> apiCall = service.getFilteredFeedData(filter+"&order=feedId%20DSC");
        apiCall.enqueue(feedResponseCallback);
    }
    public void getStatData(Callback<countryStatResponse> feedResponseCallback) {
        Call<countryStatResponse> apiCall = service.getStatData();
        apiCall.enqueue(feedResponseCallback);
    }


    public void getNinjaData(Callback<List<Ninja>> feedResponseCallback) {
        Call<List<Ninja>> apiCall = service.getNinjaData();
        apiCall.enqueue(feedResponseCallback);
    }


    public void getFilteredStatData(Callback<countryStatResponse> feedResponseCallback,String filter) {
        Call<countryStatResponse> apiCall = service.getFilteredStatData(filter);
        apiCall.enqueue(feedResponseCallback);
    }


    public void getHospitalData(Callback<hospitalResponse> feedResponseCallback) {
        Call<hospitalResponse> apiCall = service.getHospitalData();
        apiCall.enqueue(feedResponseCallback);
    }

    public void getFilteredHospitalData(Callback<hospitalResponse> feedResponseCallback,String filter) {
        Call<hospitalResponse> apiCall = service.getFilteredHospitalData(filter+"&order=hospitalName%20ASC");
        apiCall.enqueue(feedResponseCallback);
    }


    public void getBayalpataData(Callback<Nepalehr> feedResponseCallback) {
        Call<Nepalehr> apiCall = service.getBayalpataData();
        apiCall.enqueue(feedResponseCallback);
    }

    public void getDolakhaData(Callback<Nepalehr> feedResponseCallback) {
        Call<Nepalehr> apiCall = service.getDolakhaData();
        apiCall.enqueue(feedResponseCallback);
    }

    public void getChaurmanduData(Callback<Nepalehr> feedResponseCallback) {
        Call<Nepalehr> apiCall = service.getChaurmanduData();
        apiCall.enqueue(feedResponseCallback);
    }
}