package com.dabbssolutions.speakforme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static TextToSpeech t1;
    static ArrayList<speeches> data;
    static GridView lv;
    static EditText et;
    ImageView btnSpeak;
    ImageView btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data=new ArrayList<>();
        data.add(new speeches("Sentence Starters",false));
        data.add(new speeches("Activities",false));
        data.add(new speeches("Chat",false));
        data.add(new speeches("Colors",false));
        data.add(new speeches("Core Basics",false));
        data.add(new speeches("Feelings",false));
        data.add(new speeches("Food and Drinks",false));
        data.add(new speeches("Numbers",false));
        data.add(new speeches("Places",false));
        data.add(new speeches("Shapes",false));
        data.add(new speeches("Toys",false));
        data.add(new speeches("who",false));
        lv=(GridView) findViewById(R.id.imageView);
        et=(EditText)findViewById(R.id.sentence);
        btnSpeak=(ImageView) findViewById(R.id.SpeakOut);
        btnReset=(ImageView) findViewById(R.id.reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    MainActivity.t1.speak(et.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    MainActivity.t1.speak(et.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        imagesAdapter ia=new imagesAdapter(MainActivity.this,data);
        lv.setAdapter(ia);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = t1.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("kisi ne kaha tha", "This Language is not supported");

                    }
                }
            }
        });





    }


}