package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.database.contactDatabase;
import biz.melamart.www.cov19.helperClass.contactAdapter;
import biz.melamart.www.cov19.helperClass.contactTraceAdapter;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.utils.DialogUtils;
import biz.melamart.www.cov19.utils.GeneralUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class contactTraceActivity extends AppCompatActivity {
    @BindView(R.id.contactRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;


    @BindView(R.id.fabShared)
    FloatingActionButton fabShared;

    contactTraceAdapter mAdapter;
    private List<emergencyContacts> contactsList = new ArrayList<>();

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contact_trace);

        ButterKnife.bind(this, this);


        mAdapter = new contactTraceAdapter(contactsList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        progressDialog = DialogUtils.showProgressDialog(this, null, getString(R.string.msg_progress_fetching_data), true, false);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        getContactList();
                    }
                }
        );

        fabShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "COV 19 contacts.csv";//like 2016_01_12.txt
                boolean exorted = exportData(fileName);
                if(exorted)
                {
                    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                    String filePath = baseDir + File.separator + fileName;
                    Toast.makeText(contactTraceActivity.this, "Succesfully saved in "+ filePath, Toast.LENGTH_LONG).show();
                }
                else
                {

                    Toast.makeText(contactTraceActivity.this, "Failure to save data. Please contact developer-sudishrestha@gmail.com", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        prepareNewsData();
        getContactList();

    }

    public void getContactList() {

        List<emergencyContacts> contactsListDb = new ArrayList<>();
        contactDatabase contactDatabase = new contactDatabase(this);
        contactsListDb = contactDatabase.getTraceContacts();

        for(int i = 0;i < contactsListDb.size(); i++)
        {
            Log.d("CDB DATA",contactsListDb.get(i).getName());
            contactsList.add(contactsListDb.get(i));

        }


        mySwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();

    }

    public boolean exportData(String fileName)
    {

        boolean writeOk =false;
        contactDatabase contactDatabase = new contactDatabase(this);
        List<emergencyContacts> allData = contactDatabase.getTraceContacts();






        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = baseDir + File.separator + fileName;
     try{

         File f = new File(filePath);
         CSVWriter writer;
         FileWriter mFileWriter;
         // File exist
         if(f.exists()&&!f.isDirectory())
         {
             mFileWriter = new FileWriter(filePath, false);
             writer = new CSVWriter(mFileWriter);
         }
         else
         {
             writer = new CSVWriter(new FileWriter(filePath));
         }

         String[] data = {"id","contact","name","address"};
         writer.writeNext(data);
         for(int i=0;i< allData.size();i++)
         {
             emergencyContacts em = allData.get(i);

             String[] datas ={em.getContactId(),em.getTelephone1(),em.getName(),em.getAddress()};
             writer.writeNext(datas);
         }




         writer.close();
        writeOk = true;
     }
        catch (IOException e) {
        e.printStackTrace();
    }




        return writeOk;

    }
}
