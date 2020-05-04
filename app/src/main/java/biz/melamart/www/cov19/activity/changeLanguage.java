package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.utils.COVSettings;
import butterknife.BindView;
import butterknife.ButterKnife;

public class changeLanguage extends AppCompatActivity {
@BindView(R.id.languageSpinner)
    Spinner languageSpinner;

@BindView(R.id.btnOk)
    TextView btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_language);

        ButterKnife.bind(this, this);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(changeLanguage.this, languageSpinner.getSelectedItem().toString().trim() + " is selected!!!", Toast.LENGTH_SHORT).show();
                COVSettings.getInstance().setLanguage(languageSpinner.getSelectedItem().toString().trim());
                Intent intent = new Intent(changeLanguage.this, informaticActivity.class);
                startActivity(intent);
            }
        });

    }
}
