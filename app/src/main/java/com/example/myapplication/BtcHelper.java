package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.text.format.DateFormat;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class BtcHelper
{
    public static void configureLineChart(LineChart lineChart)
    {
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.setNoDataText("Nie udało się wczytać danych");
    }

    public static void configureLineDataSet(LineDataSet lineData)
    {
        lineData.setDrawValues(false);
        lineData.setDrawCircles(false);
        lineData.setFillAlpha(110);
        lineData.setColor(Color.BLUE);
    }

    public static ArrayList<Entry> getDataValues(ArrayList<Double> sourceArray)
    {
        ArrayList<Entry> values = new ArrayList<>();

        for(int i = 0; i <sourceArray.size(); i++)
        {
            Double y = sourceArray.get(i);
            values.add(new Entry(i, y.floatValue()));
        }

        return values;
    }

    public static String[] convertStringArrayListToStringArray(ArrayList<String> sourceArrayList)
    {
        String[] outputArray = new String[sourceArrayList.size()];
        outputArray = sourceArrayList.toArray(outputArray);

        return outputArray;
    }

    public static String getCurrentTimeFromTimestamp(long timestamp, String dateFormat)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
        calendar.setTimeInMillis(timestamp * 1000);
        String date = DateFormat.format(dateFormat, calendar).toString();
        return date;
    }

    public static double sumDoubleArrayList(ArrayList<Double> sourceArrayList)
    {
        double sum = 0;
        for(int i = 0; i < sourceArrayList.size(); i++)
        {
            sum += sourceArrayList.get(i);
        }
        return sum;
    }
}
