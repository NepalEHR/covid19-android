package biz.melamart.www.cov19.fragments;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.WebActivity;
import biz.melamart.www.cov19.activity.contactTraceActivity;
import biz.melamart.www.cov19.activity.faqActivity;
import biz.melamart.www.cov19.activity.locateActivity;
import biz.melamart.www.cov19.activity.selfEvaluation;
import biz.melamart.www.cov19.service.GoogleService;
import biz.melamart.www.cov19.utils.COVSettings;
import butterknife.BindView;
import butterknife.ButterKnife;

public class moreFragment extends Fragment {

    @BindView(R.id.locate)
CardView locate;

    @BindView(R.id.contractTrace)
    CardView contractTrace;


    @BindView(R.id.checkStat)
    CheckBox checkStat;

//
//    @BindView(R.id.faqCard)
//    CardView faqCard;

    @BindView(R.id.cardConsult)
    CardView cardConsult;

    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude,longitude;
    Geocoder geocoder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.more_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), locateActivity.class);
                startActivity(intent);
//                Toast.makeText(getActivity(), "Tracking Zone", Toast.LENGTH_SHORT).show();
            }
        });

        contractTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), contactTraceActivity.class);
                startActivity(intent);
            }
        });

//        faqCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getActivity(), selfEvaluation.class);
//                startActivity(intent);
//            }
//        });


        cardConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), WebActivity.class);
//                intent.putExtra("newsUrl", "https://covid.apollo247.com/");
//                getActivity().startActivity(intent);
                Intent intent = new Intent(getActivity(), selfEvaluation.class);
                startActivity(intent);
            }
        });

        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        medit = mPref.edit();

        checkStat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                     String homeLoc = COVSettings.getInstance().getHomeLoc();
                     if(homeLoc.trim().equals("No address set")) {

                         Toast.makeText(getActivity(), "Set Quarantined Location to start tracking!!!", Toast.LENGTH_SHORT).show();
                     }
                     else
                     {
                         if(isChecked)
                         {

//                             notifyThis("Tracking Enabled");


                             Toast.makeText(getActivity(), "Tracking Enabled", Toast.LENGTH_SHORT).show();

                             if (boolean_permission) {

                                 if (mPref.getString("service", "").matches("")) {
                                     medit.putString("service", "service").commit();

                                     Intent intent = new Intent(getActivity(), GoogleService.class);
                                     getActivity().startService(intent);


                                 } else {
//                                     Toast.makeText(getActivity(), "Service is already running", Toast.LENGTH_SHORT).show();
                                 }
                             } else {
                                 Toast.makeText(getActivity(), "Please enable the gps", Toast.LENGTH_SHORT).show();
                             }

                         }
                         else
                         {
                             Toast.makeText(getActivity(), "Tracking Disabled", Toast.LENGTH_SHORT).show();
//                             notifyThis("Tracking Disabled");
                             medit.putString("service", "").commit();
                             Intent intent = new Intent(getActivity(), GoogleService.class);
                             getActivity().stopService(intent);
                         }
                     }

                 }
             }
        );

        fn_permission();
            if (mPref.getString("service", "").matches("")) {
              checkStat.setChecked(false);
            } else {
                checkStat.setChecked(true);
                Toast.makeText(getActivity(), "Service is already running", Toast.LENGTH_SHORT).show();
            }

        return view;
    }

    public void notifyThis( String message) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this.getActivity());
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVibrate(new long[]{0L})
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_001_coronavirus)
                .setTicker("Tracking Enabled")
                .setContentTitle("COV 19")
                .setContentText(message)
                .setContentInfo("COV 19");

        NotificationManager nm = (NotificationManager) this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION))) {


            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;

                } else {
                    Toast.makeText(getActivity(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);

//                tv_area.setText(addresses.get(0).getAdminArea());
//                tv_locality.setText(stateName);
//                tv_address.setText(countryName);



            } catch (IOException e1) {
                e1.printStackTrace();
            }


//            tv_latitude.setText(latitude+"");
//            tv_longitude.setText(longitude+"");
//            tv_address.getText();


        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }



}