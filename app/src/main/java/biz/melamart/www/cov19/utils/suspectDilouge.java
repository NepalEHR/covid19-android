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
import android.widget.Toast;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.database.contactDatabase;
import biz.melamart.www.cov19.database.suspectDatabase;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.suspect.suspectContact;
import biz.melamart.www.cov19.models.suspect.suspectResponse;
import biz.melamart.www.cov19.network.suspectHandler;

public class suspectDilouge extends Dialog implements ViewUpdateListener {
    private static final int REQUEST_CODE = 1;

    public Activity c;
    //    Spinner relativeSpinner;
    EditText editName, editAddress, editContact,editDescription,editReport,editReportContact;

    private ProgressDialog progressDialog;
    suspectHandler suspectHandler;
    ImageView saveOk, saveNok,phoneContact;
    public suspectDilouge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dilouge_suspect_dilouge);

        saveOk= (ImageView) findViewById(R.id.saveOk);
        saveNok= (ImageView) findViewById(R.id.saveNOk);
        phoneContact = (ImageView) findViewById(R.id.phoneContact);


suspectHandler = new suspectHandler(c,this);

        editName  = (EditText) findViewById(R.id.contactName);
        editContact  = (EditText) findViewById(R.id.contactPhone);
        editAddress  = (EditText) findViewById(R.id.contactAddress);
        editDescription  = (EditText) findViewById(R.id.description);
        editReport  = (EditText) findViewById(R.id.reportingPerson);
        editReportContact  = (EditText) findViewById(R.id.reportingContact);
//        relativeSpinner  = (Spinner) findViewById(R.id.spinnerRelative);

//        phoneContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(c, "Directing toward contact diary", Toast.LENGTH_SHORT).show();
//                Uri uri = Uri.parse("content://contacts");
//                Intent intent = new Intent(Intent.ACTION_PICK, uri);
//                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//                c.startActivityForResult(intent, REQUEST_CODE);
//            }
//        });



        saveOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(validateData()) {
                        progressDialog = DialogUtils.showProgressDialog(c, null, c.getString(R.string.msg_progress_posting_suspect), true, false);
                        suspectDatabase suspectDatabase = new suspectDatabase(c);
                        suspectDatabase.insertContact(editName.getText().toString(), editContact.getText().toString(), editAddress.getText().toString(), editDescription.getText().toString(), editReport.getText().toString(), editReportContact.getText().toString(), true);

                        suspectContact suspectContact = new suspectContact(editName.getText().toString(), editAddress.getText().toString(), editReport.getText().toString(), editContact.getText().toString(), editDescription.getText().toString(), editReport.getText().toString(), editReportContact.getText().toString(), true);
                        suspectResponse suspectResponse = new suspectResponse();
                        suspectResponse.setResource(suspectContact);

                        suspectHandler.postSuspect(suspectResponse);


                        Toast.makeText(c, "New suspect Added!", Toast.LENGTH_SHORT).show();
                        dismiss();
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
        else if(editDescription.getText().toString().isEmpty())
        {
            editDescription.setError("Description is required");
        }
        else if(editReport.getText().toString().isEmpty())
        {
            editReport.setError("Reporting person is required");
        }
        else if(editReportContact.getText().toString().isEmpty())
        {
            editReportContact.setError("Reporting person's contact is required");
        }
        else
        {
            validation = true;
        }


        return  validation;
    }

    public void updateData(String name, String number)
    {
        editName.setText(name);
        editContact.setText(number);
    }


    @Override
    public void success() {
        progressDialog.dismiss();
    }

    @Override
    public void failure(String msg) {
        progressDialog.dismiss();
//        Toast.makeText(c, "Suspect Report failed!!!" + msg, Toast.LENGTH_LONG).show();
    }
}





