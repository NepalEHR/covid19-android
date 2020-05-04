package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.helperClass.newsAdapter;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.news;
import biz.melamart.www.cov19.models.newsFeeds.newsFeed;
import biz.melamart.www.cov19.network.FeedDataHandler;
import biz.melamart.www.cov19.network.emergencyContactHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class feedFragment extends Fragment  implements ViewUpdateListener {

    private ProgressDialog progressDialog;

    @BindView(R.id.shimmer_view_container)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;


    newsAdapter mAdapter;
    private List<newsFeed> newsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.news_feed_fragment_layout, container, false);

        ButterKnife.bind(this, view);

        mAdapter = new newsAdapter(newsList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);

                        getFeedList();
                    }
                }
        );
        getFeedList();
        return view;


    }


    private void getFeedList() {
          FeedDataHandler feedDataHandler = new FeedDataHandler(getActivity(), this);
        feedDataHandler.getFeedData();
    }


    private void populateData() {
        newsList.clear();
        if (COVSettings.getInstance().getNewsFeedResponse() != null) {
            newsList.addAll(COVSettings.getInstance().getNewsFeedResponse().getResource());
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void success() {
        populateData();
        mySwipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
    }

    @Override
    public void failure(String msg) {
        populateData();
        progressDialog.hide();
    }
}