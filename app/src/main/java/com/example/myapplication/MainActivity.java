package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String Price = "0";
    private final ArrayList<ExampleItem> mExampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildRecyclerView();
        createExampleList();
    }

    public void createExampleList(){
        //mExampleList.add(new ExampleItem(R.drawable.pln, "PLN", "Polski złoty","Cena: "+price));
        //mExampleList.add(new ExampleItem(R.drawable.eur, "EUR", "Euro", "Cena: "+price));
        //mExampleList.add(new ExampleItem(R.drawable.usd, "USD", "Dolar amerykański", "Cena: "+price));
        //mExampleList.add(new ExampleItem(R.drawable.chf, "CHF", "Frank szwajcarski", "Cena: "+price));
        //mExampleList.add(new ExampleItem(R.drawable.gbp, "GBP", "Funt szterling", "Cena: "+price));
        //mExampleList.add(new ExampleItem(R.drawable.rub, "RUB", "Rubel rosyjski", "Cena: "+price));
       // mExampleList.add(new ExampleItem(R.drawable.nok, "NOK", "Korona norweska", "Cena: "+price));
       // mExampleList.add(new ExampleItem(R.drawable.czk, "CZK", "Korona czeska", "Cena: "+price));
       // mExampleList.add(new ExampleItem(R.drawable.jpy, "JPY", "Jen japoński", "Cena: "+price));
       // mExampleList.add(new ExampleItem(R.drawable.huf, "HUF", "Forint węgierski", "Cena: "+price));
      //  mExampleList.add(new ExampleItem(R.drawable.cny, "CNY", "Juan chiński", "Cena: "+price));
      //  mExampleList.add(new ExampleItem(R.drawable.brl, "BRL", "Real brazylijski", "Cena: "+price));

    }

    private void getCoutries(String url)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONArray categoriesArray = response.getJSONArray("categories");
                    String categoryName = null;
                    String imageUrl = null;
                    for(int i = 0; i < categoriesArray.length(); i++)
                    {
                        JSONObject object = categoriesArray.getJSONObject(i);
                        categoryName = object.getString("strCategory");
                        imageUrl = object.getString("strCategoryThumb");
                    }
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }

        });
    }

    public void changeItem(int position, String text){
        mExampleList.get(position).changeText(text);

    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = mExampleList.get(position).getmName();
                Intent i = new Intent(getApplicationContext(),SecondActivity.class);
                i.putExtra("name",name);
                startActivity(i);
            }
        });
    }
}
