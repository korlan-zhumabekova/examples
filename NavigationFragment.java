package kz.edu.nu.sst.kzmuseum.navigation_fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import kz.edu.nu.sst.kzmuseum.R;
import kz.edu.nu.sst.kzmuseum.application.MuseumApplication;
import kz.edu.nu.sst.kzmuseum.main_fragment.FragmentInterface;


/**
 * Created by Assan on 27-Oct-15.
 * Updated by Korlan Zhumabekova on 12.06.2016.
 */
public class NavigationFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnTouchListener, FragmentInterface {
    private View mView;
    private ImageView mImage;
    private static final String TAG = "Nav_Fragment";
    private Spinner spinner;
    private Bitmap bitmap;
    private String lang;
    private ArrayAdapter<CharSequence> adapter;
    private Intent i;
    private GestureDetector gd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        i = new Intent(getActivity(), ImageViewer.class);
        gd = new GestureDetector(getActivity(), new GestureListener());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        mView = inflater.inflate(R.layout.navigation_fragment, container, false);
        mImage = (ImageView)mView.findViewById(R.id.navImage);
        spinner = (Spinner)mView.findViewById(R.id.navSpinner);

        String l = ((MuseumApplication)getContext().getApplicationContext()).getCurrentLanguage();

            switch(l){
                case "en":
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.NavSpinnerListEn, android.R.layout.simple_spinner_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case "kk":
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.NavSpinnerListKk, android.R.layout.simple_spinner_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.NavSpinnerListRu, android.R.layout.simple_spinner_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
            }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mImage.setOnTouchListener(this);
        return mView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        lang = ((MuseumApplication)getContext().getApplicationContext()).getCurrentLanguage();
        Log.d(TAG,"lang "+lang);
        id = position;
        Log.d(TAG, "id " + id);
        switch (lang){
            case "en":
                switch (position){
                    case 0:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.astana, 400,400);
                        break;
                    case 1:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.gold, 400,400);
                        break;
                    case 2:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.m_art, 400,400);
                        break;
                }
                break;
            case "ru":
                switch (position){
                    case 0:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.astana_ru, 400,400);
                        break;
                    case 1:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.gold, 400,400);
                        break;
                    case 2:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.m_art, 400,400);
                        break;
                }
                break;
            case "kk":
                switch (position){
                    case 0:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.astana_kk, 400,400);
                        break;
                    case 1:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.gold, 400,400);
                        break;
                    case 2:
                        bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.m_art, 400,400);
                        break;
                }
                break;
        }
        mImage.setImageBitmap(bitmap);
        i.putExtra("id", position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        gd.onTouchEvent(event);
        return true;
    }

    @Override
    public void fragmentVisible() {

        String l = ((MuseumApplication)getContext().getApplicationContext()).getCurrentLanguage();
        if (!lang.equals(l)){
            switch(l){
                case "en":
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.NavSpinnerListEn, android.R.layout.simple_spinner_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case "kk":
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.NavSpinnerListKk, android.R.layout.simple_spinner_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.NavSpinnerListRu, android.R.layout.simple_spinner_item);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }

    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e){
            Log.d(TAG,"onDown");
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e){
            Log.d(TAG,"onDoubleTap");
            i.putExtra("lang",lang);
            startActivity(i);
            return true;
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
