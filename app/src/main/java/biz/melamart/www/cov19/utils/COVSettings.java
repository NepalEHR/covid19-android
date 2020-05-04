package biz.melamart.www.cov19.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

import biz.melamart.www.cov19.COVApplication;
import biz.melamart.www.cov19.models.countryStat.countryStatResponse;
import biz.melamart.www.cov19.models.hospitalData.hospitalResponse;
import biz.melamart.www.cov19.models.login.LoginResponse;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse;
import biz.melamart.www.cov19.models.nepalehr.Nepalehr;
import biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse;
import biz.melamart.www.cov19.models.ninja.Ninja;

public class COVSettings {

    private static final String SP_NAME = "COV";
    private static final String SP_LOGIN_DETAILS = "loginDetails";

    private static final String SP_HOME_LOC = "homelocation";
    private static final String SP_LANGUAGE = "language";
    private static COVSettings appSettings;
    private static Context context;
    private SharedPreferences sharedPreference;

    private static final int earthRadius = 6371;
    countryStatResponse countryStatResponse;
    newsFeedResponse newsFeedResponse;
    hospitalResponse hospitalResponse;
    Nepalehr bayalpataData;
    Nepalehr DolakhaData;
    Nepalehr chaurmanduData;
    List<Ninja> ninjaData;
    private biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse emergencyContactResponse;
    public static COVSettings getInstance() {
        if (appSettings == null) {
            synchronized (COVSettings.class) {
                appSettings = new COVSettings();
                context = COVApplication.getContext();
            }
        }
        return appSettings;
    }

    public static float calculateDistance(float lat1, float lon1, float lat2, float lon2) {
        float dLat = (float) Math.toRadians(lat2 - lat1);
        float dLon = (float) Math.toRadians(lon2 - lon1);
        float a =
                (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        float d = earthRadius * c;
        return d*1000;
    }


    private SharedPreferences getSharedPreference() {
        if (sharedPreference == null) {
            synchronized (COVSettings.class) {

                sharedPreference = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
            }
        }
        return sharedPreference;
    }

    public SharedPreferences.Editor getSharedPreferenceEditor() {
        return getSharedPreference().edit();
    }
    public void setHomeLoc(String location)
    {
        getSharedPreferenceEditor().putString(SP_HOME_LOC,location).commit();
    }

    public String getHomeLoc() {
        String data = getSharedPreference().getString(SP_HOME_LOC, "No address set");
        return data;
    }

    public void setLanguage(String language)
    {
        getSharedPreferenceEditor().putString(SP_LANGUAGE,language).commit();
    }
    public void toggleLanguage()
    {
        String languageToLoad  = "Nepali"; // your language
        if(getLanguage().trim().equals("Nepali"))
        {
            languageToLoad  = "English"; // your language
        }
        getSharedPreferenceEditor().putString(SP_LANGUAGE,languageToLoad).commit();
    }

    public String getLanguage() {
        String data = getSharedPreference().getString(SP_LANGUAGE, "CHAINA");
        return data;
    }

    public void setLocale()
    {
        String languageToLoad  = "ne"; // your language
        if(getLanguage().trim().equals("Nepali"))
        {
            languageToLoad  = "ne"; // your language
        }

        Resources res = context.getResources();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());

    }

    public void setLoginDetailsInPref(LoginResponse loginDetails) {
        Gson gson = new Gson();
        String json = gson.toJson(loginDetails);
        getSharedPreferenceEditor().putString(SP_LOGIN_DETAILS, json).commit();
    }

    public LoginResponse getLoginDetailsFromPref() {
        Gson gson = new Gson();
        String json = getSharedPreference().getString(SP_LOGIN_DETAILS, "");
        return gson.fromJson(json, LoginResponse.class);
    }

    public biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse getEmergencyContactResponse() {
        return emergencyContactResponse;
    }

    public void setEmergencyContactResponse(biz.melamart.www.cov19.models.emergencyContact.emergencyContactResponse emergencyContactResponse) {
        this.emergencyContactResponse = emergencyContactResponse;
    }

    public biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse getNewsFeedResponse() {
        return newsFeedResponse;
    }

    public void setNewsFeedResponse(biz.melamart.www.cov19.models.newsFeeds.newsFeedResponse newsFeedResponse) {
        this.newsFeedResponse = newsFeedResponse;
    }

    public biz.melamart.www.cov19.models.countryStat.countryStatResponse getCountryStatResponse() {
        return countryStatResponse;
    }

    public void setCountryStatResponse(biz.melamart.www.cov19.models.countryStat.countryStatResponse countryStatResponse) {
        this.countryStatResponse = countryStatResponse;
    }

    public biz.melamart.www.cov19.models.hospitalData.hospitalResponse getHospitalResponse() {
        return hospitalResponse;
    }

    public void setHospitalResponse(biz.melamart.www.cov19.models.hospitalData.hospitalResponse hospitalResponse) {
        this.hospitalResponse = hospitalResponse;
    }

    public static String getSpName() {
        return SP_NAME;
    }

    public static String getSpLoginDetails() {
        return SP_LOGIN_DETAILS;
    }

    public static String getSpHomeLoc() {
        return SP_HOME_LOC;
    }

    public static COVSettings getAppSettings() {
        return appSettings;
    }

    public static void setAppSettings(COVSettings appSettings) {
        COVSettings.appSettings = appSettings;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        COVSettings.context = context;
    }

    public void setSharedPreference(SharedPreferences sharedPreference) {
        this.sharedPreference = sharedPreference;
    }

    public static int getEarthRadius() {
        return earthRadius;
    }

    public Nepalehr getBayalpataData() {
        return bayalpataData;
    }

    public void setBayalpataData(Nepalehr bayalpataData) {
        this.bayalpataData = bayalpataData;
    }

    public List<Ninja> getNinjaData() {
        return ninjaData;
    }

    public void setNinjaData(List<Ninja> ninjaData) {
        this.ninjaData = ninjaData;
    }

    public Nepalehr getDolakhaData() {
        return DolakhaData;
    }

    public void setDolakhaData(Nepalehr dolakhaData) {
        DolakhaData = dolakhaData;
    }

    public Nepalehr getChaurmanduData() {
        return chaurmanduData;
    }

    public void setChaurmanduData(Nepalehr chaurmanduData) {
        this.chaurmanduData = chaurmanduData;
    }
}
