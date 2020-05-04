package biz.melamart.www.cov19.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.interfaces.hospitalInterface;
import biz.melamart.www.cov19.models.hospitalData.hospitalResponse;
import biz.melamart.www.cov19.models.nepalehr.Nepalehr;
import biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse;
import biz.melamart.www.cov19.network.retrofit.RetrofitManager;
import biz.melamart.www.cov19.utils.COVSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hospitalHandler {

    private final hospitalInterface viewUpdateListener;
    private final Context mContext;

    public hospitalHandler(Context mContext, hospitalInterface viewUpdateListener) {
        this.mContext = mContext;
        this.viewUpdateListener = viewUpdateListener;
    }

    public void getFullData() {
        RetrofitManager.getInstance(mContext, true, "1").getHospitalData(new Callback<hospitalResponse>() {
            @Override
            public void onResponse(Call<hospitalResponse> call, Response<hospitalResponse> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setHospitalResponse(response.body());
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
            public void onFailure(Call<hospitalResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }


    public void getFilteredData(String filterData) {
        RetrofitManager.getInstance(mContext, true, "1").getFilteredHospitalData(new Callback<hospitalResponse>() {
            @Override
            public void onResponse(Call<hospitalResponse> call, Response<hospitalResponse> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setHospitalResponse(response.body());
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
            public void onFailure(Call<hospitalResponse> call, Throwable t) {
                viewUpdateListener.failure(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        },filterData);
    }


    public void getBayalpataData() {
        RetrofitManager.getInstance(mContext, true, "2").getBayalpataData(new Callback<Nepalehr>() {
            @Override
            public void onResponse(Call<Nepalehr> call, Response<Nepalehr> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setBayalpataData(response.body());
//                    Toast.makeText(mContext, response.body().getQueryResult().getData().getRows().get(0).getNumberOfSuspected()+"", Toast.LENGTH_LONG).show();

                    viewUpdateListener.successBayal();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        viewUpdateListener.failureBayal(jsonObject.getJSONObject("error").getString("message"));

                    } catch (IOException | NullPointerException | JsonParseException | JSONException e) {
                        e.printStackTrace();
                        viewUpdateListener.failureBayal(mContext.getResources().getString(R.string.msg_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<Nepalehr> call, Throwable t) {
                viewUpdateListener.failureBayal(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void getDolakhaData() {
        RetrofitManager.getInstance(mContext, true, "2").getDolakhaData(new Callback<Nepalehr>() {
            @Override
            public void onResponse(Call<Nepalehr> call, Response<Nepalehr> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setDolakhaData(response.body());
//                    Toast.makeText(mContext, response.body().getQueryResult().getData().getRows().get(0).getNumberOfSuspected()+"", Toast.LENGTH_LONG).show();

                    viewUpdateListener.successDolakha();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        viewUpdateListener.failureDolakha(jsonObject.getJSONObject("error").getString("message"));

                    } catch (IOException | NullPointerException | JsonParseException | JSONException e) {
                        e.printStackTrace();
                        viewUpdateListener.failureDolakha(mContext.getResources().getString(R.string.msg_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<Nepalehr> call, Throwable t) {
                viewUpdateListener.failureDolakha(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void getChaurmanduData() {
        RetrofitManager.getInstance(mContext, true, "2").getChaurmanduData(new Callback<Nepalehr>() {
            @Override
            public void onResponse(Call<Nepalehr> call, Response<Nepalehr> response) {
                if (response.isSuccessful()) {
                    COVSettings.getInstance().setChaurmanduData(response.body());
//                    Toast.makeText(mContext, response.body().getQueryResult().getData().getRows().get(0).getNumberOfSuspected()+"", Toast.LENGTH_LONG).show();

                    viewUpdateListener.successCharmandu();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);

                        viewUpdateListener.failureChaurmandu(jsonObject.getJSONObject("error").getString("message"));

                    } catch (IOException | NullPointerException | JsonParseException | JSONException e) {
                        e.printStackTrace();
                        viewUpdateListener.failureChaurmandu(mContext.getResources().getString(R.string.msg_error));
                    }
                }
            }

            @Override
            public void onFailure(Call<Nepalehr> call, Throwable t) {
                viewUpdateListener.failureChaurmandu(t.getMessage());
                Log.d("onFailure", t.toString());
            }
        });
    }
}
