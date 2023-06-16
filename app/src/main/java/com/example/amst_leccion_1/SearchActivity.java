package com.example.amst_leccion_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private HashMap<String, String> heroes = new HashMap<>();
    private String token = "";
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        heroes = (HashMap<String, String>) getIntent().getExtras().get("heroes");
        token = getIntent().getExtras().getString("token");

        TextView txtViewCount = findViewById(R.id.txtViewCount);
        txtViewCount.setText(txtViewCount.getText() + " " + heroes.size());

        if (heroes.size() == 0) return;

        listView = findViewById(R.id.listView);
        HeroesHashAdapter miAdapter = new HeroesHashAdapter(this, R.layout.list_item, heroes);
        listView.setAdapter(miAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent goToInfo = new Intent(getBaseContext(), InfoHeroeActivity.class);
            goToInfo.putExtra("token", token);
            goToInfo.putExtra("id", String.valueOf(adapterView.getAdapter().getItemId(position)));
            startActivity(goToInfo);
        });

    }
}