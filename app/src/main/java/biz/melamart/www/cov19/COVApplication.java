package biz.melamart.www.cov19;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.GeneralUtils;

public class COVApplication extends Application {
    private static COVApplication mInstance;
    private static COVSettings settings;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // initialize settings class
        Stetho.initializeWithDefaults(this);
        settings = COVSettings.getInstance();
        setImageSizeToDownload();
    }

    private void setImageSizeToDownload() {
        GeneralUtils.setImageHeight();
        GeneralUtils.setImageWidth();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        LocaleManager.setLocale(this);
//    }
    public static COVSettings getSettings() {
        return settings;
    }

    public static synchronized COVApplication getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    public void showLongToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}
