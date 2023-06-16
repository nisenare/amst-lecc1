package com.example.amst_leccion_1;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;

public class HeroesHashAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<String> ids;
    private ArrayList<String> names;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public HeroesHashAdapter(Context context, int layout, HashMap<String, String> heroes) {
        this.context = context;
        this.layout = layout;
        this.ids = new ArrayList<>();
        this.names = new ArrayList<>();
        heroes.forEach((id, nombre) -> {
           this.ids.add(id);
           this.names.add(nombre);
        });
    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ids.get(position));
    }

    public int getItemIdInteger(int position) {
        return Integer.parseInt(ids.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(R.layout.list_item, null);
        String currentName = names.get(position);
        TextView textView = convertView.findViewById(R.id.txtViewName);
        textView.setText(currentName);
        return convertView;
    }
}
