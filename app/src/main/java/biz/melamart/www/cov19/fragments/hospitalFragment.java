package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.helperClass.hsopitalAdapter;
import biz.melamart.www.cov19.helperClass.nepalehrAdapter;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.interfaces.hospitalInterface;
import biz.melamart.www.cov19.interfaces.statViewUpdateListner;
import biz.melamart.www.cov19.models.countryStat.countryStat;
import biz.melamart.www.cov19.models.hospitalData.hospitalData;
import biz.melamart.www.cov19.models.nepalehr.Nepalehr;
import biz.melamart.www.cov19.network.emergencyContactHandler;
import biz.melamart.www.cov19.network.hospitalHandler;
import biz.melamart.www.cov19.network.statHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class hospitalFragment extends Fragment implements hospitalInterface {


    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    @BindView(R.id.hospitalData)
    RecyclerView hospitalData;
    Boolean isFirst = true;
    Boolean completeResponse = false;
    int counter = 0;
//    hsopitalAdapter mAdapter;

    nepalehrAdapter mAdapter;
    private List<biz.melamart.www.cov19.models.hospitalData.hospitalData> hospitalDataList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_hospital_layout, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new nepalehrAdapter(hospitalDataList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        hospitalData.setLayoutManager(mLayoutManager);
        hospitalData.setItemAnimator(new DefaultItemAnimator());
        hospitalData.setAdapter(mAdapter);
//        hospitalData.setNestedScrollingEnabled(false);
        progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);
        fillHospitalData();
        getHospitalList();
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        isFirst = true;
                        completeResponse = false;
                        counter = 0;
                        mySwipeRefreshLayout.setRefreshing(true);
                        getHospitalList();
                    }
                }
        );
        return view;


    }

    private void getHospitalList() {

        hospitalDataList.clear();
        hospitalHandler hospitalHandler = new hospitalHandler(getActivity(), this);
//        hospitalHandler.getFullData();
        hospitalHandler.getBayalpataData();
        hospitalHandler.getDolakhaData();
        hospitalHandler.getChaurmanduData();

    }

    public void fillHospitalData() {
        biz.melamart.www.cov19.models.hospitalData.hospitalData hospitalData = new hospitalData();
        hospitalData.setHospitalName("Bayalpata Hospital");

        hospitalDataList.add(hospitalData);


        hospitalData = new hospitalData();
        hospitalData.setHospitalName("Charikot Hospital");
        hospitalDataList.add(hospitalData);

        hospitalData = new hospitalData();
        hospitalData.setHospitalName("Chaurpati PHC");
        hospitalDataList.add(hospitalData);

        mAdapter.notifyDataSetChanged();

    }

    private void populateData() {
        hospitalDataList.clear();
        if (COVSettings.getInstance().getHospitalResponse() != null) {
            hospitalDataList.addAll(COVSettings.getInstance().getHospitalResponse().getResource());
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


        mySwipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
    }

    private void populateEhrData() {

        if (COVSettings.getInstance().getBayalpataData() != null) {
            Nepalehr nepalehr = COVSettings.getInstance().getBayalpataData();
            hospitalData hosData = new hospitalData();
            hosData.setHospitalName("Bayalpata Hospital");
            hosData.setDashUrl("http://covid19.nepalehr.org/public/dashboards/cjv44gAgY9NjWc1HzRQ0L3R5b3S8G6S4HqaACqMD?org_slug=default");

//            int total = extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(1).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(2).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(3).getNumbers());

            int positive = extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(3).getNumbers());

            int negative = extractData(nepalehr.getQueryResult().getData().getRows().get(1).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(4).getNumbers());
            int resultWaiting = extractData(nepalehr.getQueryResult().getData().getRows().get(2).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(5).getNumbers());
            int total = positive + negative + resultWaiting;

//            hosData.setScreened(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfScreenedPatient()));
//            hosData.setUnIsoBed(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfUnOccupiedIsolationBeds()));
//            hosData.setIsolated(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfIsolatedInHospital()));
//            hosData.setSamCol(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfSampleCollected()));
//            hosData.setRef(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfReferredToOtherHospital()));
            hosData.setSuspect(total + "");
            hosData.setPositiveCase(positive + "");
            hosData.setNegativeCase(negative + "");
            hosData.setResultWaiting(resultWaiting+"");



            hospitalDataList.add(hosData);
        }
        mAdapter.notifyDataSetChanged();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private int extractData(String response ) {
        int data = 0;

        if (isInteger(response)) {
            data = Integer.parseInt(response);
        } else {
            try {
                String s = response.substring(response.indexOf(">") + 1);

                s = s.substring(0, s.indexOf("<"));

                if(isInteger(s.trim()))
                {
                    data = Integer.parseInt(s);
                }
                else {
                    String[] tData = s.split("/");
                    data = Integer.parseInt(tData[1].trim());
                }
            } catch (Exception ex) {

            }
        }
        return data;
    }

    private void populateDolData() {

        if (COVSettings.getInstance().getDolakhaData() != null) {
            Nepalehr nepalehr = COVSettings.getInstance().getDolakhaData();
            hospitalData hosData = new hospitalData();
            hosData.setHospitalName("Charikot Hospital");
            hosData.setDashUrl("http://covid19.nepalehr.org/public/dashboards/mvUqhwzomnEDBeld4BgJqz3TNCN32adHmH72dbFx?org_slug=default");

            int positive = extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(3).getNumbers());

            int negative = extractData(nepalehr.getQueryResult().getData().getRows().get(1).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(4).getNumbers());
            int resultWaiting = extractData(nepalehr.getQueryResult().getData().getRows().get(2).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(5).getNumbers());
            int total = positive + negative + resultWaiting;

//            hosData.setScreened(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfScreenedPatient()));
//            hosData.setUnIsoBed(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfUnOccupiedIsolationBeds()));
//            hosData.setIsolated(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfIsolatedInHospital()));
//            hosData.setSamCol(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfSampleCollected()));
//            hosData.setRef(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfReferredToOtherHospital()));
            hosData.setSuspect(total + "");
            hosData.setPositiveCase(positive + "");
            hosData.setNegativeCase(negative + "");
            hosData.setResultWaiting(resultWaiting+"");


            hospitalDataList.add(hosData);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void populateCharData() {

        if (COVSettings.getInstance().getChaurmanduData() != null) {
            Nepalehr nepalehr = COVSettings.getInstance().getChaurmanduData();
            hospitalData hosData = new hospitalData();
            hosData.setHospitalName("Chaurpati PHC");
            hosData.setDashUrl("http://covid19.nepalehr.org/public/dashboards/Yv3xoV5kb2JIJ677CaPRMRbXPZe755ALthnBFqVX?org_slug=default");


            int positive = extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(3).getNumbers());

            int negative = extractData(nepalehr.getQueryResult().getData().getRows().get(1).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(4).getNumbers());
            int resultWaiting = extractData(nepalehr.getQueryResult().getData().getRows().get(2).getNumbers()) + extractData(nepalehr.getQueryResult().getData().getRows().get(5).getNumbers());
            int total = positive + negative + resultWaiting;

//            hosData.setScreened(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfScreenedPatient()));
//            hosData.setUnIsoBed(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfUnOccupiedIsolationBeds()));
//            hosData.setIsolated(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfIsolatedInHospital()));
//            hosData.setSamCol(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfSampleCollected()));
//            hosData.setRef(extractData(nepalehr.getQueryResult().getData().getRows().get(0).getNumberOfReferredToOtherHospital()));
            hosData.setSuspect(total + "");
            hosData.setPositiveCase(positive + "");
            hosData.setNegativeCase(negative + "");
            hosData.setResultWaiting(resultWaiting+"");


            hospitalDataList.add(hosData);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void successBayal() {
        counter++;
        populateEhrData();
//        if(isFirst)
//        {
//
//            hospitalDataList.clear();
//            isFirst= false;
//        }
        if (counter == 3) {
            mySwipeRefreshLayout.setRefreshing(false);
            progressDialog.hide();
        }
    }

    @Override
    public void failureBayal(String msg) {
        mySwipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
        Toast.makeText(getActivity(), "Problem Loading Data!!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void successDolakha() {

        counter++;
        populateDolData();
//        if(isFirst)
//        {
//
//            hospitalDataList.clear();
//            isFirst= false;
//        }
        if (counter == 3) {
            mySwipeRefreshLayout.setRefreshing(false);
            progressDialog.hide();
        }
    }

    @Override
    public void failureDolakha(String msg) {

        mySwipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
        Toast.makeText(getActivity(), "Problem Loading Data!!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void successCharmandu() {

        counter++;
        populateCharData();
//        if(isFirst)
//        {
//
//            hospitalDataList.clear();
//            isFirst= false;
//        }
        if (counter == 3) {
            mySwipeRefreshLayout.setRefreshing(false);
            progressDialog.hide();
        }
    }

    @Override
    public void failureChaurmandu(String msg) {

        mySwipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
        Toast.makeText(getActivity(), "Problem Loading Data!!!", Toast.LENGTH_LONG).show();
    }


}