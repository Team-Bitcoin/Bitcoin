package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.wifi.aware.IdentityChangedListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;


public class SecondActivity extends AppCompatActivity {

    private String currencyHash;
    TextView mCurrentPriceTextView;
    Button mRefreshButton;
    TextView mCurrentDateTextView;
    TextView mDailyAverageTextView;
    TextView mWeeklyAverageTextView;
    TextView mMonthlyAverageTextView;
    TextView mOneHourChangeTextView;
    TextView mOneHourPercentChangeTextView;
    TextView mOneDayChangeTextView;
    TextView mOneDayPercentChangeTextView;
    TextView mCurrencyNameTextView;
    LineChart mTrendLineChart;

    Button mThreeMonthsButton;
    Button mOneMonthButton;
    Button mOneWeekButton;
    Button mOneDayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        currencyHash = intent.getStringExtra("name");

        mCurrentPriceTextView = (TextView) findViewById(R.id.currentPriceTextView);
        mRefreshButton = (Button) findViewById(R.id.refreshButton);
        mCurrencyNameTextView = (TextView) findViewById(R.id.currencyNameTextView);
        mCurrentDateTextView = (TextView) findViewById(R.id.currentDateTextView);
        mDailyAverageTextView = (TextView) findViewById(R.id.dailyAverageTextView);
        mWeeklyAverageTextView = (TextView) findViewById(R.id.weeklyAverageTextView);
        mMonthlyAverageTextView = (TextView) findViewById(R.id.monthlyAverageTextView);
        mOneHourChangeTextView = (TextView) findViewById(R.id.oneHourChangeTextView);
        mOneHourPercentChangeTextView = (TextView) findViewById(R.id.oneHourPercentChangeTextView);
        mOneDayChangeTextView = (TextView) findViewById(R.id.oneDayChangeTextView);
        mOneDayPercentChangeTextView = (TextView) findViewById(R.id.oneDayPercentChangeTextView);
        mTrendLineChart = (LineChart) findViewById(R.id.trendLineChart);
        mThreeMonthsButton = (Button) findViewById(R.id.threeMonthsButton);
        mOneMonthButton = (Button) findViewById(R.id.oneMonthButton);
        mOneWeekButton = (Button) findViewById(R.id.oneWeekButton);
        mOneDayButton = (Button) findViewById(R.id.oneDayButton);

        BtcHelper.configureLineChart(mTrendLineChart);

        mOneMonthButton.setBackgroundColor(Color.parseColor("#3DDC84"));

