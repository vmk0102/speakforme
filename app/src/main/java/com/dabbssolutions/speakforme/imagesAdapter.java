package com.dabbssolutions.speakforme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Locale;

public class imagesAdapter extends BaseAdapter {
    String[] images;
    Context context;
    public imagesAdapter(Context context,String[] images){
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
        return images[i];

    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.imagelayout,viewGroup,false);
        }
        ImageView imgv=(ImageView)view.findViewById(R.id.myImage);
        String img=images[i];

        try {
            InputStream imageStream = context.getAssets().open(img+".jpg");
            // load image as Drawable
            Drawable drawable = Drawable.createFromStream(imageStream, null);
            // set image to ImageView
            imgv.setImageDrawable(drawable);
            imgv.setTag(img);
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.t1.speak(imgv.getTag().toString(), TextToSpeech.QUEUE_FLUSH,null);

                }
            });
        }catch (Exception e){

        }

        return view;
    }
}
