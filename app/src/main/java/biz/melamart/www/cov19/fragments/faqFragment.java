package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.helperClass.faqAdapter;
import biz.melamart.www.cov19.helperClass.newsAdapter;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.faq;
import biz.melamart.www.cov19.models.newsFeeds.newsFeed;
import biz.melamart.www.cov19.network.FeedDataHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class faqFragment extends Fragment  {

    private ProgressDialog progressDialog;

    @BindView(R.id.shimmer_view_container)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;


    faqAdapter mAdapter;

    private List<faq> faqList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.news_feed_fragment_layout, container, false);

        ButterKnife.bind(this, view);

        mAdapter = new faqAdapter(faqList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(false);

//                        getFaqList();
                    }
                }
        );
        getFaqList();
        return view;


    }

    public void getFaqList()
    {
        faqList.clear();
        faq faqs = new faq();
        faqs.setFaqQuestion("What are the symptoms?");
        faqs.setFaqAnswer("High Temperature, sore throat, cough, difficulty breathing, muscle ache are the symptoms");
        faqList.add(faqs);

        faqs = new faq();
        faqs.setFaqQuestion("Should i go to hospital as soon as i get symptoms?");
        faqs.setFaqAnswer("No first you should qurantine yourself and see if your immunity heal itself. Dont rush to hospital.");
        faqList.add(faqs);


        faqs = new faq();
        faqs.setFaqQuestion("How can i consult to doctor?");
        faqs.setFaqAnswer("Use government provided number 1133 and 1125 to get consultation");
        faqList.add(faqs);


        faqs = new faq();
        faqs.setFaqQuestion("How to travel in emergency in lockdown?");
        faqs.setFaqAnswer("Contact police headquarter or neares police station to ask for permit to travel. Only for genuine reason will be permitted to travel");
        faqList.add(faqs);

        mAdapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }

}