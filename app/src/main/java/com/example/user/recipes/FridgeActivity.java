package com.example.user.recipes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FridgeActivity extends AppCompatActivity {

    private ListView dataList;
    private DatabaseHelper DB;
    private String name;
    private String expDate;
    private ArrayList<String> listData;
    private ArrayList<String> listDays;
    private NotificationCompat.Builder notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
            DB = new DatabaseHelper(this);

            dataList = findViewById(R.id.list);

            notification = new NotificationCompat.Builder(this);


            ImageButton addButton = findViewById(R.id.addButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startIntent = new Intent(getApplicationContext(), Fridge2Activity.class);
                    startActivity(startIntent);
                }
            });
        }

        protected void onResume() {
            super.onResume();
            showList();
        }

        private void showList() {
            //get the data and append to a list
            Cursor data = DB.getData();

            listData = new ArrayList<>();
            listDays = new ArrayList<>();

            listData.clear();
            listDays.clear();
            if (data.moveToFirst()) {
                do {
                    //get the value from the database
                    //then add it to the ArrayList
                    name = data.getString(data.getColumnIndex("adi"));
                    expDate = data.getString(data.getColumnIndex("skt"));

                    String result = calculateDays();

                    listData.add(name);
                    listDays.add(result);

                    if (result.equals("0")) {
                        notifications(name);
                    }
                }while(data.moveToNext());
            }
            data.close();
            //create the list adapter and set the adapter
            dataList.setAdapter(new FridgeAdapter(FridgeActivity.this, listData, listDays));
        }

        private String calculateDays() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = format.format(calendar.getTime());

            Date d1;
            Date d2;

            long diffDays = 0;
            try {
                d1 = format.parse(currentDate);
                d2 = format.parse(expDate);

                long diff = d2.getTime() - d1.getTime();
                diffDays = diff / (24 * 60 * 60 * 1000);    //millisecond

            } catch (Exception e) {
                e.printStackTrace();
            }
            return Long.toString(diffDays);
        }

        public void notifications(String f_name){
            String title = "Uyarı!";
            String body = "Son kullanma tarihi geçen ürün: " + f_name;

            notification.setSmallIcon(R.mipmap.ic_launcher);
            notification.setTicker(
                    "This is the ticker"
            );
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle(title);
            notification.setContentText(body);

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);

            NotificationManager notifManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.notify(16122017, notification.build());

            notification.setAutoCancel(true);
        }
    }

