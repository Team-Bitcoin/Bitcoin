package com.example.myapplication;

import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mName;
        public TextView mCountry;
        public TextView mMarketCap;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mCountry = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList){
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
       ExampleViewHolder evh = new ExampleViewHolder(v, mListener);

       return evh;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, int position) {
        final ExampleItem currentItem = mExampleList.get(position);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(StaticDetails.getBTC_DATA_URL().replace("CURRENCY", currentItem.getmName()), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                String currentPrice = null;

                try {
                    JSONObject jsonObj = response.getJSONObject("RAW").getJSONObject("BTC").getJSONObject(currentItem.getmName());
                    currentPrice = jsonObj.getString("PRICE");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                holder.mMarketCap.setText("Cena: "+ String.format("%.2f", Float.valueOf(currentPrice)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.e(StaticDetails.getTAG(), e.toString());
            }

        });

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mName.setText(currentItem.getmName());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
