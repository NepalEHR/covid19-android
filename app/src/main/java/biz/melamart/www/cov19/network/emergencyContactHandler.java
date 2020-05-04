package biz.melamart.www.cov19.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse;
import biz.melamart.www.cov19.network.retrofit.RetrofitManager;
import biz.melamart.www.cov19.utils.COVSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class emergencyContactHandler{

    private final ViewUpdateListener viewUpdateListener;
    private final Context mContext;

    public emergencyContactHandler(Context mContext, ViewUpdateListener viewUpdateListener) {
        this.mContext = mContext;
        this.viewUpdateListener = viewUpdateListener;
    }

    public void getFullData() {
        RetrofitManager.getInstance(mContext, true, "1").getEmergencyContactData(new Callback<emergencyContactResponse>() {
            @Override
            public void onResponse(Call<emergencyContactResponse> call, Response<emergencyContactResponse> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setEmergencyContactResponse(response.body());
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
            public void onFailure(Call<emergencyContactResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }


    public void getFilteredData(String filterData) {
        RetrofitManager.getInstance(mContext, true, "1").getFilteredEmergencyData(new Callback<emergencyContactResponse>() {
            @Override
            public void onResponse(Call<emergencyContactResponse> call, Response<emergencyContactResponse> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setEmergencyContactResponse(response.body());
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
            public void onFailure(Call<emergencyContactResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        },filterData);
    }
}
