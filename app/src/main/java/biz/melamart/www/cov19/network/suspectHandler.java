package biz.melamart.www.cov19.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.suspect.suspectResponse;
import biz.melamart.www.cov19.network.retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class suspectHandler  {

    private final ViewUpdateListener viewUpdateListener;
    private final Context mContext;

    public suspectHandler(Context mContext, ViewUpdateListener viewUpdateListener) {
        this.mContext = mContext;
        this.viewUpdateListener = viewUpdateListener;
    }

    public void postSuspect(suspectResponse suspectResponse) {
        RetrofitManager.getInstance(mContext, true, "1").postSuspect(suspectResponse, new Callback<suspectResponse>() {
            @Override
            public void onResponse(Call<suspectResponse> call, Response<suspectResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("aaaa", "onResponse signup success: " + response.body());
                    viewUpdateListener.success();
                } else {
                    viewUpdateListener.success();
                }
            }

            @Override
            public void onFailure(Call<suspectResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }
}
