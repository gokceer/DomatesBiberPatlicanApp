package com.example.user.recipes;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Fridge2Activity extends AppCompatActivity {

    private TextView displayDate;
    private EditText foodNameText;
    private Button saveButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    DatabaseHelper db;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge2);
            db = new DatabaseHelper(this);

            displayDate = findViewById(R.id.dateText);
            foodNameText = findViewById(R.id.foodEdit);
            saveButton = findViewById(R.id.saveButton);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = foodNameText.getText().toString();
                    if (name.length() != 0) {
                        AddData(name, date);
                        finish();
                    } else {
                        toastMessage("Lütfen boş alan bırakmayınız!");
                    }

                }
            });

            // All of the calendar stuff
            displayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Fridge2Activity.this,
                            android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                            dateSetListener,
                            day, month, year);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getDatePicker().setMinDate(System.currentTimeMillis()); // start from today
                    dialog.show();


                }
            });

            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    month = month + 1;

                    date = day + "/" + month + "/" + year;
                    displayDate.setText(date);
                }
            };

        }

        public void AddData (String name, String date){
            boolean insertData = db.addData(name, date);

            if (insertData) {
                toastMessage("Successfully saved");
            } else {
                toastMessage("Something went wrong");
            }
        }

        private void toastMessage (String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }



}

