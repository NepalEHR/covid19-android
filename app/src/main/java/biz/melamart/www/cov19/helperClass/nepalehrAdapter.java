package biz.melamart.www.cov19.helperClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.WebActivity;
import biz.melamart.www.cov19.models.hospitalData.hospitalData;

public class nepalehrAdapter  extends RecyclerView.Adapter<nepalehrAdapter.MyViewHolder> {

    private List<hospitalData> hospitalDataList;
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hospitalName, screenText,  positiveText,negativeText; //sampleText,suspectedText,
        RelativeLayout hosRelative;
        //
//,qurantineTotal,isolationText
        public MyViewHolder(View view) {
            super(view);
            hospitalName = (TextView) view.findViewById(R.id.hospitalName);
            screenText = (TextView) view.findViewById(R.id.screenText);

//            sampleText = (TextView) view.findViewById(R.id.sampleText);
//            suspectedText = (TextView) view.findViewById(R.id.suspectedText);

            positiveText = (TextView) view.findViewById(R.id.positiveText);
            negativeText = (TextView) view.findViewById(R.id.negativeText);

            hosRelative = (RelativeLayout) view.findViewById(R.id.hosRelative);
        }
    }


    public nepalehrAdapter(List<hospitalData> hospitalDataList,Context mContext) {
        this.hospitalDataList = hospitalDataList;
        this.mContext = mContext;
    }

    @Override
    public nepalehrAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospita_data_item_nehr, parent, false);

        return new nepalehrAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(nepalehrAdapter.MyViewHolder holder, int position) {
        hospitalData hospitalData = hospitalDataList.get(position);
        holder.hospitalName.setText(hospitalData.getHospitalName());
        holder.screenText.setText(hospitalData.getSuspect()+"");
//        holder.suspectedText.setText(hospitalData.getSuspect()+"");
//        holder.sampleText.setText(hospitalData.getSamCol()+"");
        holder.positiveText.setText(hospitalData.getPositiveCase()+"");
        holder.negativeText.setText(hospitalData.getNegativeCase()+"");
        holder.hosRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("newsUrl",hospitalData.getDashUrl());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hospitalDataList.size();
    }
}
