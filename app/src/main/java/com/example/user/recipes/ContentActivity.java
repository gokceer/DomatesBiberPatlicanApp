package com.example.user.recipes;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ContentActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private Button speechBtn;
    private TextView ingredientV;
    private TextView nameV;
    private TextView contentV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        String ingredient = getIntent().getExtras().getString("ingredients");
        String image = getIntent().getExtras().getString("image");
        String content = getIntent().getExtras().getString("contents");
        String name = getIntent().getExtras().getString("name");

        ingredientV = findViewById(R.id.malzemeView2);
        ingredientV.setText(ingredient);

        contentV = findViewById(R.id.yapilisView2);
        contentV.setMovementMethod(new ScrollingMovementMethod());
        contentV.setText(content);

        nameV = findViewById(R.id.yemekTextView);
        nameV.setText(name);

        ImageView imageV = findViewById(R.id.imageView2);
        int resID = ContentActivity.this.getResources().getIdentifier(image,"drawable", ContentActivity.this.getPackageName()); // to find the R.drawable.menemen
        imageV.setImageResource(resID);

        speechBtn = findViewById(R.id.speechBtn);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.getDefault());

                    if(result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    } else {
                        speechBtn.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        String text = nameV.getText().toString() +"         "+ingredientV.getText().toString() + contentV.getText().toString();
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(mTTS != null){
            mTTS.stop();
           // mTTS.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if(mTTS != null){
            mTTS.stop();
            //mTTS.shutdown();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        speak();
        super.onResume();
    }


}
