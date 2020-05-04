package biz.melamart.www.cov19.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.locateActivity;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.GeneralUtils;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GoogleService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 5000;
    public static String str_receiver = "biz.melamart.www.cov19.service.receiver";
    Intent intent;
    Notification noti;
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 500;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    private float OrderLongitude = 85.3721781f;
    private float OrderLatitude = 27.6759779f;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    private GoogleMap googleMap;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    float zoomval=14;
    boolean firstSet = true;

    SharedPreferences mPref;
    public GoogleService() {

    }
Context mContext;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;
        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
        intent = new Intent(str_receiver);

        fn_getlocation();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        if (!mPref.getString("service", "").matches(""))
        {
            Log.i("EXIT", "ondestroy test!");
            Intent broadcastIntent = new Intent(this, locationRestarterBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        String homeLoc = COVSettings.getInstance().getHomeLoc();
                        if(!homeLoc.trim().equals("No address set")) {
                            String[] homeData = homeLoc.split(",");
                            OrderLatitude = Float.parseFloat(homeData[1].trim());
                            OrderLongitude = Float.parseFloat(homeData[0].trim());
                            float distance = COVSettings.getInstance().calculateDistance(OrderLatitude,OrderLongitude,Float.parseFloat(location.getLatitude()+""),Float.parseFloat(location.getLongitude()+""));

                            Log.e("latitude Serdis", distance + "");
                            if(distance > 100f)
                            {

                                notifyThis( "You are in Danger Zone");
                                Log.e("latitude SerdisB", distance + "");
                            }

                        }
                        Log.e("latitude Ser", location.getLatitude() + "");
                        Log.e("longitude Ser", location.getLongitude() + "");




                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }

            }


            if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){


                        Log.e("latitude Ser",location.getLatitude()+"");
                        Log.e("longitude Ser",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        String homeLoc = COVSettings.getInstance().getHomeLoc();
                        if(!homeLoc.trim().equals("No address set")) {
                            String[] homeData = homeLoc.split(",");
                            OrderLatitude = Float.parseFloat(homeData[1].trim());
                            OrderLongitude = Float.parseFloat(homeData[0].trim());
                            float distance = COVSettings.getInstance().calculateDistance(OrderLatitude,OrderLongitude,Float.parseFloat(location.getLatitude()+""),Float.parseFloat(location.getLongitude()+""));

                            if(distance > 100)
                            {
                                notifyThis( "You are in Danger Zone");
                                Notification noti = new Notification.Builder(mContext)
                                        .setContentTitle("COV 19")
                                        .setContentText("You are on Danger Zone")
                                        .setSmallIcon(R.drawable.ic_001_coronavirus)
                                        .build();
                            }

                        }
                        fn_update(location);
                    }
                }
            }


        }

    }

    public void notifyThis( String message) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_001_coronavirus)
                .setTicker("Tracking Enabled")
                .setContentTitle("COV 19")
                .setContentText(message)
                .setContentInfo("COV 19");

        NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

    private void fn_update(Location location){

        intent.putExtra("latutide",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");
        sendBroadcast(intent);
    }


}