package biz.melamart.www.cov19.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.interfaces.statViewUpdateListner;
import biz.melamart.www.cov19.models.countryStat.countryStatResponse;
import biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse;
import biz.melamart.www.cov19.models.ninja.Ninja;
import biz.melamart.www.cov19.network.retrofit.RetrofitManager;
import biz.melamart.www.cov19.utils.COVSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class statHandler {

    private final statViewUpdateListner viewUpdateListener;
    private final Context mContext;

    public statHandler(Context mContext, statViewUpdateListner viewUpdateListener) {
        this.mContext = mContext;
        this.viewUpdateListener = viewUpdateListener;
    }

    public void getFullData() {
        RetrofitManager.getInstance(mContext, true, "1").getStatData(new Callback<countryStatResponse>() {
            @Override
            public void onResponse(Call<countryStatResponse> call, Response<countryStatResponse> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setCountryStatResponse(response.body());
                    viewUpdateListener.success();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        viewUpdateListener.failure(jsonObject.getJSONObject("error").getString("message"));

                    } catch (IOException | NullPointerException | JsonParseException | JSONException e) {
                        e.printStackTrace();
                        viewUpdateListener.failure(mContext.getResources().getString(R.string.msg_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<countryStatResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void getNinjaData() {
        RetrofitManager.getInstance(mContext, true, "3").getNinjaData(new Callback<List<Ninja>>() {
            @Override
            public void onResponse(Call<List<Ninja>> call, Response<List<Ninja>> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setNinjaData(response.body());
//                    Toast.makeText(mContext, response.body().get(0).getCountry()+"", Toast.LENGTH_SHORT).show();
                    viewUpdateListener.success();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        viewUpdateListener.failure(jsonObject.getJSONObject("error").getString("message"));

                    } catch (IOException | NullPointerException | JsonParseException | JSONException e) {
                        e.printStackTrace();
                        viewUpdateListener.failure(mContext.getResources().getString(R.string.msg_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ninja>> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }


    public void getFilteredData(String filterData) {
        RetrofitManager.getInstance(mContext, true, "1").getFilteredStatData(new Callback<countryStatResponse>() {
            @Override
            public void onResponse(Call<countryStatResponse> call, Response<countryStatResponse> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setCountryStatResponse(response.body());
                    viewUpdateListener.success();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        viewUpdateListener.failure(jsonObject.getJSONObject("error").getString("message"));

                    } catch (IOException | NullPointerException | JsonParseException | JSONException e) {
                        e.printStackTrace();
                        viewUpdateListener.failure(mContext.getResources().getString(R.string.msg_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<countryStatResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        },filterData);
    }
}
