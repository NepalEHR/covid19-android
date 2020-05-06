package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.statActivity;
import biz.melamart.www.cov19.helperClass.hsopitalAdapter;
import biz.melamart.www.cov19.helperClass.nepalehrAdapter;
import biz.melamart.www.cov19.helperClass.newsAdapter;
import biz.melamart.www.cov19.interfaces.statViewUpdateListner;
import biz.melamart.www.cov19.models.countryStat.countryStat;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.models.hospitalData.hospitalData;
import biz.melamart.www.cov19.models.newsFeeds.newsFeed;
import biz.melamart.www.cov19.models.ninja.Ninja;
import biz.melamart.www.cov19.network.statHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class homeFragment extends Fragment implements statViewUpdateListner {

    @BindView(R.id.txtTotalEffected)
    TextView txtTotalEffected;

    @BindView(R.id.txtTotalEffectedRatio)
    TextView txtTotalEffectedRatio;

    @BindView(R.id.txtTotaldeath)
    TextView txtTotaldeath;

    @BindView(R.id.txtTotaldeathRatio)
    TextView txtTotaldeathRatio;

    @BindView(R.id.txtTotalrecovered)
    TextView txtTotalrecovered;

    @BindView(R.id.txtTotalrecoveredRatio)
    TextView txtTotalrecoveredRatio;


    private List<countryStat> statList = new ArrayList<>();
    List<Ninja> ninjaList = new ArrayList<>();
    private ProgressDialog progressDialog;


    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    @BindView(R.id.statsCard)
    CardView statsCard;

    @BindView(R.id.statRelative)
    RelativeLayout statRelative;

//    @BindView(R.id.hospitalData)
//    RecyclerView hospitalData;

    private List<biz.melamart.www.cov19.models.hospitalData.hospitalData> hospitalDataList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_dash_layout, container, false);
        ButterKnife.bind(this, view);
        progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);
        getCountryData();

        statsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
//                Toast.makeText(getActivity(), "STAT DATA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), statActivity.class);
                getActivity().startActivity(intent);

            }
        });

//        mAdapter = new hsopitalAdapter(hospitalDataList,getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        hospitalData.setLayoutManager(mLayoutManager);
//        hospitalData.setItemAnimator(new DefaultItemAnimator());
//        hospitalData.setAdapter(mAdapter);
//        hospitalData.setNestedScrollingEnabled(false);
//        fillHospitalData();
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        getCountryData();
                    }
                }
        );
        return view;


    }
