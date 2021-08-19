package com.example.covid_19;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AffectedCountries extends AppCompatActivity {
    RecyclerView recyclerView;
    SimpleArcLoader simpleArcLoader;
    AdapterClass adapter;
    static List<Data> dataList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        recyclerView = findViewById(R.id.recyclerView);
        simpleArcLoader = findViewById(R.id.loader);
        refreshLayout = findViewById(R.id.swipeRefreshId);
        refreshLayout.setColorSchemeColors(Color.YELLOW, Color.BLUE, Color.GREEN);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        this.setTitle("Affected Countries");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();
    }

    private void fetchData() {

        simpleArcLoader.start();
        String url = "https://corona.lmao.ninja/v2/countries";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String countryName = object.getString("country");
                        String continent = object.getString("continent");
                        String population = object.getString("population");
                        String update = object.getString("updated");
                        String tests = object.getString("tests");
                        String cases = object.getString("cases");
                        String todayCases = object.getString("todayCases");
                        String todayRecovered = object.getString("todayRecovered");
                        String deaths = object.getString("deaths");
                        String toDayDeaths = object.getString("todayDeaths");
                        String recovered = object.getString("recovered");
                        String active = object.getString("active");
                        String critical = object.getString("critical");

                        JSONObject jsonObject = object.getJSONObject("countryInfo");
                        String flagUrl = jsonObject.getString("flag");

                        Data data = new Data(flagUrl, countryName, continent, population, tests, update, cases, todayCases, todayRecovered, deaths, toDayDeaths, recovered, active, critical);
                        dataList.add(data);
                    }
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new AdapterClass(getApplicationContext(), dataList);
                    recyclerView.setAdapter(adapter);

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                Toast.makeText(AffectedCountries.this, "Please check your internet connection and try again !", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              /*  ArrayList<Data> filterList = new ArrayList<>();
                for (Data data : dataList) {
                    if (data.getCountry().toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(data);
                    }
                }
                adapter.getFilter(filterList);*/
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}