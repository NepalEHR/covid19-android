package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.webView1)
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this, this);
        String url = "https://www.mohp.gov.np/en/";

        Intent intent = getIntent();
        String newsUrl = intent.getExtras().getString("newsUrl");
        if(!newsUrl.trim().equals(""))
        {
            url = newsUrl;
        }

        web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(url);
        web.setWebViewClient(new myWebClient());
        web.setWebChromeClient(new WebChromeClient());
        setContentView(web);


    }


    public class myWebClient extends WebViewClient
    {
        private ProgressDialog progressDialog;
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressDialog = DialogUtils.showProgressDialog(WebActivity.this, null, getString(R.string.msg_progress_fetching_data), true, false);
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

//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(event.getAction() == KeyEvent.ACTION_DOWN){
//            switch(keyCode)
//            {
//                case KeyEvent.KEYCODE_BACK:
//                    if(web.canGoBack()){
//                        web.goBack();
//                    }
//                    else
//                    {
//                        finish();
//                    }
//                    return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
