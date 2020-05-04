package biz.melamart.www.cov19.helperClass;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.WebActivity;
import biz.melamart.www.cov19.models.news;
import biz.melamart.www.cov19.models.newsFeeds.newsFeed;
import biz.melamart.www.cov19.utils.DialogUtils;
import biz.melamart.www.cov19.utils.GeneralUtils;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.MyViewHolder> {

    private List<newsFeed> newsList;
Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle, newsDescription, newsProvider,newsDate;
        public ImageView newsPortalIcon,share;
        public CardView newsFeedCard;

        public MyViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            newsDescription = (TextView) view.findViewById(R.id.newsDescription);
            newsProvider = (TextView) view.findViewById(R.id.newsProvider);
            newsPortalIcon = (ImageView) view.findViewById(R.id.newsPortalIcon);
            share = (ImageView) view.findViewById(R.id.share);
            newsFeedCard = (CardView) view.findViewById(R.id.newsFeeds);
            newsDate = (TextView) view.findViewById(R.id.newsDate);
        }
    }


    public newsAdapter(List<newsFeed> newsList,Context mContext) {
        this.newsList = newsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_placement, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        newsFeed news = newsList.get(position);
        holder.newsTitle.setText(news.getFeedTitle());

        int maxlength = 200;
        if(news.getFeedDescription().length() >maxlength)
        {
            String s =news.getFeedDescription();
            String desc = s.substring(0, Math.min(s.length(), maxlength-3))+"...";
//            holder.newsDescription.setText(news.getFeedDescription());
            holder.newsDescription.setText(desc);
        }


        holder.newsProvider.setText(news.getSource());
        DialogUtils.showCircleGlideProgressDialog(mContext, null, news.getIconImage(), holder.newsPortalIcon, GeneralUtils.getImageWidth(), GeneralUtils.getImageHeight(), Priority.IMMEDIATE);


        String dates = news.getDateAdded();

       String myDate= GeneralUtils.getConvertedDate(dates);
       holder.newsDate.setText(myDate);
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,news.getFeedTitle());
                i.putExtra(android.content.Intent.EXTRA_TEXT, news.getFullLink());
                mContext.startActivity(Intent.createChooser(i,"Share via"));
            }
        });


        holder.newsFeedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("newsUrl", news.getFullLink());
                 mContext.startActivity(intent);
            }
        });
        holder.newsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("newsUrl", news.getFullLink());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
