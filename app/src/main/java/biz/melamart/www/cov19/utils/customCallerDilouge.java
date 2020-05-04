package biz.melamart.www.cov19.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;

public class customCallerDilouge extends Dialog {

    public Activity c;
    emergencyContacts emergencyContacts;
    Spinner edVendor;
    RadioButton tel1,tel2,tel3;
    ImageView dialOk, dialNok;
    public customCallerDilouge(Activity a, emergencyContacts emergencyContacts) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.emergencyContacts = emergencyContacts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.caller_dilouge_layout);

        tel1 = (RadioButton) findViewById(R.id.radio0);
        tel2 = (RadioButton) findViewById(R.id.radio1);
        tel3 = (RadioButton) findViewById(R.id.radio2);

        dialOk= (ImageView) findViewById(R.id.dialOk);
        dialNok= (ImageView) findViewById(R.id.dialNOk);

        tel1.setText(emergencyContacts.getTelephone1());
        tel2.setText(emergencyContacts.getTelephone2());
        tel3.setText(emergencyContacts.getTelephone3());

tel1.setChecked(true);
        if(emergencyContacts.getTelephone2().trim().equals(""))
        {
            tel2.setVisibility(View.GONE);

        }


        if(emergencyContacts.getTelephone3().trim().equals(""))
        {
tel3.setVisibility(View.GONE);
        }

        dialNok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        dialOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telno = emergencyContacts.getTelephone1();
                if(tel2.isChecked())
                {
                    telno = emergencyContacts.getTelephone2();
                }
                else if (tel3.isChecked())
                {
                    telno = emergencyContacts.getTelephone3();
                }

                dialNumber(telno);
                            }
        });


    }

    public void dialNumber(String phone)
    {
        Toast.makeText(c, "Dialing "+ phone, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" +phone));
        c.startActivity(intent);
    }
}





