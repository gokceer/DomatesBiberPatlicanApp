package com.example.user.recipes;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayAdapter<String> mAdapter;
    Button cookBtn;
    DatabaseHelper db;

    private SQLiteDatabase mDb;
    TextView mItemSelected;
    boolean[] checkedItems;
    ArrayList<String> mUserItems = new ArrayList<>();

    GridLayout mGridLayout; //GridLayout'a buton eklemek istiyorum, GridLayout objesi olusturduk once.
    int j=0;
    int clickCounter = 0;
    Button yiyecek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Grid Layout yapımı
        mGridLayout = (GridLayout) findViewById(R.id.GridLay);

        /* for(int j=0; j<8; j++) {
            mGridLayout.addView(new Yiyecek(this, "Domates", j));
        }*/

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        /************************************DATABASE**********************************************/

        db = new DatabaseHelper(MainActivity.this);

        Log.d("Get Tarifs", "Getting All Tarifs");

        List<Tarif> allTarifs = db.getAllTarif();
        for (Tarif tarif : allTarifs) {
            Log.d("Tarifler", tarif.getResim());
        }

        mItemSelected = findViewById(R.id.tvItemSelected);

        //Elemanları array'e yerlestirdigimiz kisim
        mAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.food_array));
        checkedItems = new boolean[mAdapter.getCount()];


        cookBtn = findViewById(R.id.cookBtn);
       // cookBtn.setVisibility(View.INVISIBLE);
        cookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List <Tarif> tarifs = new ArrayList<Tarif>();
                //tarifs = searchResult(mUserItems);

                if(mUserItems.size()== 0){
                    Toast.makeText(MainActivity.this, "HENÜZ MALZEME SEÇMEDİNİZ!", Toast.LENGTH_SHORT).show();

                } else {Intent startIntent = new Intent(getApplicationContext(), Second_Activity.class);
                    startIntent.putStringArrayListExtra("list", mUserItems);
                    startActivity(startIntent);}



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        //Inflate the search menu action bar.
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu,menu);

        // Get the search menu.
        MenuItem mSearch = menu.findItem(R.id.action_search);
        MenuItem mFridge = menu.findItem(R.id.action_fridge);


        /****YENİ EKLENDİ ******/
        // Get SearchView object.
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Ara");

        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.TRANSPARENT);//Color.parseColor("#ff4444")
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.darker_gray);

        searchAutoComplete.setAdapter(mAdapter);

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,  View view, int itemIndex, long id) {

                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);

                if(!mUserItems.contains((String)adapterView.getItemAtPosition(itemIndex)))
                {String value_at_position_of_item = (String)adapterView.getItemAtPosition(itemIndex);
                    mUserItems.add((String)adapterView.getItemAtPosition(itemIndex));
                    Toast.makeText(MainActivity.this, "Seçtiğiniz yiyecek: " + queryString, Toast.LENGTH_SHORT).show();

                    yiyecek = new Yiyecek(MainActivity.this, value_at_position_of_item,j,330,200);

                    yiyecek.setWidth(((Yiyecek) yiyecek).getWidthButton());
                    yiyecek.setHeight(((Yiyecek) yiyecek).getHeightButton());
                    yiyecek.setShadowLayer(3,2,2,Color.BLACK);
                    yiyecek.setPadding(1,1,1,1);
                    mGridLayout.addView(yiyecek);

                    j++;

                    removeClickedItems(mGridLayout, yiyecek, value_at_position_of_item);

                } /*else {

                    String value_at_position_of_item = (String)adapterView.getItemAtPosition(itemIndex);
                    mUserItems.remove((String)adapterView.getItemAtPosition(itemIndex));
                    Toast.makeText(MainActivity.this, "Seçtiğiniz yiyecek: " + queryString + " silindi!", Toast.LENGTH_LONG).show();
                   mGridLayout.removeViewAt(getGridLayoutIndexOfItem(value_at_position_of_item));

                }*/

                //printSelectedItems();
            }
        });

        mFridge.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent startIntent = new Intent(getApplicationContext(), FridgeActivity.class);
                startActivity(startIntent);
                return true;
            }
        });

        // Below event is triggered when submit search query.
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    //This is for assigning the item's action when user clicked the one of them
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_about_us){
            Toast.makeText(MainActivity.this,
                    "Merhaba, biz Ezgi, Gökçe ve Sedanur!" + "\n"
                            + "Hoşgeldiniz!" + "\n" +
                            "Track foods, track dreams, track future!",
                    Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.action_info){
            Toast.makeText(MainActivity.this,
                    "Elinizde olan malzemeleri Ara kısmından seçip, hadi pişirelim tuşuna" + "\n"
                            + "bastıktan sonra sizin için sunduğumuz mevcut tarifleri görebilirsiniz." + "\n"
                            + "Tarifi açtıktan sonra sağ üst köşede bulunan DİNLE tuşuna basarak" +
                              "tarifi dinleyebilirsiniz." + "\n"
                            + "Menüden MARKET BUL a tıklayarak haritadan marketleri arayabilirsiniz."
                    ,
                    Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.action_maps){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=supermarkets");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }, 1000);
        }

        return super.onOptionsItemSelected(item);
    }

    public void printSelectedItems(){
        String selectedItem = "";
        for (int i=0; i < mUserItems.size(); i++){
            if(i==0){
                selectedItem = "#" + selectedItem;
            }
            selectedItem = selectedItem + mUserItems.get(i).toString();
            if(i != mUserItems.size()-1){
                selectedItem = selectedItem + "\n" + "#";
            }
        }

        //Show the selected items on text view object
        mItemSelected.setText(selectedItem);

        int a = mUserItems.size();
        String ananas = String.valueOf(a);


        /*if(mUserItems.size()>=3){
            cookBtn.setVisibility(View.VISIBLE);
        } else {
            cookBtn.setVisibility(View.GONE);
        }*/
    }

    private void removeClickedItems (GridLayout mGridLayout, Button yiyecek, final String itemToBeDeleted){

        //All items are an object of Yiyecek
        final GridLayout mGL = mGridLayout;
        yiyecek.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Single button click
                clickCounter++;
                Yiyecek Y = (Yiyecek)v;
                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        clickCounter=0;
                    }
                };
                if(clickCounter == 1){
                    handler.postDelayed(runn, 500);
                }
                else if(clickCounter == 2){
                    Toast.makeText(MainActivity.this, "Seçtiğiniz yiyecek: "
                            + Y.getItem_name() + " silindi!", Toast.LENGTH_SHORT).show();

                    mUserItems.remove(itemToBeDeleted); //Remove item from itemList

                    mGL.removeViewAt(getGridLayoutIndexOfItem(Y.getItem_name())); //Remove item from screen
                }
            }
        });
    }

    public int getGridLayoutIndexOfItem(String itemName){
        int id_toBeDeleted=0;

        for (int i=0; i<mGridLayout.getChildCount(); i++){
            Yiyecek yiyecek = (Yiyecek) mGridLayout.getChildAt(i);
            if(yiyecek.item_name.equalsIgnoreCase(itemName)){
                id_toBeDeleted=i;
                break;
            }
        }
        return id_toBeDeleted;
    }


}
