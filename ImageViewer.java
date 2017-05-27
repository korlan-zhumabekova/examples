package kz.edu.nu.sst.kzmuseum.navigation_fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import kz.edu.nu.sst.kzmuseum.R;


/**
 * Created by Korlan on 15.06.2016.
 */
public class ImageViewer extends Activity {

    private MyCustomView mcv;
    private int id;
    private String lang, tag = "ImageViewer";
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);
        mcv = (MyCustomView)findViewById(R.id.myCustomView);
        Log.d(tag,"onCreate");
        id = getIntent().getIntExtra("id",0);
        lang = getIntent().getStringExtra("lang");
        Log.d(tag,"id " + id + " lang "+lang);
        switch (lang){
            case "en":
                switch(id){
                    case 0:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.astana, 400,400);
                        break;
                    case 1:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.gold, 400,400);
                        break;
                    case 2:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.m_art, 400,400);
                        break;
                }
                break;
            case "ru":
                switch(id){
                    case 0:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.astana_ru, 400,400);
                        break;
                    case 1:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.gold, 400,400);
                        break;
                    case 2:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.m_art, 400,400);
                        break;
                }
                break;
            case "kk":
                switch(id){
                    case 0:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.astana_kk, 400,400);
                        break;
                    case 1:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.gold, 400,400);
                        break;
                    case 2:
                        bitmap = mcv.decodeSampledBitmapFromResource(getResources(), R.drawable.m_art, 400,400);
                        break;
                }
                break;

        }
        mcv.setImageBitmap(bitmap);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}





