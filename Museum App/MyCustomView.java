package kz.edu.nu.sst.kzmuseum.navigation_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * Created by Korlan Zhumabekova on 12.06.2016.
 * Sample taken from http://stackoverflow.com/questions/12169905/zoom-and-panning-imageview-android
 */


public class MyCustomView extends ImageView {
    private static final int INVALID_POINTER_ID = -1;
    private String tag = "MyCustomView";

    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;
    private float mLastGestureX;
    private float mLastGestureY;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private float mScaleFactor = 1.f;

    public MyCustomView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        mGestureDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if (!mScaleDetector.isInProgress()) {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    mLastTouchX = x;
                    mLastTouchY = y;
                    mActivePointerId = ev.getPointerId(0);
                    Log.d(tag,"adown " + mActivePointerId);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                Log.d(tag,"apointerdown " + pointerId);

                if (mScaleDetector.isInProgress()) {
                    final float gx = mScaleDetector.getFocusX();
                    final float gy = mScaleDetector.getFocusY();
                    mLastGestureX = gx;
                    mLastGestureY = gy;
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {

                // Only move if the ScaleGestureDetector isn't processing a gesture.

                if (ev.getPointerCount()==1) {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(pointerIndex);
                    final float y = ev.getY(pointerIndex);

                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;

                    invalidate();

                    mLastTouchX = x;
                    mLastTouchY = y;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    Log.d("DEBUG", "mActivePointerId");
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    Log.d(tag,"apointerup " + newPointerIndex);
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }   else{
                    final int tempPointerIndex = ev.findPointerIndex(mActivePointerId);
                    mLastTouchX = ev.getX(tempPointerIndex);
                    mLastTouchY = ev.getY(tempPointerIndex);
                }


                break;
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.save();

        canvas.translate(mPosX, mPosY);

        canvas.scale(mScaleFactor, mScaleFactor, mLastGestureX, mLastGestureY);

        super.onDraw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            invalidate();
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e){
            Log.d(tag,"onDown");
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e){
            Log.d(tag,"onDoubleTap");
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


