package biz.melamart.www.cov19.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.database.contactDatabase;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class contactDilouge extends Dialog {
    private static final int REQUEST_CODE = 1;

    public Activity c;
//    Spinner relativeSpinner;
    EditText editName, editAddress, editContact;

    ImageView saveOk, saveNok,phoneContact;
    public contactDilouge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dilouge_contact_dilouge);

        saveOk= (ImageView) findViewById(R.id.saveOk);
        saveNok= (ImageView) findViewById(R.id.saveNOk);
        phoneContact = (ImageView) findViewById(R.id.phoneContact);




        editName  = (EditText) findViewById(R.id.contactName);
        editContact  = (EditText) findViewById(R.id.contactPhone);
        editAddress  = (EditText) findViewById(R.id.contactAddress);
//        relativeSpinner  = (Spinner) findViewById(R.id.spinnerRelative);

        phoneContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "Directing toward contact diary", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                c.startActivityForResult(intent, REQUEST_CODE);
            }
        });



        saveOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    contactDatabase contactDatabase = new contactDatabase(c);
                    contactDatabase.insertContact(editName.getText().toString(), editContact.getText().toString(), editAddress.getText().toString(), "");
                    Toast.makeText(c, "New Contact Added!", Toast.LENGTH_SHORT).show();
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
            editName.setError("Name is required ");
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


}





