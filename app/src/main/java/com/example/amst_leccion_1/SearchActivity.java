package com.example.amst_leccion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private HashMap<String, String> heroes = new HashMap<>();
    private String token = "";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        heroes = (HashMap<String, String>) getIntent().getExtras().get("heroes");
        token = getIntent().getExtras().getString("token");
        TextView txtViewResultCount =  (TextView) findViewById(R.id.txtViewCount);
        txtViewResultCount.setText(txtViewResultCount.getText() + " " + heroes.size());
        listView = (ListView) findViewById(R.id.listView);
        ListAdapter listAdapter = listView.getAdapter();
        int alturaTotal = 0;
        for (int i = 0; i < heroes.size(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            alturaTotal += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = alturaTotal + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}