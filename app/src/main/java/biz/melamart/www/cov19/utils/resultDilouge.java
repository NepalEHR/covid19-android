package biz.melamart.www.cov19.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.database.contactDatabase;
import biz.melamart.www.cov19.database.suspectDatabase;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.suspect.suspectContact;
import biz.melamart.www.cov19.models.suspect.suspectResponse;
import biz.melamart.www.cov19.network.suspectHandler;

public class resultDilouge extends Dialog  implements ViewUpdateListener {
    private static final int REQUEST_CODE = 1;
    private ProgressDialog progressDialog;
    ProgressBar progressBar;
    public Activity c;
    //    Spinner relativeSpinner;
    EditText editName, editAddress, editContact;
    ImageView saveOk, saveNok,phoneContact;
    RelativeLayout relateData;
    TextView tvResult,infoData;
int reData= 0;
    String result= "You are in LOW risk zone";
    biz.melamart.www.cov19.network.suspectHandler suspectHandler;;

    float proresult=0.0f;
    public resultDilouge(Activity a,int data,float proresult) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
this.reData =data;
this.proresult = proresult;
        if(data == 1)
        {
            this.result = "You are in MILD risk zone";
        }
        else  if (data ==2)
        {
            this.result = "You are in HIGH risk zone";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dilouge_result_layout);

        saveOk= (ImageView) findViewById(R.id.saveOk);
        saveNok= (ImageView) findViewById(R.id.saveNOk);
        tvResult= (TextView) findViewById(R.id.tvResult);
        infoData= (TextView) findViewById(R.id.infoData);
        relateData =(RelativeLayout) findViewById(R.id.radioGroup1);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar) ;


        editName  = (EditText) findViewById(R.id.contactName);
        editContact  = (EditText) findViewById(R.id.contactPhone);
        editAddress  = (EditText) findViewById(R.id.contactAddress);

        suspectHandler = new suspectHandler(c,this);

        if(reData != 2)
        {
            tvResult.setTextColor(c.getResources().getColor(R.color.bg_screen2));

            progressBar.setProgress(35);
            progressBar.setProgressDrawable(c.getResources().getDrawable(R.drawable.greenprogress));
            if(reData ==1)
            {

                tvResult.setTextColor(c.getResources().getColor(R.color.bg_screen3));

                progressBar.setProgress(55);
                progressBar.setProgressDrawable(c.getResources().getDrawable(R.drawable.blueprogress));
            }
            infoData.setVisibility(View.VISIBLE);
            relateData.setVisibility(View.GONE);
        }
        else
        {

            progressBar.setProgress(100);
            progressBar.setProgressDrawable(c.getResources().getDrawable(R.drawable.redprogress));
            tvResult.setTextColor(c.getResources().getColor(R.color.bg_screen1));
            infoData.setVisibility(View.GONE);
            relateData.setVisibility(View.VISIBLE);
        }

        tvResult.setText(result);
        saveOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    progressDialog = DialogUtils.showProgressDialog(c, null, c.getString(R.string.msg_progress_posting_suspect), true, false);
                    suspectDatabase suspectDatabase = new suspectDatabase(c);
                    suspectDatabase.insertContact(editName.getText().toString(), editContact.getText().toString(), editAddress.getText().toString(), "Self evaluated, HIGH RISK", editName.getText().toString(), editContact.getText().toString(), true);

                    suspectContact suspectContact = new suspectContact(editName.getText().toString(), editAddress.getText().toString(), editName.getText().toString(), editContact.getText().toString(),"Self evaluated, HIGH RISK", editName.getText().toString(), editContact.getText().toString(), true);
                    suspectResponse suspectResponse = new suspectResponse();
                    suspectResponse.setResource(suspectContact);

                    suspectHandler.postSuspect(suspectResponse);
                }
            }
        });


        saveNok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



    }

    public boolean validateData()
    {
        Boolean validation =false;

        if(editName.getText().toString().isEmpty())
        {
            editName.setError("Name is required type unknown if no name");
        }
        else if(editAddress.getText().toString().isEmpty())
        {
            editAddress.setError("Address is required");
        }
        else if(editContact.getText().toString().isEmpty())
        {
            editContact.setError("Contact is required");
        }

        else
        {
            validation = true;
        }


        return  validation;
    }

    @Override
    public void success() {
        progressDialog.dismiss();
        Toast.makeText(c, "Local Authority has been alerted!!!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void failure(String msg) {

        progressDialog.dismiss();
//        Toast.makeText(c, "Problem in sending alert, please try again!!!!", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}





