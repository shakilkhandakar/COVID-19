package com.example.covid_19;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;
    TextView tvPopulation, tvTests, tvUpdated, tvCases, tvRecovered, tvTodayRecovered, tvCritical, tvActive,
            tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new NetworkReciever();
        registerNetworkBroadCast();

        tvPopulation = findViewById(R.id.tvPopulation);
        tvUpdated = findViewById(R.id.tvUpdated);
        tvTests = findViewById(R.id.tvTest);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvTodayRecovered = findViewById(R.id.tvTodayRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        simpleArcLoader = findViewById(R.id.arcLoader);
        scrollView = findViewById(R.id.scrollView);
        pieChart = findViewById(R.id.pieChart);
        refreshLayout = findViewById(R.id.swipeRefreshId);
        refreshLayout.setColorSchemeColors(Color.YELLOW, Color.BLUE, Color.GREEN);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        fetchData();
    }

    private void registerNetworkBroadCast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterNetworkBroadCast();
    }

    private void unRegisterNetworkBroadCast() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

     private void fetchData() {
        simpleArcLoader.start();
        String url = "https://corona.lmao.ninja/v2/all";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    tvPopulation.setText(object.getString("population"));
                    tvUpdated.setText(object.getString("updated"));
                    tvTests.setText(object.getString("tests"));
                    tvCases.setText(object.getString("cases"));
                    tvRecovered.setText(object.getString("recovered"));
                    tvTodayRecovered.setText(object.getString("todayRecovered"));
                    tvCritical.setText(object.getString("critical"));
                    tvActive.setText(object.getString("active"));
                    tvTodayCases.setText(object.getString("todayCases"));
                    tvTotalDeaths.setText(object.getString("deaths"));
                    tvTodayDeaths.setText(object.getString("todayDeaths"));
                    tvAffectedCountries.setText(object.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("cases",
                            Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("recovered",
                            Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("deaths",
                            Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("active",
                            Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    scrollView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    scrollView.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Please check your internet connection and try again !", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goToTrackCountry(View view) {
        startActivity(new Intent(this, AffectedCountries.class));
    }
}