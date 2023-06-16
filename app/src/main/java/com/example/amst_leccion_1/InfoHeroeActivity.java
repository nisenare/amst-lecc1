package com.example.amst_leccion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoHeroeActivity extends AppCompatActivity {

    private String token;
    private String id;
    private ArrayList<String> statsLabels;
    private ArrayList<Integer> statsValues;
    private RequestQueue mQueue;

    private TextView txtViewHeroName;
    private TextView txtViewHeroFullName;
    private ImageView heroImgView;
    private BarChart statsBarChart;

    private String infoURL = "https://www.superheroapi.com/api.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_heroe);
        statsLabels = new ArrayList<>();
        statsLabels.add("intelligence");
        statsLabels.add("strength");
        statsLabels.add("speed");
        statsLabels.add("durability");
        statsLabels.add("power");
        statsLabels.add("combat");
        statsValues = new ArrayList<>();
        token = (String) getIntent().getExtras().get("token");
        id = (String) getIntent().getExtras().get("id");
        infoURL += token + "/" + id;

        txtViewHeroName = findViewById(R.id.txtViewHeroName);
        txtViewHeroFullName = findViewById(R.id.txtViewHeroFullName);
        heroImgView = findViewById(R.id.heroImgView);
        statsBarChart = findViewById(R.id.statsBarChart);

        mQueue = Volley.newRequestQueue(this);
        makeRequest();
    }

    private synchronized void makeRequest() {
        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.GET, infoURL, null,
                response -> {
                    try {
                        txtViewHeroName.setText(String.valueOf(response.get("name")));
                        JSONObject biography = (JSONObject) response.get("biography");
                        txtViewHeroFullName.setText(String.valueOf(biography.get("full-name")));
                        JSONObject powerstats = (JSONObject) response.get("powerstats");
                        for (String stat : statsLabels) {
                            this.statsValues.add(Integer.parseInt((String) powerstats.get(stat)));
                        }
                        showBarChart();
                    } catch (JSONException ex) {

                    }
                },
                error -> { }
        );
        mQueue.add(request);

        JsonObjectRequest imageRequest = new JsonObjectRequest(
            Request.Method.GET, infoURL + "/image", null,
            response -> {
                try {
                    String imageURL = response.getString("url");
                    Picasso.get().load(imageURL).into(heroImgView);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            },
            error -> { }
        );
        mQueue.add(imageRequest);
    }

    private void showBarChart() {
        ArrayList<BarEntry> values = new ArrayList<>();
        int count = 0;
        for (Integer value : statsValues) {
            values.add(new BarEntry(count, value));
            count++;
        }
        BarDataSet dataSet = new BarDataSet(values, "");
        BarData barData = new BarData(dataSet);
        statsBarChart.setData(barData);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);
        statsBarChart.getDescription().setEnabled(true);
        statsBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(statsLabels));
        statsBarChart.invalidate();
    }
}