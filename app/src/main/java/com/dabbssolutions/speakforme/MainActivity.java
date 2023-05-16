package com.dabbssolutions.speakforme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
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
    static boolean recording=false;
    static  MenuItem records;
    static  MenuItem stoprecords;
    static ArrayList<ImageView> imageviews;
    static AudioRecorder ad;
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
        ad=new AudioRecorder("sound.3gp",MainActivity.this);
        imageviews=new ArrayList<>();
       // checkPermissions();

        // TODO: Remove the redundant calls to getSupportActionBar()
        //       and use variable actionBar instead
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageviews.size()>0 && images.getChildCount()>0) {
                    imageviews.remove(imageviews.size() - 1);
                    images.removeViewAt(images.getChildCount() - 1);
                }
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
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        records=menu.getItem(0);
        stoprecords=menu.getItem(1);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.record) {
            if (recording == false) {
                Toast.makeText(this, "Recording Started, You May Speak Now", Toast.LENGTH_SHORT).show();
                recording = true;

                try {
                    ad.start();
                    item.setVisible(false);
                    stoprecords.setVisible(true);
                } catch (Exception e) {
                    Log.d("nahi horha record abay saalay", e.getMessage());
                }
            } else {

            }
        }
            else if(item.getItemId()==R.id.Stoprecord){
                Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show();
                recording=false;

                try {
                    ad.stop();
                    item.setVisible(false);
                    records.setVisible(true);
                }catch (Exception e){
                    Log.d("nai horha stop abay saalay",e.getMessage());
                }
            }


        else if(item.getItemId()==R.id.play){
            try {

                ad.playarcoding("sound.3gp");
                item.setEnabled(false);
            }catch (Exception e){
                Log.d("Naho horha play abay saalay",e.getMessage());
            }
            final String adpath=ad.path;
            MediaMetadataRetriever r = new MediaMetadataRetriever();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File f = new File(adpath);
                        r.setDataSource(getApplicationContext(), Uri.fromFile(f));
                        String time = r.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        final long timeInMillisec = Long.parseLong(time);
                        r.release();
                        try {
                            Thread.sleep(timeInMillisec);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    item.setEnabled(true);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }

                }).start();


        }
        else{
            fillist();
        }
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
        data=imagesAdapter.removeDuplicates(data);
        imagesAdapter ia=new imagesAdapter(MainActivity.this,data);
        lv.setAdapter(ia);
        lv.deferNotifyDataSetChanged();
    }
    boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        fillist();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);


    }
    public void checkPermissions(){
        String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET,Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_MEDIA};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {

            }

        });
    }
}