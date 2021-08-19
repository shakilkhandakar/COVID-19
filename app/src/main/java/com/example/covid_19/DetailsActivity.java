package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    TextView tvContinent,tvPopulation,tvTests,tvUpdated,tvCountry,tvCases,tvRecovered,tvCritical,tvActive,
            tvTodayCases,tvTodayRecovered,tvTotalDeaths,tvTodayDeaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvCountry = findViewById(R.id.countryName);
        tvContinent = findViewById(R.id.tvContinent);
        tvPopulation = findViewById(R.id.tvPopulation);
        tvTests = findViewById(R.id.tvTest);
        tvUpdated = findViewById(R.id.tvUpdated);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayRecovered = findViewById(R.id.tvTodayRecovered);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);

        int position=getIntent().getIntExtra("position",0);
        Data data=AffectedCountries.dataList.get(position);

        this.setTitle("Details of "+data.getCountry());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvCountry.setText(data.getCountry());
        tvContinent.setText(data.getContinent());
        tvPopulation.setText(data.getPopulation());
        tvTests.setText(data.getTests());
        tvUpdated.setText(data.getUpdated());
        tvCases.setText(data.getCases());
        tvRecovered.setText(data.getRecovered());
        tvCritical.setText(data.getCritical());
        tvActive.setText(data.getActive());
        tvTodayCases.setText(data.getTodayCases());
        tvTodayRecovered.setText(data.getTodayRecovered());
        tvTotalDeaths.setText(data.getDeaths());
        tvTodayDeaths.setText(data.getTodayDeaths());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}