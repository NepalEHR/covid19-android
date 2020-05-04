package biz.melamart.www.cov19.network.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import biz.melamart.www.cov19.models.countryStat.countryStatResponse;
import biz.melamart.www.cov19.models.hospitalData.hospitalResponse;
import biz.melamart.www.cov19.models.login.LoginResponse;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse;
import biz.melamart.www.cov19.models.nepalehr.Nepalehr;
import biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse;
import biz.melamart.www.cov19.models.ninja.Ninja;
import biz.melamart.www.cov19.models.suspect.suspectResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetrofitService {
    /*
     * RetrofitService get annotation with our URL
     *
     */

    @FormUrlEncoded
    @POST("api/v2/user/session")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("api/v2/cov19/_table/contact?order=name%20ASC")
    Call<emergencyContactResponse> getEmergencyContacts();


    @POST("api/v2/cov19/_table/contactPerson")
    Call<suspectResponse> postSuspect(@Body suspectResponse suspectResponse);


    //filter=(categoriesId=3)%20or%20(categoriesId LIKE %3,%)%20or%20(categoriesId LIKE %,3%)
    @GET("api/v2/cov19/_table/contact")
    Call<emergencyContactResponse> getFilteredEmergencyData(@Query("filter") String filter);

    @GET("api/v2/cov19/_table/newsFeed?order=feedId%20DSC")
    Call<newsFeedResponse> getFeedData();

    //filter=(categoriesId=3)%20or%20(categoriesId LIKE %3,%)%20or%20(categoriesId LIKE %,3%)
    @GET("api/v2/cov19/_table/newsFeed")
    Call<newsFeedResponse> getFilteredFeedData(@Query("filter") String filter);


    @GET("api/v2/cov19/_table/countryStatView")
    Call<countryStatResponse> getStatData();

    //filter=(categoriesId=3)%20or%20(categoriesId LIKE %3,%)%20or%20(categoriesId LIKE %,3%)
    @GET("api/v2/cov19/_table/countryStatView")
    Call<countryStatResponse> getFilteredStatData(@Query("filter") String filter);


    @GET("api/v2/cov19/_table/hospitalDataView?order=hospitalName%20ASC")
    Call<hospitalResponse> getHospitalData();

    //filter=(categoriesId=3)%20or%20(categoriesId LIKE %3,%)%20or%20(categoriesId LIKE %,3%)
    @GET("api/v2/cov19/_table/hospitalDataView")
    Call<hospitalResponse> getFilteredHospitalData(@Query("filter") String filter);

    @GET("api/queries/24/results.json?api_key=IuRzz9roR6LVepCUrKWFsT458h1PEMM6IB4Ys6G4")
    Call<Nepalehr> getBayalpataData();


    @GET("api/queries/28/results.json?api_key=8dYTSLtygHkOrH2F4BI1cpU5fvUAUr2US46U3xtj")
    Call<Nepalehr> getDolakhaData();


    @GET("api/queries/26/results.json?api_key=UmfEo43PL7cgKcksR1PwWr6xjZXit5pPaYKq15pM")
    Call<Nepalehr> getChaurmanduData();


    @GET("countries?sort=cases")
    Call<List<Ninja>> getNinjaData();

}
