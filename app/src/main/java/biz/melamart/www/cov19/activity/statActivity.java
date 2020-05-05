package biz.melamart.www.cov19.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.interfaces.statViewUpdateListner;
import biz.melamart.www.cov19.models.countryStat.countryStat;
import biz.melamart.www.cov19.models.ninja.Ninja;
import biz.melamart.www.cov19.network.statHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class statActivity extends AppCompatActivity  implements statViewUpdateListner {

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

    @BindView(R.id.any_chart_view)
    AnyChartView lineChart;

//    @BindView(R.id.any_chart_view_daily)
//    AnyChartView lineChartDaily;


    @BindView(R.id.r1c1)
    TextView r1c1;
    @BindView(R.id.r1c2)
    TextView r1c2;
    @BindView(R.id.r1c3)
    TextView r1c3;
    @BindView(R.id.r1c4)
    TextView r1c4;
    @BindView(R.id.r1c5)
    TextView r1c5;
    @BindView(R.id.r2c1)
    TextView r2c1;
    @BindView(R.id.r2c2)
    TextView r2c2;
    @BindView(R.id.r2c3)
    TextView r2c3;
    @BindView(R.id.r2c4)
    TextView r2c4;
    @BindView(R.id.r2c5)
    TextView r2c5;
    @BindView(R.id.r3c1)
    TextView r3c1;
    @BindView(R.id.r3c2)
    TextView r3c2;
    @BindView(R.id.r3c3)
    TextView r3c3;
    @BindView(R.id.r3c4)
    TextView r3c4;
    @BindView(R.id.r3c5)
    TextView r3c5;
    @BindView(R.id.r4c1)
    TextView r4c1;
    @BindView(R.id.r4c2)
    TextView r4c2;
    @BindView(R.id.r4c3)
    TextView r4c3;
    @BindView(R.id.r4c4)
    TextView r4c4;
    @BindView(R.id.r4c5)
    TextView r4c5;


    @BindView(R.id.r5c1)
    TextView r5c1;
    @BindView(R.id.r5c2)
    TextView r5c2;
    @BindView(R.id.r5c3)
    TextView r5c3;
    @BindView(R.id.r5c4)
    TextView r5c4;
    @BindView(R.id.r5c5)
    TextView r5c5;


    @BindView(R.id.r6c1)
    TextView r6c1;
    @BindView(R.id.r6c2)
    TextView r6c2;
    @BindView(R.id.r6c3)
    TextView r6c3;
    @BindView(R.id.r6c4)
    TextView r6c4;
    @BindView(R.id.r6c5)
    TextView r6c5;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    @BindView(R.id.supportinCompany)
    TextView supportinCompany;

    @BindView(R.id.ref1)
    TextView ref1;

    @BindView(R.id.ref3)
    TextView ref3;

    private List<countryStat> statList = new ArrayList<>();
    private List<Ninja> ninjaList = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_stat);

        ButterKnife.bind(this, this);
        lineChart.setProgressBar(findViewById(R.id.progress_bar));
        APIlib.getInstance().setActiveAnyChartView(lineChart);

//        lineChartDaily.setProgressBar(view.findViewById(R.id.progress_bar_daily));
        progressDialog = DialogUtils.showProgressDialog(this, null, getString(R.string.msg_progress_fetching_data), true, false);
        getCountryData();
