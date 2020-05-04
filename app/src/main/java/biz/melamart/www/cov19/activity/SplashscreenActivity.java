package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.login.LoginRequest;
import biz.melamart.www.cov19.network.LoginHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {

int SPLASH_DISPLAY_LENGTH = 4000;
int SPLASH_animation_LENGTH = 2000;
int counter = 0 ;
    int counter2 = 0 ;
@BindView(R.id.progImage)
ImageView progImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splashscreen);


        ButterKnife.bind(this, this);
        counter =0;



//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        String tkn = FirebaseInstanceId.getInstance().getToken();
//        Toast.makeText(SplashScreen.this, "Current token ["+tkn+"]", Toast.LENGTH_LONG).show();
        Log.d("App", "Token [" + tkn + "]");

        FirebaseMessaging.getInstance().subscribeToTopic("allDevices").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });




        progImage.setBackgroundResource(R.drawable.progress_image);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) progImage.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();


        RelativeLayout relateSplash = (RelativeLayout) findViewById(R.id.relateSplash);
        relateSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if(counter > 1)
                {
                    counter =0;
                    Intent intent = new Intent(SplashscreenActivity.this, informaticActivity.class);
                    startActivity(intent);
                }
            }
        });



//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                progImage.setBackgroundResource(R.drawable.ic_20sec);
//                Log.d("MADAATA", counter2+"CHECK FOR LEAKAGE again");
//            }
//        }, SPLASH_animation_LENGTH);
//
////        animateImage();
//
//

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               if(COVSettings.getInstance().getLanguage().trim().equals("CHAINA"))
               {
                   Intent intent = new Intent(SplashscreenActivity.this, changeLanguage.class);
                   startActivity(intent);
               }
               else {

                   setLanguageForApp(COVSettings.getInstance().getLanguage().trim());

                   Intent intent = new Intent(SplashscreenActivity.this, informaticActivity.class);
                   startActivity(intent);
               }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void setLanguageForApp(String languageToLoad){
        Locale locale;
        if(languageToLoad.equals("English")){ //use any value for default
            locale = Locale.getDefault();
        }
        else {
            locale = new Locale("ne");
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
