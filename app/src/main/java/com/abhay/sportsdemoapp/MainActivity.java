package com.abhay.sportsdemoapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    int currentItems,totalItems,scrolledOutItems;
    Boolean isScrolling=false;
    int currentItems1,totalItems1,scrolledOutItems1;
    Boolean isScrolling1=false;

    private static final String URL_DATA="https://newsapi.org/v2/top-headlines?country=ca&category=sports&apiKey=bf4aaf430a79426e8b87036f64de97ac";
    private static final String URL_DATA1="https://newsapi.org/v2/top-headlines?country=au&category=sports&apiKey=bf4aaf430a79426e8b87036f64de97ac";
    private static final String URL_DATA2="https://cricapi.com/api/matches?apikey=7R54QvYXYXSOFn7cYunn7TdCOKO2";

    List<ListItem> listItems;
    List<matchListItem> matchListItems;
    ProgressBar progressBar;
    ProgressBar progressBar1;
    private ProgressDialog mLoginProgress;

    RecyclerView recyclerView,horizontalrecyclerview;
    LinearLayoutManager manager;
    LinearLayoutManager linearLayoutManager;
    Adapter adapter;
    HorizontalAdapter horizontalAdapter;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.Progress);
        progressBar1=findViewById(R.id.Progress1);
        recyclerView=findViewById(R.id.RecyclerView);
        horizontalrecyclerview=findViewById(R.id.horizontalrecyclerview);

        listItems=new ArrayList<>();
        matchListItems=new ArrayList<>();

        loadRecyclerViewData();
        loadhorizontalRecyclerViewData();



        try {


            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            horizontalAdapter=new HorizontalAdapter(matchListItems,this);
            horizontalrecyclerview.setAdapter(horizontalAdapter);
            horizontalrecyclerview.setLayoutManager(linearLayoutManager);

            horizontalrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                        isScrolling1=true;
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    currentItems1=linearLayoutManager.getChildCount();
                    totalItems1=linearLayoutManager.getItemCount();
                    scrolledOutItems1=linearLayoutManager.findFirstVisibleItemPosition();

                    if(isScrolling1 && currentItems1+scrolledOutItems1==totalItems1){
                        isScrolling1=false;
                        fetchData1();

                    }
                }
            });


            manager=new LinearLayoutManager(this);
            adapter=new Adapter(listItems,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                        isScrolling=true;
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    currentItems=manager.getChildCount();
                    totalItems=manager.getItemCount();
                    scrolledOutItems=manager.findFirstVisibleItemPosition();

                    if(isScrolling && currentItems+scrolledOutItems==totalItems){
                        isScrolling=false;
                        fetchData();

                    }
                }
            });




        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fetchData1() {
        progressBar1.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar1.setVisibility(View.GONE);



                StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mLoginProgress.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("matches");
                            JSONArray Array = new JSONArray(data);

                            for (int i = 0; i < Array.length(); i++) {
                                JSONObject jsonPart = Array.getJSONObject(i);//only one object in array i.e. at 0th position--{"id":721,"main":"Haze","description":"haze","icon":"50n"}
                                matchListItem item=new matchListItem(
                                        jsonPart.getString("team-1")
                                        ,jsonPart.getString("team-2")
                                        ,jsonPart.getString("type")
                                        ,jsonPart.getString("matchStarted")
                                );

                                matchListItems.add(item);

                                //adapter=new Adapter(listItems,getApplicationContext());

                                horizontalAdapter.notifyDataSetChanged();
                                //recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            mLoginProgress.dismiss();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mLoginProgress.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);



            }
        },3000);
    }

    private void loadhorizontalRecyclerViewData() {


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mLoginProgress.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("matches");
                    JSONArray Array = new JSONArray(data);

                    for (int i = 0; i < Array.length(); i++) {
                        JSONObject jsonPart = Array.getJSONObject(i);//only one object in array i.e. at 0th position--{"id":721,"main":"Haze","description":"haze","icon":"50n"}
                        matchListItem item=new matchListItem(
                                jsonPart.getString("team-1")
                                ,jsonPart.getString("team-2")
                                ,jsonPart.getString("type")
                                ,jsonPart.getString("matchStarted")
                                );

                        matchListItems.add(item);

                        horizontalAdapter=new HorizontalAdapter(matchListItems,getApplicationContext());

                        horizontalrecyclerview.setAdapter(horizontalAdapter);
                    }
                } catch (JSONException e) {
                    mLoginProgress.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoginProgress.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }

    private void loadRecyclerViewData() {

        mLoginProgress=new ProgressDialog(this);
        mLoginProgress.setTitle("Wait for a while");
        mLoginProgress.setMessage("Please wait while we fetching some sports content");
        mLoginProgress.setCanceledOnTouchOutside(false);
        mLoginProgress.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mLoginProgress.dismiss();
                try {

                   JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("articles");
                    JSONArray Array = new JSONArray(data);

                    for (int i = 0; i < Array.length(); i++) {
                        JSONObject jsonPart = Array.getJSONObject(i);//only one object in array i.e. at 0th position--{"id":721,"main":"Haze","description":"haze","icon":"50n"}
                       ListItem item=new ListItem(
                               jsonPart.getString("title")
                               ,jsonPart.getString("description")
                                ,jsonPart.getString("urlToImage")
                                ,jsonPart.getString("url"));
                        String title = jsonPart.getString("title");
                        String description = jsonPart.getString("description");
                        String url = jsonPart.getString("url");
                        String urlToImage = jsonPart.getString("urlToImage");

                        listItems.add(item);

                        adapter=new Adapter(listItems,getApplicationContext());

                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    mLoginProgress.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoginProgress.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);



                StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DATA1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mLoginProgress.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("articles");
                            JSONArray Array = new JSONArray(data);

                            for (int i = 0; i < Array.length(); i++) {
                                JSONObject jsonPart = Array.getJSONObject(i);//only one object in array i.e. at 0th position--{"id":721,"main":"Haze","description":"haze","icon":"50n"}
                                ListItem item=new ListItem(
                                        jsonPart.getString("title")
                                        ,jsonPart.getString("description")
                                        ,jsonPart.getString("urlToImage")
                                        ,jsonPart.getString("url"));
                                String title = jsonPart.getString("title");
                                String description = jsonPart.getString("description");
                                String url = jsonPart.getString("url");
                                String urlToImage = jsonPart.getString("urlToImage");

                                listItems.add(item);

                                //adapter=new Adapter(listItems,getApplicationContext());

                                adapter.notifyDataSetChanged();
                                //recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            mLoginProgress.dismiss();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mLoginProgress.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);



            }
        },3000);
    }
}
