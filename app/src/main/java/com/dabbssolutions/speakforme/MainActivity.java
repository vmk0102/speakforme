package com.dabbssolutions.speakforme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static TextToSpeech t1;
    static ArrayList<speeches> data;
    static GridView lv;
    static EditText et;
    static LinearLayout images;
    ImageView btnSpeak;
    ImageView btnReset;
    ImageView btnBack;
    static ArrayList<ImageView> imageviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(GridView) findViewById(R.id.imageView);
        et=(EditText)findViewById(R.id.sentence);
        fillist();
        btnSpeak=(ImageView) findViewById(R.id.SpeakOut);
        btnReset=(ImageView) findViewById(R.id.reset);
        btnBack=(ImageView)findViewById(R.id.btnBack);
        images=(LinearLayout)findViewById(R.id.images);
        imageviews=new ArrayList<>();

        // TODO: Remove the redundant calls to getSupportActionBar()
        //       and use variable actionBar instead
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageviews.remove(imageviews.size()-1);
                images.removeViewAt(images.getChildCount()-1);

            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    for(ImageView i:imageviews){
                        s=s+" "+i.getTag().toString();
                    }
                    MainActivity.t1.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    for(ImageView i:imageviews){
                        s=s+" "+i.getTag().toString();
                    }
                    MainActivity.t1.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillist();
            }
        });



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fillist();
        return super.onOptionsItemSelected(item);
    }

    public void fillist(){
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
        data.add(new speeches("Who",false));
        imagesAdapter ia=new imagesAdapter(MainActivity.this,data);
        lv.setAdapter(ia);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fillist();
    }
}