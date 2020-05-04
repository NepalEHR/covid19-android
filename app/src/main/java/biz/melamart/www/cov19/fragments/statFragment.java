package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.Stroke;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.activity.WebActivity;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.interfaces.statViewUpdateListner;
import biz.melamart.www.cov19.models.countryStat.countryStat;
import biz.melamart.www.cov19.models.countryStat.countryStatResponse;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.network.statHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class statFragment extends Fragment implements statViewUpdateListner {


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

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    @BindView(R.id.supportinCompany)
    TextView supportinCompany;

    @BindView(R.id.ref1)
    TextView ref1;

    @BindView(R.id.ref3)
    TextView ref3;

    private List<countryStat> statList = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.stat_fragment_layout, container, false);


        ButterKnife.bind(this, view);
        lineChart.setProgressBar(view.findViewById(R.id.progress_bar));
        APIlib.getInstance().setActiveAnyChartView(lineChart);

//        lineChartDaily.setProgressBar(view.findViewById(R.id.progress_bar_daily));
        progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);
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

        return view;


    }


    public void openSite(String url)
    {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("newsUrl",url);
        getActivity().startActivity(intent);
    }
    public  void getCountryData()
    {

        statHandler statHandler = new statHandler(getActivity(),this);
        statHandler.getFullData();


    }
//    public void fillTotalGraphdaily()
//    {
//        Cartesian pie = AnyChart.bar();
//        pie.data( getDailyData());
//        lineChartDaily.setChart(pie);
//    }
//
//    public void fillTotalGraph()
//    {
//
//        Cartesian cartesian = AnyChart.line();
//        cartesian.animation(true);
////        cartesian.padding(10d, 20d, 5d, 20d);
//        cartesian.crosshair().enabled(true);
//        cartesian.crosshair()
//                .yLabel(true)
//                .yStroke((Stroke) null, null, null, (String) null, (String) null);
//        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//        cartesian.title("COVID-19 Graph");
//        cartesian.yAxis(0).title("Number");
//        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
//        Set set = Set.instantiate();
//        set.data(getData());
//        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
//        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
//        Line series1 = cartesian.line(series1Mapping);
//        series1.name("Confirmed");
//
//        series1.hovered().markers().enabled(true);
//        series1.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series1.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);
//        Line series2 = cartesian.line(series2Mapping);
//
//        series2.name("Death");
//        series2.hovered().markers().enabled(true);
//        series2.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series2.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);
//        Line series3 = cartesian.line(series3Mapping);
//        series3.name("Recovered");
//        series3.hovered().markers().enabled(true);
//        series3.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series3.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);
//        cartesian.legend().enabled(true);
//        cartesian.legend().fontSize(13d);
//        cartesian.legend().padding(0d, 0d, 10d, 0d);
//        lineChart.setChart(cartesian);
//
//
//        APIlib.getInstance().setActiveAnyChartView(lineChartDaily);
//
//        fillTotalGraphdaily();
//    }

    private ArrayList getDailyData(){
        ArrayList<DataEntry> entries = new ArrayList<>();
        entries.add(new CustomDailyDataEntry("22", 32450));
        entries.add(new CustomDailyDataEntry("23", 32650));
        entries.add(new CustomDailyDataEntry("24", 41365));
        entries.add(new CustomDailyDataEntry("25", 39822));
        entries.add(new CustomDailyDataEntry("26", 49555));
        entries.add(new CustomDailyDataEntry("27", 61952));
        entries.add(new CustomDailyDataEntry("28", 63750));


        return entries;
    }

    private ArrayList getData(){
        ArrayList<DataEntry> entries = new ArrayList<>();
        entries.add(new CustomDataEntry("December", 555, 45, 110));
        entries.add(new CustomDataEntry("January", 12000, 5004, 45125));
        entries.add(new CustomDataEntry("February", 90000, 14025, 85656));
        entries.add(new CustomDataEntry("March", 593000, 27862, 131854));
        return entries;
    }

    public void fillConclusionGraph( List<DataEntry>  conData)
    {
        Pie pie = AnyChart.pie();
        pie.data(conData);
        lineChart.setChart(pie);
    }

    public void fillFirst()
    {
for(int i =0;i < statList.size();i++)
{

    countryStat countryStat = statList.get(i);
    if(i == 1  )
    {
        r1c1.setText(i+"");
        r1c2.setText(countryStat.getRegion());
        r1c3.setText(countryStat.getConfirmed());
        r1c4.setText(countryStat.getDeath());
        r1c5.setText(countryStat.getRecovered());
    }
   else if( i == 2  )
    {
        r2c1.setText(i+"");
        r2c2.setText(countryStat.getRegion());
        r2c3.setText(countryStat.getConfirmed());
        r2c4.setText(countryStat.getDeath());
        r2c5.setText(countryStat.getRecovered());

    }
    else if( i == 3   )
    {

        r3c1.setText(i+"");
        r3c2.setText(countryStat.getRegion());
        r3c3.setText(countryStat.getConfirmed());
        r3c4.setText(countryStat.getDeath());
        r3c5.setText(countryStat.getRecovered());
    }
    else if( countryStat.getRegion().trim().toLowerCase().equals("nepal") )
    {

        r4c1.setText(i+"");
        r4c2.setText(countryStat.getRegion());
        r4c3.setText(countryStat.getConfirmed());
        r4c4.setText(countryStat.getDeath());
        r4c5.setText(countryStat.getRecovered());
    }


    if(countryStat.getRegion().trim().toLowerCase().equals("world"))
    {

        float totalResolved = Float.parseFloat(countryStat.getDeath().trim()) + Float.parseFloat(countryStat.getRecovered().trim());

        float er = (Integer.parseInt(countryStat.getConfirmed().trim())/7000000000f) *100;
        float dr = (Integer.parseInt(countryStat.getDeath().trim())/totalResolved) *100;
        float rr = (Integer.parseInt(countryStat.getRecovered().trim())/totalResolved) *100;


//        Toast.makeText(getActivity(), dr+""+Integer.parseInt(countryStat.getDeath().trim()), Toast.LENGTH_SHORT).show();
        txtTotalEffected.setText(countryStat.getConfirmed());
        txtTotalEffectedRatio.setText( String.format("%.02f", er)+"%");
        txtTotaldeath.setText(countryStat.getDeath());
        txtTotaldeathRatio.setText( String.format("%.02f", dr)+"%");
        txtTotalrecovered.setText(countryStat.getRecovered());
        txtTotalrecoveredRatio.setText( String.format("%.02f", rr)+"%");

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Death", dr));
        data.add(new ValueDataEntry("Recovered", rr));
        fillConclusionGraph(data);
    }
}



    }

    @Override
    public void success() {
        statList.clear();
        if (COVSettings.getInstance().getCountryStatResponse() != null) {
            statList.addAll(COVSettings.getInstance().getCountryStatResponse().getResource());
        } fillFirst();
        progressDialog.dismiss();
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failure(String msg) {

        progressDialog.dismiss();
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }

    private class CustomDailyDataEntry extends ValueDataEntry {
        CustomDailyDataEntry(String x, Number value) {
            super(x, value);
        }
    }
}