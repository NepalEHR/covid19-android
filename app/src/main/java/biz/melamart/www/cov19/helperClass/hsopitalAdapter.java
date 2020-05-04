package biz.melamart.www.cov19.helperClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;

import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.WebActivity;
import biz.melamart.www.cov19.models.hospitalData.hospitalData;
import biz.melamart.www.cov19.models.newsFeeds.newsFeed;
import biz.melamart.www.cov19.utils.DialogUtils;
import biz.melamart.www.cov19.utils.GeneralUtils;

public class hsopitalAdapter  extends RecyclerView.Adapter<hsopitalAdapter.MyViewHolder> {

    private List<hospitalData> hospitalDataList;
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hospitalName, screenText, positiveText,negativeText,qurantineTotal,isolationText,ppeText;

        public MyViewHolder(View view) {
            super(view);
            hospitalName = (TextView) view.findViewById(R.id.hospitalName);
            screenText = (TextView) view.findViewById(R.id.screenText);
            positiveText = (TextView) view.findViewById(R.id.positiveText);
            negativeText = (TextView) view.findViewById(R.id.negativeText);
            qurantineTotal = (TextView) view.findViewById(R.id.qurantineTotal);
            isolationText = (TextView) view.findViewById(R.id.isolationText);
            ppeText = (TextView) view.findViewById(R.id.ppeText);
        }
    }


    public hsopitalAdapter(List<hospitalData> hospitalDataList,Context mContext) {
        this.hospitalDataList = hospitalDataList;
        this.mContext = mContext;
    }

    @Override
    public hsopitalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospital_data_item, parent, false);

        return new hsopitalAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(hsopitalAdapter.MyViewHolder holder, int position) {
        hospitalData hospitalData = hospitalDataList.get(position);
        holder.hospitalName.setText(hospitalData.getHospitalName());
        holder.screenText.setText(hospitalData.getScreened()+"");
        holder.negativeText.setText(hospitalData.getNegativeCase()+"");
        holder.positiveText.setText(hospitalData.getPositiveCase()+"");
    holder.qurantineTotal.setText(hospitalData.getQurantined()+"");
        holder.isolationText.setText(hospitalData.getIsolated()+"");
        holder.ppeText.setText(hospitalData.getUsedppe()+"");//+"/"+hospitalData.getTotalppe());
        


    }

    @Override
    public int getItemCount() {
        return hospitalDataList.size();
    }
}