        //Set all widgets when the activity is build
        setCurrentDate();
        getJSONObjAndSetLineChart(StaticDetails.getMONTH_CHART_URL().replace("CURRENCY", currencyHash), "dd.MM.yyyy", 5);
        getJSONObjAndSetBtcTable(StaticDetails.getBTC_DATA_URL().replace("CURRENCY", currencyHash));
        getJSONObjectAndCalculateAverage(StaticDetails.getMONTH_CHART_URL().replace("CURRENCY", currencyHash), 30, mMonthlyAverageTextView);
        getJSONObjectAndCalculateAverage(StaticDetails.getWEEK_CHART_URL().replace("CURRENCY", currencyHash), 7, mWeeklyAverageTextView);
        getJSONObjectAndCalculateAverage(StaticDetails.getDAY_CHART_URL().replace("CURRENCY", currencyHash), 24, mDailyAverageTextView);

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setCurrentDate();
                getJSONObjAndSetBtcTable(StaticDetails.getBTC_DATA_URL().replace("CURRENCY", currencyHash));
                getJSONObjectAndCalculateAverage(StaticDetails.getMONTH_CHART_URL().replace("CURRENCY", currencyHash), 30, mMonthlyAverageTextView);
                getJSONObjectAndCalculateAverage(StaticDetails.getWEEK_CHART_URL().replace("CURRENCY", currencyHash), 7, mWeeklyAverageTextView);
                getJSONObjectAndCalculateAverage(StaticDetails.getDAY_CHART_URL().replace("CURRENCY", currencyHash), 24, mDailyAverageTextView);
            }
        });

        mThreeMonthsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getJSONObjAndSetLineChart(StaticDetails.getTHREE_MONTHS_CHART_URL().replace("CURRENCY", currencyHash), "dd.MM.yyyy", 5);
                mThreeMonthsButton.setBackgroundColor(Color.parseColor("#3DDC84"));
                mOneMonthButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneWeekButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneDayButton.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        mOneMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getJSONObjAndSetLineChart(StaticDetails.getMONTH_CHART_URL().replace("CURRENCY", currencyHash), "dd.MM.yyyy", 5);
                mOneMonthButton.setBackgroundColor(Color.parseColor("#3DDC84"));
                mThreeMonthsButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneWeekButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneDayButton.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        mOneWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getJSONObjAndSetLineChart(StaticDetails.getWEEK_CHART_URL().replace("CURRENCY", currencyHash), "dd.MM.yyyy", 5);
                mOneWeekButton.setBackgroundColor(Color.parseColor("#3DDC84"));
                mOneMonthButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mThreeMonthsButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneDayButton.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        mOneDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSONObjAndSetLineChart(StaticDetails.getDAY_CHART_URL().replace("CURRENCY", currencyHash), "HH:mm a", 3);
                mOneWeekButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneMonthButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mThreeMonthsButton.setBackgroundColor(Color.parseColor("#ffffff"));
                mOneDayButton.setBackgroundColor(Color.parseColor("#3DDC84"));

            }
        });

    }

    private void getJSONObjAndSetLineChart(String url, final String timeFormat, final int remove)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try
                {
                    JSONObject jsonObject = response.getJSONObject("Data");
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");

                    ArrayList<Double> highPrices = new ArrayList<>();
                    ArrayList<String> times = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject chartData = jsonArray.getJSONObject(i);
                        Long time = chartData.getLong("time");
                        String date = BtcHelper.getCurrentTimeFromTimestamp(time, timeFormat);
                        String chartDate = date.substring(0, date.length() - remove);
                        times.add(chartDate);
                        Double price = chartData.getDouble("high");
                        highPrices.add(price);
                    }
                    String[] dateArray = BtcHelper.convertStringArrayListToStringArray(times);

                    LineDataSet lineDataSet = new LineDataSet(BtcHelper.getDataValues(highPrices), "Linia trendu");

                    BtcHelper.configureLineDataSet(lineDataSet);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(lineDataSet);
                    LineData lineData = new LineData(dataSets);

                    XAxis xAxis = mTrendLineChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray));
                    xAxis.setLabelCount(5);
                    mTrendLineChart.setData(lineData);
                    mTrendLineChart.invalidate();
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.e(StaticDetails.getTAG(), e.toString());
                Toast.makeText(SecondActivity.this, "Pobranie danych nie powiodło się", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void getJSONObjectAndCalculateAverage(String url, final int divider, final TextView textView)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try
                {
                    JSONObject jsonObject = response.getJSONObject("Data");
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    ArrayList<Double> prices = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject data = jsonArray.getJSONObject(i);
                        Double price = data.getDouble("high");
                        prices.add(price);
                    }

                    double average = BtcHelper.sumDoubleArrayList(prices) / divider;
                    String avrToString = String.valueOf(average);
                    textView.setText(String.format("%.2f", Float.valueOf(avrToString)));
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.e(StaticDetails.getTAG(), e.toString());
                Toast.makeText(SecondActivity.this, "Pobranie danych nie powiodło się", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getJSONObjAndSetBtcTable(String url)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                String currentPrice = null;
                String dayChange = null;
                String dayPercentChange = null;
                String hourChange = null;
                String hourPercentChange = null;

                try {
                    JSONObject jsonObj = response.getJSONObject("RAW").getJSONObject("BTC").getJSONObject(currencyHash);
                    currentPrice = jsonObj.getString("PRICE");
                    dayChange = jsonObj.getString("CHANGE24HOUR");
                    dayPercentChange = jsonObj.getString("CHANGEPCT24HOUR");
                    hourChange = jsonObj.getString("CHANGEHOUR");
                    hourPercentChange = jsonObj.getString("CHANGEPCTHOUR");

                    Log.d(StaticDetails.getTAG(), currentPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mCurrentPriceTextView.setText(String.format("%.2f", Float.valueOf(currentPrice)));
                mCurrencyNameTextView.setText(currencyHash);

                if (Double.parseDouble(dayChange) >= 0)
                {
                    mOneDayChangeTextView.setTextColor(Color.parseColor("#008000"));
                    mOneDayChangeTextView.setText("+" + String.format("%.2f", Float.valueOf(dayChange)));
                }
                else
                {
                    mOneDayChangeTextView.setTextColor(Color.parseColor("#cc2424"));
                    mOneDayChangeTextView.setText(String.format("%.2f", Float.valueOf(dayChange)));
                }

                if (Double.parseDouble(dayPercentChange) >= 0)
                {
                    mOneDayPercentChangeTextView.setTextColor(Color.parseColor("#008000"));
                    mOneDayPercentChangeTextView.setText("+" + String.format("%.2f", Float.valueOf(dayPercentChange)) + "%");
                }
                else
                {
                    mOneDayPercentChangeTextView.setTextColor(Color.parseColor("#cc2424"));
                    mOneDayPercentChangeTextView.setText(String.format("%.2f", Float.valueOf(dayPercentChange)) + "%");
                }

                if (Double.parseDouble(hourChange) >= 0)
                {
                    mOneHourChangeTextView.setTextColor(Color.parseColor("#008000"));
                    mOneHourChangeTextView.setText("+" + String.format("%.2f", Float.valueOf(hourChange)));
                }
                else
                {
                    mOneHourChangeTextView.setTextColor(Color.parseColor("#cc2424"));
                    mOneHourChangeTextView.setText(String.format("%.2f", Float.valueOf(hourChange)));
                }

                if (Double.parseDouble(hourPercentChange) >= 0)
                {
                    mOneHourPercentChangeTextView.setTextColor(Color.parseColor("#008000"));
                    mOneHourPercentChangeTextView.setText("+" + String.format("%.2f", Float.valueOf(hourPercentChange)) + "%");
                }
                else
                {
                    mOneHourPercentChangeTextView.setTextColor(Color.parseColor("#cc2424"));
                    mOneHourPercentChangeTextView.setText(String.format("%.2f", Float.valueOf(hourPercentChange)) + "%");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.e(StaticDetails.getTAG(), e.toString());
                Toast.makeText(SecondActivity.this, "Pobranie danych nie powiodło się", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setCurrentDate()
    {
        SimpleDateFormat currentDateGMT = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        currentDateGMT.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String currentDate = currentDateGMT.format(new Date());

        mCurrentDateTextView.setText(currentDate);
    }
}
