package com.dabbssolutions.speakforme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] data= {"all","angry","bored","bad","bye","go for a walk"};
        GridView lv=(GridView) findViewById(R.id.imageView);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(new Locale("hin", "IND"));

                }
            }
        });
        try {
            Object myImageList = Arrays.asList(getResources().getAssets().list("/"));
            Log.d("someone once said: ",myImageList.toString());

        }catch (Exception e){

        }
        imagesAdapter ia=new imagesAdapter(MainActivity.this,data);
        lv.setAdapter(ia);


    }


}