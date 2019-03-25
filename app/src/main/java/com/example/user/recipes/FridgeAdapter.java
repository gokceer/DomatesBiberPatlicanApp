package com.example.user.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FridgeAdapter extends BaseAdapter{
    private DatabaseHelper DB;
    private Context context;
    private LayoutInflater inflater = null;
    private ArrayList<String> listFood;
    private ArrayList<String> listDays;

    public FridgeAdapter(Context context, ArrayList<String> listFood, ArrayList<String> listDays){
        this.context = context;
        this.listFood = listFood;
        this.listDays = listDays;
    }

    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public Object getItem(int position) {
        return listFood.get(position);
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
            row = inflater.inflate(R.layout.fridge_items,null);
        }

        TextView food = row.findViewById(R.id.foodName);
        TextView days = row.findViewById(R.id.expDate);
        TextView message = row.findViewById(R.id.infoTxt);

        int control = Integer.parseInt(listDays.get(position));

        if(control == 0 ){
            days.setText("Tarih geçti!");
            food.setText(listFood.get(position));
            message.setVisibility(View.INVISIBLE);
        } else {
            food.setText(listFood.get(position));
            days.setText(listDays.get(position));
        }

        DB = new DatabaseHelper(context);
        Button del = (Button) row.findViewById(R.id.deleteBtn);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB.deleteData(listFood.get(position));
                listFood.remove(position);
                listDays.remove(position);
                notifyDataSetChanged();
            }
        });

        return row;
    }


}
