package biz.melamart.www.cov19.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.login.LoginRequest;
import biz.melamart.www.cov19.models.login.LoginResponse;
import biz.melamart.www.cov19.network.retrofit.RetrofitManager;
import biz.melamart.www.cov19.utils.COVSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHandler {

    private final ViewUpdateListener viewUpdateListener;
    private final Context mContext;

    public LoginHandler(Context mContext, ViewUpdateListener viewUpdateListener) {
        this.mContext = mContext;
        this.viewUpdateListener = viewUpdateListener;
    }

    public void login(LoginRequest loginRequest) {
        RetrofitManager.getInstance(mContext, false,"1").login(loginRequest, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    COVSettings.getInstance().setLoginDetailsInPref(loginResponse);
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }
}
