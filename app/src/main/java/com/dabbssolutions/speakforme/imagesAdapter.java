package com.dabbssolutions.speakforme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class imagesAdapter extends BaseAdapter {
    ArrayList<speeches> images;
    Context context;
    public imagesAdapter(Context context,ArrayList<speeches> images){
        this.images=images;
        this.context=context;
    }
//    final TextToSpeech t1=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
//        @Override
//        public void onInit(int status) {
//            if(status != TextToSpeech.ERROR) {
//                t1.setLanguage(Locale.UK);
//            }
//        }
//    });
    @Override
    public Object getItem(int i) {
        return images.get(i);

    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.imagelayout,viewGroup,false);
        }
        ImageView imgv=(ImageView)view.findViewById(R.id.myImage);
        speeches img=(speeches) getItem(i);
        InputStream imageStream;
        try {
            String s="";
            if(img.isSentence()==false) {
                s=img.getImageName();
                imageStream= context.getAssets().open(img.getImageName() + ".jpg");
            }else{
                Toast.makeText(context,s+"/"+img.getImageName()+".jpeg" , Toast.LENGTH_SHORT).show();
                imageStream=context.getAssets().open(s+"/"+img.getImageName()+".jpeg");
            }

            // load image as Drawable
            Drawable drawable = Drawable.createFromStream(imageStream, null);
            // set image to ImageView
            imgv.setImageDrawable(drawable);
            imgv.setTag(img.getImageName());
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        MainActivity.t1.speak(imgv.getTag().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
                    } else {
                        MainActivity.t1.speak(imgv.getTag().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    if(img.isSentence()==false){
                        try {
                            String[] s = context.getAssets().list(img.getImageName());
                            ArrayList<speeches> more=new ArrayList<>();
                            for(String a : s){
                                more.add(new speeches(a.replace(".jpeg",""),true));
                                imagesAdapter ia=new imagesAdapter(context,more);
                                MainActivity.lv.setAdapter(ia);
                                MainActivity.lv.deferNotifyDataSetChanged();

                            }
                        }catch (Exception e){

                        }
                    }
                }

            });


        }catch (Exception e){

        }
        if (img.isSentence()==false){

        }

        return view;
    }
}
