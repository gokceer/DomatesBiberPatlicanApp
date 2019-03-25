package com.example.user.recipes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Second_Activity extends AppCompatActivity {

    private String image;
    DatabaseHelper DB;
    private String ingredients;
    private String contents;
    private ListView dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);

        dataList = findViewById(R.id.list);

        DB = new DatabaseHelper(this);

        ArrayList<String> toSearch = getIntent().getStringArrayListExtra("list");

        List<Tarif> tarifs = searchResult(toSearch);

        if (tarifs.isEmpty()) {
            setContentView(R.layout.sorry_activity);
        } else {
            showList(tarifs);

            dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object item = dataList.getAdapter().getItem(position);

                    Tarif t = (Tarif) item;
                    Intent contentActivity = new Intent(view.getContext(), ContentActivity.class);
                    contentActivity.putExtra("ingredients", t.getMalzemeListesi());
                    contentActivity.putExtra("contents", t.getYapilisi());
                    contentActivity.putExtra("image", t.getResim());
                    contentActivity.putExtra("name", t.getTarifAdi());

                    startActivity(contentActivity);
                }
            });
        }
    }

    protected void onResume() {
        super.onResume();
        ArrayList<String> toSearch = getIntent().getStringArrayListExtra("list");
        List<Tarif> tarifs = searchResult(toSearch);
        if(!tarifs.isEmpty()){
            showList(tarifs);
        }
    }

    public List<Tarif> searchResult(ArrayList<String> listToSearch){
        List <Tarif> tarifs;
        tarifs = DB.getAllTarifsByMalzeme(listToSearch);

        for (Tarif tarif: tarifs) {
            Log.d("Tarifadları", tarif.getTarifAdi());
        }
        return tarifs;
    }


    private void showList(List<Tarif> tarifs) {
        //create the list adapter and set the adapter
        dataList.setAdapter(new Adapter(Second_Activity.this, tarifs));
    }

}