//        fillTotalGraph();

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        getCountryData();
                    }
                }
        );

        supportinCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSite("https://possiblehealth.org/");
            }
        });

        ref3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSite("https://www.worldometers.info/coronavirus/");
            }
        });
        ref1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSite("https://www.mohp.gov.np/en/");
            }
        });

    }
    public void openSite(String url)
    {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("newsUrl",url);
        startActivity(intent);
    }
    public  void getCountryData()
    {

        statHandler statHandler = new statHandler(this,this);
//        statHandler.getFullData();
        statHandler.getNinjaData();

    }
    public void fillConclusionGraph( List<DataEntry>  conData)
    {
        Pie pie = AnyChart.pie();
        pie.data(conData);
        lineChart.setChart(pie);
    }

    public void fillFirst()
    {

        int totalCases = 0;
        int totalDeath = 0;
        int totalRecovered = 0;

        for(int i =0;i < ninjaList.size();i++) {

            if(i == 0  )
            {
                r1c1.setText((i+1)+"");
                r1c2.setText( ninjaList.get(i).getCountry());
                r1c3.setText( ninjaList.get(i).getCases()+"");
                r1c4.setText( ninjaList.get(i).getDeaths()+"");
                r1c5.setText( ninjaList.get(i).getRecovered()+"");
            }
            else if( i == 1 )
            {
                r2c1.setText((i+1)+"");
                r2c2.setText( ninjaList.get(i).getCountry());
                r2c3.setText( ninjaList.get(i).getCases()+"");
                r2c4.setText( ninjaList.get(i).getDeaths()+"");
                r2c5.setText( ninjaList.get(i).getRecovered()+"");

            }
            else if( i == 2 ) {

                r3c1.setText((i+1)+"");
                r3c2.setText( ninjaList.get(i).getCountry());
                r3c3.setText( ninjaList.get(i).getCases()+"");
                r3c4.setText( ninjaList.get(i).getDeaths()+"");
                r3c5.setText( ninjaList.get(i).getRecovered()+"");
            } else if (ninjaList.get(i).getCountry().toLowerCase().trim().equals("china")) {
                r4c1.setText((i+1)+"");
                r4c2.setText( ninjaList.get(i).getCountry());
                r4c3.setText( ninjaList.get(i).getCases()+"");
                r4c4.setText( ninjaList.get(i).getDeaths()+"");
                r4c5.setText( ninjaList.get(i).getRecovered()+"");
            } else if (ninjaList.get(i).getCountry().toLowerCase().trim().equals("india")) {
                r5c1.setText((i+1)+"");
                r5c2.setText( ninjaList.get(i).getCountry());
                r5c3.setText( ninjaList.get(i).getCases()+"");
                r5c4.setText( ninjaList.get(i).getDeaths()+"");
                r5c5.setText( ninjaList.get(i).getRecovered()+"");
            } else if (ninjaList.get(i).getCountry().toLowerCase().trim().equals("nepal")) {
                r6c1.setText((i+1)+"");
                r6c2.setText( ninjaList.get(i).getCountry());
                r6c3.setText( ninjaList.get(i).getCases()+"");
                r6c4.setText( ninjaList.get(i).getDeaths()+"");
                r6c5.setText( ninjaList.get(i).getRecovered()+"");
            }
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
        txtTotalEffectedRatio.setText(String.format("%.02f", er)+"%(of total Pop.)");
        txtTotaldeathRatio.setText(String.format("%.02f", dr)+"%(of total cases)");
        txtTotalrecoveredRatio.setText(String.format("%.02f", rr)+"%(of total cases)");

                        List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("Death", dr));
                data.add(new ValueDataEntry("Recovered", rr));
                fillConclusionGraph(data);
//        for(int i =0;i < statList.size();i++)
//        {
//
//            countryStat countryStat = statList.get(i);
//            if(i == 1  )
//            {
//                r1c1.setText(i+"");
//                r1c2.setText(countryStat.getRegion());
//                r1c3.setText(countryStat.getConfirmed());
//                r1c4.setText(countryStat.getDeath());
//                r1c5.setText(countryStat.getRecovered());
//            }
//            else if( i == 2  )
//            {
//                r2c1.setText(i+"");
//                r2c2.setText(countryStat.getRegion());
//                r2c3.setText(countryStat.getConfirmed());
//                r2c4.setText(countryStat.getDeath());
//                r2c5.setText(countryStat.getRecovered());
//
//            }
//            else if( i == 3   )
//            {
//
//                r3c1.setText(i+"");
//                r3c2.setText(countryStat.getRegion());
//                r3c3.setText(countryStat.getConfirmed());
//                r3c4.setText(countryStat.getDeath());
//                r3c5.setText(countryStat.getRecovered());
//            }
//            else if( countryStat.getRegion().trim().toLowerCase().equals("nepal") )
//            {
//
//                r4c1.setText(i+"");
//                r4c2.setText(countryStat.getRegion());
//                r4c3.setText(countryStat.getConfirmed());
//                r4c4.setText(countryStat.getDeath());
//                r4c5.setText(countryStat.getRecovered());
//            }
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
//                List<DataEntry> data = new ArrayList<>();
//                data.add(new ValueDataEntry("Death", dr));
//                data.add(new ValueDataEntry("Recovered", rr));
//                fillConclusionGraph(data);
//            }
//        }



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

}
