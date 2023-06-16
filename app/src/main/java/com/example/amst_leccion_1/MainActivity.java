package com.example.amst_leccion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue = null;
    private final String token = "9563282113744681";
    private final String searchURL = "https://www.superheroapi.com/api.php/" + token + "/search/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
    }

    public void buscarHeroe(View view) {
        String busqueda = String.valueOf(((EditText) findViewById(R.id.edtTxtBuscar)).getText());
        String urlFinal = searchURL + busqueda;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, urlFinal, null,
                response -> {

                    HashMap<String, String> heroes = new HashMap<>();
                    try {
                        JSONArray arreglo = (JSONArray) response.get("results");
                        for (int i = 0; i < arreglo.length(); i++) {
                            JSONObject objeto = new JSONObject(arreglo.get(i).toString());
                            String id = objeto.get("id").toString();
                            String name = objeto.get("name").toString();
                            heroes.put(id, name);
                        }
                        goToSearch(heroes, token);
                    } catch (JSONException e) {
                        goToSearch(heroes, token);
                    }
                },
                error -> {

                }
        );
        mQueue.add(request);
    }

    private void goToSearch(HashMap<String, String> heroes, String token) {
        Intent goToSearch = new Intent(getBaseContext(), SearchActivity.class);
        goToSearch.putExtra("heroes", heroes);
        goToSearch.putExtra("token", token);
        startActivity(goToSearch);
    }
}