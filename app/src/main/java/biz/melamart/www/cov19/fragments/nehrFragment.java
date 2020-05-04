package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.WebActivity;
import biz.melamart.www.cov19.helperClass.nepalehrAdapter;
import biz.melamart.www.cov19.interfaces.hospitalInterface;
import biz.melamart.www.cov19.models.hospitalData.hospitalData;
import biz.melamart.www.cov19.models.nepalehr.Nepalehr;
import biz.melamart.www.cov19.network.hospitalHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class nehrFragment  extends Fragment {
    @BindView(R.id.webView1)
    WebView web;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.nepaehr_dash, container, false);
        ButterKnife.bind(this, view);
//        String url = "http://covid19.nepalehr.org/public/dashboards/LSVYIrmKepOqM1rdN73Ziw1pJT4hnTs2nljPk0Gr?org_slug=default";

        String url = "http://covid19.nepalehr.org/public/dashboards/cjv44gAgY9NjWc1HzRQ0L3R5b3S8G6S4HqaACqMD?org_slug=default";
        web.loadUrl(url);

        // Enable Javascript
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        web.setWebViewClient(new myWebClient());


//        web = new WebView(getContext());
//        web.getSettings().setJavaScriptEnabled(true);
//        web.loadUrl(url);
//        web.setWebViewClient(new myWebClient());
//        web.setWebChromeClient(new WebChromeClient());
//        getContext().setContentView(web);
        return view;


    }

    public class myWebClient extends WebViewClient
    {
        private ProgressDialog progressDialog;
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressDialog.dismiss();


        }
    }

    //flip screen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }





}
