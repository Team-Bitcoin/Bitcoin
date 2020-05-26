package com.example.myapplication;

public class StaticDetails {

    //TAG
    private final static  String TAG = "Bitcoin";

    //HISTORICAL BTC DATA URL
    private static  String MONTH_CHART_URL =
   "https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=CURRENCY&limit=29&api_key=c80efc5fb67f2828e13fe98a971ff8d1555c8c618739bb8fc4360624cad5f946";
    private static  String THREE_MONTHS_CHART_URL = "https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=CURRENCY&limit=89&api_key=c80efc5fb67f2828e13fe98a971ff8d1555c8c618739bb8fc4360624cad5f946";
    private static  String WEEK_CHART_URL = "https://min-api.cryptocompare.com/data/v2/histoday?fsym=BTC&tsym=CURRENCY&limit=6&api_key=c80efc5fb67f2828e13fe98a971ff8d1555c8c618739bb8fc4360624cad5f946";
    private static  String DAY_CHART_URL = "https://min-api.cryptocompare.com/data/v2/histohour?fsym=BTC&tsym=CURRENCY&limit=23&api_key=c80efc5fb67f2828e13fe98a971ff8d1555c8c618739bb8fc4360624cad5f946";

    //FULL DATA BTC URL
    private static  String BTC_DATA_URL = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=CURRENCY&api_key=c80efc5fb67f2828e13fe98a971ff8d1555c8c618739bb8fc4360624cad5f946";

    public static  String getTAG() {return TAG;}

    public static String getMONTH_CHART_URL()
    {
        return MONTH_CHART_URL;
    }

    public static  String getTHREE_MONTHS_CHART_URL() { return THREE_MONTHS_CHART_URL; }

    public static  String getWEEK_CHART_URL()
    {
        return WEEK_CHART_URL;
    }

    public static  String getDAY_CHART_URL() { return DAY_CHART_URL; }

    public static String getBTC_DATA_URL() {return BTC_DATA_URL;}

}
