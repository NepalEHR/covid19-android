package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.helperClass.contactTraceAdapter;
import biz.melamart.www.cov19.helperClass.faqAdapter;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.models.faq;
import butterknife.BindView;
import butterknife.ButterKnife;

public class faqActivity extends AppCompatActivity {
    @BindView(R.id.faqRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    faqAdapter mAdapter;
    private List<faq> faqList = new ArrayList<>();

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faq);

        ButterKnife.bind(this, this);


        mAdapter = new faqAdapter(faqList, this);
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
                        getFaqList();
                    }
                }
        );

//        prepareNewsData();
        getFaqList();
    }

    public void getFaqList()
    {
        faqList.clear();
        faq faqs = new faq();
        faqs.setFaqQuestion("What are the symptoms?");
        faqs.setFaqAnswer("High Temperature, sore throat, cough, difficulty breathing, muscle ache are the symptoms");
        faqList.add(faqs);

        faqs = new faq();
        faqs.setFaqQuestion("Should I go to hospital as soon as I get symptoms?");
        faqs.setFaqAnswer("No first you should quarantine yourself and see if your immunity heal itself. Do not rush to hospital.");
        faqList.add(faqs);


        faqs = new faq();
        faqs.setFaqQuestion("How can I consult to doctor?");
        faqs.setFaqAnswer("Use government provided hospital lists and try to get consultation");
        faqList.add(faqs);


        faqs = new faq();
        faqs.setFaqQuestion("How to travel in emergency in lockdown?");
        faqs.setFaqAnswer("Contact police headquarter or neares police station to ask for permit to travel. Only for genuine reason will be permitted to travel");
        faqList.add(faqs);

        mAdapter.notifyDataSetChanged();

    }
}
