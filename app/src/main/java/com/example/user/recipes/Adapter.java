package com.example.user.recipes;

import android.content.Context;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {
    private DatabaseHelper DB;
    private Context context;
    private LayoutInflater inflater = null;
    List<Tarif> tarifs;

    public Adapter(Context context, List<Tarif> tarifs){
        this.context = context;
        this.tarifs = tarifs;
    }

    @Override
    public int getCount() {
        return tarifs.size();
    }

    @Override
    public Object getItem(int position) {
        return tarifs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.recipe_list,null);
        }

        TextView recipeNameView = row.findViewById(R.id.recipeText);
        ImageView img = row.findViewById(R.id.imageView);

        int resID = context.getResources().getIdentifier(tarifs.get(position).getResim(),"drawable", context.getPackageName()); // to find the R.drawable.menemen
        img.setImageResource(resID);

        recipeNameView.setText(tarifs.get(position).tarif_adi);

        return row;
    }


}