//    public void fillHospitalData()
//    {
//        hospitalData hospitalData = new hospitalData();
//        hospitalData.setHospitalName("Bayalpata Hospital");
//        hospitalDataList.add(hospitalData);
//
//        hospitalData = new hospitalData();
//        hospitalData.setHospitalName("Charikot Hospital");
//        hospitalDataList.add(hospitalData);
//
//        hospitalData = new hospitalData();
//        hospitalData.setHospitalName("Chaurmandu Hospital");
//        hospitalDataList.add(hospitalData);
//
//        mAdapter.notifyDataSetChanged();
//
//    }
    public  void getCountryData()
    {

        statHandler statHandler = new statHandler(getActivity(),this);
//        statHandler.getFullData();
        statHandler.getNinjaData();


    }
    @Override
    public void success() {
//        statList.clear();
//        if (COVSettings.getInstance().getCountryStatResponse() != null) {
//            statList.addAll(COVSettings.getInstance().getCountryStatResponse().getResource());
//        } fillFirst();


                ninjaList.clear();
        if (COVSettings.getInstance().getNinjaData() != null) {
            ninjaList.addAll(COVSettings.getInstance().getNinjaData());
        } fillFirst();

        progressDialog.dismiss();
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failure(String msg) {

        progressDialog.dismiss();
    }

    public void fillFirst()
    {
        int totalCases = 0;
        int totalDeath = 0;
        int totalRecovered = 0;

        for(int i =0;i < ninjaList.size();i++) {
            totalCases = totalCases + ninjaList.get(i).getCases();
             totalDeath = totalDeath + ninjaList.get(i).getDeaths();
             totalRecovered = totalRecovered + ninjaList.get(i).getRecovered();

        }

        int totalInActiveCase = totalDeath + totalRecovered;

        float er = ((totalCases/ 7000000000f)*100);
        float dr = ((Float.parseFloat(totalDeath+"")/ totalCases)*100f);
        float rr = ((Float.parseFloat(totalRecovered+"")/ totalCases)*100f);

        txtTotalEffected.setText(totalCases+"");
        txtTotaldeath.setText(totalDeath+"");
        txtTotalrecovered.setText(totalRecovered+"");
        txtTotalEffectedRatio.setText(String.format("%.02f", er)+"%\n("+getResources().getString(R.string.statSubTitle1)+")");
        txtTotaldeathRatio.setText(String.format("%.02f", dr)+"%\n("+getResources().getString(R.string.statSubTitle1)+")");
        txtTotalrecoveredRatio.setText(String.format("%.02f", rr)+"%\n("+getResources().getString(R.string.statSubTitle1)+")");

//        for(int i =0;i < statList.size();i++)
//        {
//
//            countryStat countryStat = statList.get(i);
////            if(i == 1  )
////            {
////                r1c1.setText(i+"");
////                r1c2.setText(countryStat.getRegion());
////                r1c3.setText(countryStat.getConfirmed());
////                r1c4.setText(countryStat.getDeath());
////                r1c5.setText(countryStat.getRecovered());
////            }
////            else if( i == 2  )
////            {
////                r2c1.setText(i+"");
////                r2c2.setText(countryStat.getRegion());
////                r2c3.setText(countryStat.getConfirmed());
////                r2c4.setText(countryStat.getDeath());
////                r2c5.setText(countryStat.getRecovered());
////
////            }
////            else if( i == 3   )
////            {
////
////                r3c1.setText(i+"");
////                r3c2.setText(countryStat.getRegion());
////                r3c3.setText(countryStat.getConfirmed());
////                r3c4.setText(countryStat.getDeath());
////                r3c5.setText(countryStat.getRecovered());
////            }
////            else if( countryStat.getRegion().trim().toLowerCase().equals("nepal") )
////            {
////
////                r4c1.setText(i+"");
////                r4c2.setText(countryStat.getRegion());
////                r4c3.setText(countryStat.getConfirmed());
////                r4c4.setText(countryStat.getDeath());
////                r4c5.setText(countryStat.getRecovered());
////            }
//
//
//            if(countryStat.getRegion().trim().toLowerCase().equals("world"))
//            {
//
//                float totalResolved = Float.parseFloat(countryStat.getDeath().trim()) + Float.parseFloat(countryStat.getRecovered().trim());
//
//                float er = (Integer.parseInt(countryStat.getConfirmed().trim())/7000000000f) *100;
//                float dr = (Integer.parseInt(countryStat.getDeath().trim())/totalResolved) *100;
//                float rr = (Integer.parseInt(countryStat.getRecovered().trim())/totalResolved) *100;
//
//
////        Toast.makeText(getActivity(), dr+""+Integer.parseInt(countryStat.getDeath().trim()), Toast.LENGTH_SHORT).show();
//                txtTotalEffected.setText(countryStat.getConfirmed());
//                txtTotalEffectedRatio.setText( String.format("%.02f", er)+"%");
//                txtTotaldeath.setText(countryStat.getDeath());
//                txtTotaldeathRatio.setText( String.format("%.02f", dr)+"%");
//                txtTotalrecovered.setText(countryStat.getRecovered());
//                txtTotalrecoveredRatio.setText( String.format("%.02f", rr)+"%");
//
//            }
//        }



    }
}