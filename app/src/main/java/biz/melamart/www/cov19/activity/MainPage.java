package biz.melamart.www.cov19.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.fragments.contactFragment;
import biz.melamart.www.cov19.fragments.faqFragment;
import biz.melamart.www.cov19.fragments.feedFragment;
import biz.melamart.www.cov19.fragments.homeFragment;
import biz.melamart.www.cov19.fragments.hospitalFragment;
import biz.melamart.www.cov19.fragments.moreFragment;
import biz.melamart.www.cov19.fragments.nehrFragment;
import biz.melamart.www.cov19.fragments.statFragment;
import biz.melamart.www.cov19.helperClass.CurvedBottomNavigationView;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.login.LoginRequest;
import biz.melamart.www.cov19.network.LoginHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.contactDilouge;
import biz.melamart.www.cov19.utils.suspectDilouge;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MainPage extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener, ViewUpdateListener {
    FragmentTransaction transaction;
    contactDilouge contactDilouge;
    suspectDilouge suspectDilouge;
    private static final int REQUEST_CODE = 1;
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;
    private LinearLayout layoutFabLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_page);
//        getSession();

        CurvedBottomNavigationView mView = findViewById(R.id.customBottomBar);
        mView.setItemIconTintList(null);
//        navigationView.setItemIconTintList(null);
        mView.inflateMenu(R.menu.bottom_menu);
        mView.setSelectedItemId(R.id.action_home);
        //getting bottom navigation view and attaching the listener
        mView.setOnNavigationItemSelectedListener(MainPage.this);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,  new homeFragment());
        transaction.addToBackStack(null);
        transaction.commit();


        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);

        layoutFabSave = (LinearLayout) this.findViewById(R.id.layoutFabSave);
         contactDilouge = new contactDilouge(MainPage.this);
        layoutFabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contactDilouge.show();
            }
        });

        layoutFabEdit = (LinearLayout) this.findViewById(R.id.layoutFabEdit);
        suspectDilouge = new suspectDilouge(MainPage.this);
        layoutFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                suspectDilouge.show();
            }
        });

        layoutFabLanguage = (LinearLayout) this.findViewById(R.id.layoutFabLanguage);
        layoutFabLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                COVSettings.getInstance().toggleLanguage();

                String languageToLoad  = "en"; // your language
                if(COVSettings.getInstance().getLanguage().trim().equals("Nepali"))
                {
                    languageToLoad  = "ne"; // your language
                }

                Resources res = getResources();
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();

                if (Build.VERSION.SDK_INT >= 17) {
                    config.setLocale(locale);
                } else {
                    config.locale = locale;
                }
                res.updateConfiguration(config, res.getDisplayMetrics());
                recreate();
            }
        });
        //layoutFabSettings = (LinearLayout) this.findViewById(R.id.layoutFabSettings);

        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();
    }


    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSave.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        layoutFabLanguage.setVisibility(View.INVISIBLE);
//        fabSettings.setImageResource(R.drawable.ic_settings_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSave.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        layoutFabLanguage.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
//        fabSettings.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {

            case R.id.action_dash:
                transaction.replace(R.id.frame_container, new hospitalFragment());
                break;
            case R.id.action_feeds:
                transaction.replace(R.id.frame_container, new faqFragment());
                break;
            case R.id.action_home:
                transaction.replace(R.id.frame_container, new homeFragment());
                break;
            case R.id.action_contact:
                transaction.replace(R.id.frame_container, new contactFragment());
                break;
            case R.id.action_more:
                transaction.replace(R.id.frame_container, new moreFragment());
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }

    @Override
    public void success() {

    }

    @Override
    public void failure(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);


                contactDilouge.show();
                contactDilouge.updateData(name,number);
//                Log.d(TAG, "ZZZ number : " + number + " , name : " + name);



            }
        }
    };
}
